package com.swms.user.repository.model;

import com.swms.user.repository.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author sws
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserHasRole extends User {

    @ApiModelProperty("角色")
    private String roleNames;

    private String roleIds;

}
