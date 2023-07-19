package com.swms.user.rest.common.vo;

import com.swms.user.repository.entity.Menu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * 角色权限模型
 *
 * @author sws
 * @version 1.0
 */
@ApiModel("角色权限模型")
@Data
public class RoleMenuVo {

    @ApiModelProperty("当前角色已经分配的菜单id")
    private Set<Long> menuIds;

    @ApiModelProperty("当前登录的用户所拥有的菜单树")
    private List<Menu> menuTree;

}
