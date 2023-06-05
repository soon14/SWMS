package com.swms.utils.http.parameter;

import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class UserSearchParam extends DateSearchParam {
    private String createUser;
    private String updateUser;
}
