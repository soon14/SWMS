package com.swms.user.external;

import com.swms.user.rest.common.enums.UserTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author sws
 * @date 2021/10/12 11:34
 */
@Configuration
public class ExternalConfig {

    @Value("${countries:中国}")
    private String countries;

    public String getCountries() {
        return countries;
    }

    @Value("${language:zh_CN}")
    private String language;

    public String getLanguage() {
        return language;
    }

    /**
     * 第三方系统的名称
     */
    @Value("${oauth.config.project:ali}")
    private String project;

    public String getProject() {
        return project;
    }

    /**
     * user 的部署地址
     */
    @Value("${oauth.config.userAddress:}")
    private String userAddress;

    public String getUserAddress() {
        return userAddress;
    }

    /**
     * user 在第三方系统的 clientId
     */
    @Value("${oauth.config.clientId:}")
    private String clientId;

    public String getClientId() {
        return clientId;
    }

    /**
     * user 在第三方系统的 clientSecret
     */
    @Value("${oauth.config.secret:}")
    private String clientSecret;

    public String getClientSecret() {
        return clientSecret;
    }

    /**
     * 第三方系统内，通过 clientId 与 clientSecret ，以及对应的第三方登陆的账号密码验证登陆获取 code 的 url
     */
    @Value("${oauth.config.externalAuthorizeUrl:}")
    private String externalAuthorizeUrl;

    public String getExternalAuthorizeUrl() {
        return externalAuthorizeUrl;
    }

    /**
     * 第三方系统内，通过 authorizeUrl 获取到的 code 进一步获取 token 的 url
     */
    @Value("${oauth.config.externalAccessTokenUrl:}")
    private String externalAccessTokenUrl;

    public String getExternalAccessTokenUrl() {
        return externalAccessTokenUrl;
    }

    /**
     * 第三方系统内，通过 token 获取外部登录账号的用户信息的 url
     */
    @Value("${oauth.config.externalUserInfoUrl:}")
    private String externalUserInfoUrl;

    public String getExternalUserInfoUrl() {
        return externalUserInfoUrl;
    }

    /**
     * 第三方系统内，海柔系统登出时进行调用实现第三方账号一起登出的 url
     */
    @Value("${oauth.config.externalLogout:}")
    private String externalLogout;

    /**
     * @return 第三方系统登陆成功后，回调海柔的地址
     */
    public String getExternalCallback() {
        return getUserAddress() + ExternalConstant.EXTERNAL + ExternalConstant.CALLBACK + project;
    }

    /**
     * @return 提供给前端查询的，第三方登陆按钮对应的跳转地址
     */
    public String getExternalRedirect() {
        if (StringUtils.isBlank(getProject()) || StringUtils.isBlank(getUserAddress())) {
            return "";
        }
        if (UserTypeEnum.getByCode(getProject()) == null) {
            return "";
        }
        return getUserAddress() + ExternalConstant.EXTERNAL + ExternalConstant.REDIRECT + project;
    }

    public String getExternalLogout(String loginUrl, boolean isExternalAccount) {
        if (StringUtils.isBlank(loginUrl)) {
            return null;
        }

        String logoutUrl = loginUrl + ExternalConstant.LOGOUT_SUCCESS;
        if (isExternalAccount && StringUtils.isNotBlank(externalLogout)) {
            logoutUrl = externalLogout + logoutUrl;
        }
        return logoutUrl;
    }
}
