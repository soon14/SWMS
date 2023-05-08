package com.swms.user.external;

import com.swms.user.utils.ContextUtil;
import me.zhyd.oauth.config.AuthSource;
import me.zhyd.oauth.request.AuthDefaultRequest;
import org.springframework.stereotype.Component;

/**
 * @author sws
 * @date 2021/7/14 20:02
 */
@Component
public enum AntaSource implements AuthSource {

    /**
     * 定义安踏项目的授权、获取 token 、获取用户信息地址的枚举
     */
    ANTA {
        private final ExternalConfig externalConfig = ContextUtil.getBean(ExternalConfig.class);

        /**
         * 选择安踏账号登陆时，跳转的安踏页面链接。通过安踏的账号认证后重定向至 redirect_uri ，并返回一个用于查询token的临时一次性code
         *
         * @return url
         */
        @Override
        public String authorize() {
            return externalConfig.getExternalAuthorizeUrl();
        }

        /**
         * 通过 code 获取 accessToken
         *
         * @return url
         */
        @Override
        public String accessToken() {
            return externalConfig.getExternalAccessTokenUrl();
        }

        /**
         * 通过 accessToken 获取用户信息
         *
         * @return url
         */
        @Override
        public String userInfo() {
            return externalConfig.getExternalUserInfoUrl();
        }

        @Override
        public Class<? extends AuthDefaultRequest> getTargetClass() {
            return AntaRequest.class;
        }
    }
}
