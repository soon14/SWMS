package com.swms.user.external;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.net.HttpHeaders;
import com.swms.user.repository.entity.Role;
import com.swms.user.repository.entity.User;
import com.swms.user.rest.common.enums.UserTypeEnum;
import com.swms.user.rest.common.enums.YesOrNo;
import com.swms.user.rest.param.role.RoleAddParam;
import com.swms.user.rest.param.user.UserAddParam;
import com.swms.user.service.RoleService;
import com.swms.user.service.UserService;
import com.swms.user.api.UserContext;
import com.swms.utils.exception.WmsException;
import com.swms.utils.exception.code_enum.UserErrorDescEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.enums.scope.AuthGiteeScope;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthGiteeRequest;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthScopeUtils;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jcajce.provider.digest.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.imageio.ImageIO;

/**
 * @author sws
 * @date 2021/7/15 10:07
 */
@RestController
@RequestMapping(ExternalConstant.EXTERNAL)
@Slf4j
@Api(tags = "外部账号登陆")
public class ExternalLoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ExternalConfig externalConfig;

    @Autowired
    private ResourceLoader resourceLoader;

    private final Map<String, String> map = Maps.newConcurrentMap();

    /**
     * @return ProjectInfo(项目名称 project, 第三方登陆 redirectUrl, logo 图片 imageUrls)
     */
    @RequestMapping(value = ExternalConstant.GET)
    @ApiOperation(value = "(前端)查询项目名称,第三方登陆地址,logo")
    public ProjectInfo getProjectInfo(@RequestParam(required = false) String loginUrl) {
        if (UserTypeEnum.getByCode(externalConfig.getProject()) != null && StringUtils.isNotBlank(loginUrl)) {
            map.put(externalConfig.getProject().toLowerCase(), loginUrl);
        }

        return ProjectInfo.builder()
            .countries(externalConfig.getCountries())
            .language(externalConfig.getLanguage())
            .project(externalConfig.getProject())
            .redirectUrl(externalConfig.getExternalRedirect())
            .imageUrls(getUrls(externalConfig.getProject()))
            .build();
    }

    /**
     * 前端在登陆界面增加一个第三方登陆的按钮,地址为 getProjectInfo()方法返回值的 ProjectInfo.redirectUrl,对应此接口
     * 根据 source 确定第三方登陆的项目名称,以此为根据,解析对应的配置,并重定向至第三方登陆地址
     *
     * @param source   项目名称,对应 getProjectInfo() 方法返回值的 ProjectInfo.project
     * @param response response response
     *
     * @throws Exception Exception Exception
     */
    @RequestMapping(value = ExternalConstant.REDIRECT + "{source}")
    public void redirect(@PathVariable("source") String source, HttpServletResponse response) throws Exception {
        AuthRequest authRequest = getAuthRequest(source);
        String authorizeUrl = authRequest.authorize(AuthStateUtils.createState());
        log.info("authorizeUrl : {} ", authorizeUrl);
        response.sendRedirect(authorizeUrl);
    }

    /**
     * 传递给第三方系统的回调地址 redirect_uri 对应的接口
     * 在用户成功于第三方系统登陆后,携带 code 信息回调此地址。在海柔系统内创建用户(首次),重定向至user页面,并直接登陆
     *
     * @param source   项目名称
     * @param callback 包含第三方系统返回的 code 信息
     * @param response response
     *
     * @throws Exception Exception
     */
    @RequestMapping(ExternalConstant.CALLBACK + "{source}")
    public void callback(@PathVariable("source") String source, AuthCallback callback, HttpServletResponse response) throws Exception {
        log.info("callback success , source : {}, callback params : {}", source, JSONObject.toJSONString(callback));

        if (StringUtils.isBlank(callback.getCode())) {
            // 安踏系统，成功登出后也会回调此接口，区别在于并不携带 code
            return;
        }
        // 根据 code 获取 token,再根据 token 获取用户信息
        AuthRequest authRequest = getAuthRequest(source);
        AuthResponse<AuthUser> authResponse = authRequest.login(callback);
        log.info("external user info : {} ", JSONObject.toJSONString(authResponse));

        // 根据第三方系统返回的用户信息，创建海柔系统对应的 user
        String loginName = getUniqueKey(source, authResponse);
        if (StringUtils.isBlank(loginName)) {
            throw new WmsException("get unique key from external account failed");
        }
        initUser(loginName);
        String url = map.get(externalConfig.getProject().toLowerCase());
        if (StringUtils.isBlank(url)) {
            throw new WmsException("invalid login url");
        }
        String formattedUserLoginUrl =
            UriComponentsBuilder.fromUriString(url)
                .queryParam(ExternalConstant.USERNAME, loginName)
                .queryParam(ExternalConstant.PASSWORD, ExternalConstant.EXTERNAL_ACCOUNT_PASSWORD)
                .queryParam(ExternalConstant.DATA, ExternalConstant.SECOND_LOGIN)
                .build().toString();
        log.info("redirect to user login : {}", formattedUserLoginUrl);
        response.sendRedirect(formattedUserLoginUrl);
    }

    /**
     * 退出登陆时调用的接口
     * 防止用户通过第三方账号登陆海柔，在海柔系统内退出登陆后，再次点击第三方登陆按钮时，跳过了输入账号密码的步骤，直接登陆成功
     *
     * @param response response
     */
    @RequestMapping(ExternalConstant.LOGOUT)
    @ApiOperation(value = "(前端)登出", notes = "兼容普通登出与第三方系统的登出。成功时携带 logout=success 重定向至登陆页面，失败时返回前端异常 code:1")
    public void logout(HttpServletRequest request, HttpServletResponse response, @RequestParam String loginUrl) {
        String currentUser = UserContext.getCurrentUser();
        log.info("logout, currentUser: {}", currentUser);
        boolean isExternalAccount = !(Objects.equals("anonymousUser", currentUser) || UserTypeEnum.NORMAL.getCode().equalsIgnoreCase(userService.getByUsername(currentUser).getType()));

        String externalLogout = externalConfig.getExternalLogout(loginUrl, isExternalAccount);

        if (StringUtils.isBlank(externalLogout)) {
            throw new WmsException(UserErrorDescEnum.ERR_INVALID_LOGOUT_REDIRECT_URL);
        }
        log.info("logout url : {}", externalLogout);

        try {
            // 前端发送的 ajax 请求，并不包含 x-requested-with:XMLHttpRequest 的请求头
            // 此处以第二次 document 请求的 Accept:application/xml 作为识别依据
            if (request.getHeader(HttpHeaders.ACCEPT).contains("application/xml")) {
                log.info("not ajax");
            } else {
                log.info("ajax");
                // 对 ajax 请求，即正常的登出请求，给前端返回 redirectUrl 的响应头，由前端完成重定向的跳转
                response.setHeader("redirectUrl", externalLogout);
                response.setHeader("enableRedirect", Boolean.TRUE.toString());
                response.flushBuffer();
            }
        } catch (Exception e) {
            log.error("external redirect logout error", e);
            throw new WmsException(UserErrorDescEnum.ERR_INVALID_LOGOUT_REDIRECT_URL);
        }
    }

    /**
     * 根据访问 url 的 source 的不同,获取不同的第三方登陆配置
     *
     * @param source 第三方登陆的具体第三方
     *
     * @return AuthRequest 进行第三方登陆时需要的校验类,根据不同的第三方配置不同的clientId、clientSecret、redirectUrl
     */
    private AuthRequest getAuthRequest(String source) {
        AuthRequest tempAuth = null;
        UserTypeEnum userType = UserTypeEnum.getByCode(source);
        if (userType != null) {
            switch (userType) {
                case GITEE: {
                    tempAuth = new AuthGiteeRequest(AuthConfig.builder()
                        .clientId("4e23f00f662f0168a709a17289df22898c44e53ab8fd486c596fb64d462d18cd")
                        .clientSecret("6fb415f748a724a772bd1656aab5ad71e28e313f50f1a0de0dc73a073c889d1d")
                        .redirectUri("http://localhost:10011/external/callback/gitee")
                        .scopes(AuthScopeUtils.getScopes(AuthGiteeScope.values()))
                        .build());
                    break;
                }
                case ANTA: {
                    tempAuth = new AntaRequest(AuthConfig.builder()
                        .clientId(externalConfig.getClientId())
                        .clientSecret(externalConfig.getClientSecret())
                        .redirectUri(externalConfig.getExternalCallback())
                        .build());
                    break;
                }
                default: {
                }
            }
        }
        if (tempAuth == null) {
            throw new WmsException(UserErrorDescEnum.ERR_INVALID_EXTERNAL_LOGIN);
        }
        return tempAuth;
    }

    private String getUniqueKey(String source, AuthResponse<AuthUser> authUser) {
        String uniqueKey = "";
        UserTypeEnum userType = UserTypeEnum.getByCode(source);
        if (userType == null) {
            return "";
        }
        switch (userType) {
            case GITEE: {
                uniqueKey = authUser.getData().getUsername();
                break;
            }
            case ANTA: {
                AntaUser antaUser = (AntaUser) authUser.getData();
                if (antaUser == null) {
                    log.error("getUniqueKey from anta AuthResponse<AuthUser>: {}  failed", authUser);
                } else {
                    uniqueKey = antaUser.getAttributes().getLoginName();
                }
                break;
            }
            default: {
            }
        }
        return uniqueKey;
    }

    private void initUser(String userName) throws Exception {
        if (userService.getByUsername(userName) == null) {
            // 安踏账号第一次登陆，给默认操作员 operator 角色
            // 万一现场没更新 role 与 role_menu 表，则给一个空的 default 角色
            UserAddParam param = new UserAddParam();
            Set<Long> set = new HashSet<>();
            Wrapper<Role> operatorWrapper = Wrappers.<Role>lambdaQuery()
                .eq(Role::getCode, ExternalConstant.OPERATOR)
                .eq(Role::getId, ExternalConstant.OPERATOR_ID);
            Role operatorRole = roleService.getOne(operatorWrapper);

            if (operatorRole != null) {
                set.add(roleService.getOne(operatorWrapper).getId());
            } else {
                Wrapper<Role> wrapper = Wrappers.<Role>lambdaQuery().eq(Role::getCode, ExternalConstant.DEFAULT_ROLE);
                Role defaultRole = roleService.getOne(wrapper);
                if (defaultRole == null) {
                    RoleAddParam roleAddParam = new RoleAddParam();
                    roleAddParam.setCode(ExternalConstant.DEFAULT_ROLE);
                    roleAddParam.setName(ExternalConstant.DEFAULT_ROLE);
                    roleAddParam.setStatus(Integer.valueOf(YesOrNo.YES.getCode()));
                    roleService.addRole(roleAddParam);
                }
                set.add(roleService.getOne(wrapper).getId());
            }

            param.setRoleIds(set);
            param.setName(userName);
            param.setUsername(userName);
            // 通过前端页面创建用户时，会对密码做MD5。如果这里直接 setPassword(ExternalConstant.EXTERNAL_ACCOUNT_PASSWORD) 会导致后续登陆密码错误
            byte[] aes = MD5.Digest.getInstance("AES").digest(ExternalConstant.EXTERNAL_ACCOUNT_PASSWORD.getBytes(StandardCharsets.UTF_8));
            param.setPassword(new String(aes, StandardCharsets.UTF_8));
            param.setStatus(Integer.valueOf(YesOrNo.YES.getCode()));
            userService.addUser(param);

            // 添加外部账号标识,在 UserServiceImpl.loadUserByUsername 内禁止其不通过外部账号而是通过对应的 user 账号直接登录
            User user = userService.getByUsername(userName);
            user.setType(externalConfig.getProject());
            userService.updateById(user);
        }
    }

    private Map<String, String> getUrls(String projectName) {
        Map<String, String> map = new HashMap<>(8);
        List<String> positionList = Lists.newArrayList(
            ExternalConstant.LOGIN,
            ExternalConstant.LOGIN_HAIROBOT,
            ExternalConstant.LOGO_OPEN,
            ExternalConstant.LOGO_COLLAPSE,
            ExternalConstant.NAME_OPEN,
            ExternalConstant.NAME_COLLAPSE,
            ExternalConstant.BACKGROUND,
            ExternalConstant.LOGO_TOOLBAR);
        String imageUrl;
        BufferedImage bufferedImage;

        for (String position : positionList) {
            map.put(position, "");
            try {
                imageUrl = "/" + projectName + "/" + projectName + "_" + position + ExternalConstant.PNG;
                Resource logoResource = resourceLoader.getResource(ExternalConstant.DEFAULT_RESOURCE_PATH + imageUrl);
                bufferedImage = ImageIO.read(logoResource.getInputStream());
            } catch (IOException e) {
                continue;
            }
            if (bufferedImage != null) {
                map.put(position, externalConfig.getUserAddress() + imageUrl);
            }
        }
        return map;
    }
}
