package com.swms.utils.base;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseUserDTO extends BaseDateDTO {
    private String createUser;
    private String updateUser;
}
