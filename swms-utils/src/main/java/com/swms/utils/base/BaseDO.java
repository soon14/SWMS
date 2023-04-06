package com.swms.utils.base;

import lombok.Data;

@Data
public class BaseDO {
    private Long createTime;
    private String createUser;

    private Long updateTime;
    private String updateUser;
}
