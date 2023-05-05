package com.swms.user.external;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.cache.AuthStateCache;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.exception.AuthException;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthToken;
import me.zhyd.oauth.request.AuthDefaultRequest;

/**
 * @author sws
 * @date 2021/7/14 20:09
 */
@Slf4j
public class AntaRequest extends AuthDefaultRequest {
    public AntaRequest(AuthConfig config) {
        super(config, AntaSource.ANTA);
    }

    public AntaRequest(AuthConfig config, AuthStateCache authStateCache) {
        super(config, AntaSource.ANTA, authStateCache);
    }

    @Override
    protected AntaToken getAccessToken(AuthCallback authCallback) {
        String response = doPostAuthorizationCode(authCallback.getCode());
        JSONObject object = JSONObject.parseObject(response);

        this.checkToken(object);

        AntaToken antaToken = new AntaToken();
        antaToken.setAccessToken(object.getString(ExternalConstant.ANTA_ACCESS_TOKEN));
        antaToken.setRefreshToken(object.getString(ExternalConstant.ANTA_REFRESH_TOKEN));
        antaToken.setSuccess(object.getBoolean(ExternalConstant.ANTA_SUCCESS));
        antaToken.setScope(object.getString(ExternalConstant.ANTA_SCOPE));
        antaToken.setTokenType(object.getString(ExternalConstant.ANTA_TOKEN_TYPE));
        antaToken.setMessage(object.getString(ExternalConstant.ANTA_MESSAGE));
        antaToken.setExpireIn(object.getInteger(ExternalConstant.ANTA_EXPIRES_IN));
        log.info("anta token:{}", antaToken);
        return antaToken;
    }

    @Override
    protected AntaUser getUserInfo(AuthToken authToken) {
        String response = doGetUserInfo(authToken);
        JSONObject object = JSONObject.parseObject(response);

        AntaUser user = new AntaUser();
        user.setService(object.getString(ExternalConstant.ANTA_SERVICE));
        user.setId(object.getString(ExternalConstant.ANTA_ID));
        user.setUuid(object.getString(ExternalConstant.ANTA_CLIENT_ID));
        user.setClientId(object.getString(ExternalConstant.ANTA_CLIENT_ID));
        user.setAttributes(JSON.toJavaObject(object.getJSONObject(ExternalConstant.ANTA_ATTRIBUTES), AntaUser.Attribute.class));
        return user;
    }

    private void checkToken(JSONObject object) {
        // token的异常验证。因为根据code获取token时，无论成功与否都会返回token
        // 区别在于成功时 json 内 key 为 success 对应的 value 为 true
        if (object.containsKey(ExternalConstant.ANTA_SUCCESS) && !object.getBoolean(ExternalConstant.ANTA_SUCCESS)) {
            throw new AuthException(object.getString(ExternalConstant.ANTA_MESSAGE));
        }
    }
}
