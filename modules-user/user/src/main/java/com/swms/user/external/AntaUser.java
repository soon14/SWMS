package com.swms.user.external;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import me.zhyd.oauth.model.AuthUser;

/**
 * @author sws
 * @date 2021/7/15 9:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AntaUser extends AuthUser {
    private String service;
    private String id;
    private String clientId;
    private Attribute attributes;

    @Data
    public static class Attribute {
        private String authenticationDate;
        private String clientId;
        private String authenticationMethod;
        private String credentialType;
        private String email;
        private String employeeId;
        private String isFromNewLogin;
        private String loginName;
        private String longTermAuthenticationRequestTokenUsed;
        private String mobilephone;
        private String name;
        private String successfulAuthenticationHandlers;
    }
}
