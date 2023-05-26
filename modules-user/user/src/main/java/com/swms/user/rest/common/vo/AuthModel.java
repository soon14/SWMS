package com.swms.user.rest.common.vo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthModel {
    private String api_token;
    private String refreshToken;

    private UserModel user;
}
