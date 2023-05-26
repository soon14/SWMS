package com.swms.user.rest.common.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserModel {
    private long id;
    private String username;
}
