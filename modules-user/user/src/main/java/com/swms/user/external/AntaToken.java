package com.swms.user.external;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import me.zhyd.oauth.model.AuthToken;

/**
 * @author sws
 * @date 2021/7/15 9:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AntaToken extends AuthToken {
    private String accessToken;
    private String refreshToken;
    private int expireIn;

    private boolean success;
    private String scope;
    private String tokenType;
    private String message;
}
