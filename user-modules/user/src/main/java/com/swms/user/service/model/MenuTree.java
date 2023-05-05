package com.swms.user.service.model;

import com.swms.user.repository.entity.Menu;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 菜单树
 *
 * @author sws
 * @version 1.0
 * @since 2020-12-31
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MenuTree extends Menu {
    @ApiModelProperty("子节点")
    private List<MenuTree> children;

    @ApiModelProperty("角色是否有该菜单的权限(1:有,0:没有,2:角色无菜单权限，但拥有菜单的某个直接或简介子菜单的权限)")
    private Integer roleHasRight;

    @ApiModelProperty("菜单的层级结构")
    private String currentIndex;
}
