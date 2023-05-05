package com.swms.user.rest.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Sets;
import com.swms.user.config.prop.SystemProp;
import com.swms.user.repository.entity.Role;
import com.swms.user.repository.entity.RoleMenu;
import com.swms.user.rest.common.BaseResource;
import com.swms.user.rest.common.vo.RoleMenuVo;
import com.swms.user.rest.param.CommonParam;
import com.swms.user.rest.param.role.RoleAddParam;
import com.swms.user.rest.param.role.RoleMenuFetchParam;
import com.swms.user.rest.param.role.RoleMenuUpdateParam;
import com.swms.user.rest.param.role.RolePageParam;
import com.swms.user.rest.param.role.RoleUpdateParam;
import com.swms.user.rest.param.role.RoleUpdateStatusParam;
import com.swms.user.rest.param.role.RoleVO;
import com.swms.user.service.MenuService;
import com.swms.user.service.RoleMenuService;
import com.swms.user.service.RoleService;
import com.swms.user.service.model.MenuTree;
import com.swms.user.utils.PageHelper;
import com.swms.user.utils.Response;
import com.swms.user.api.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * 角色接口
 *
 * @author sws
 * @version 1.0
 */
@RestController
@RequestMapping(BaseResource.API + "role")
@Api(tags = "角色接口")
@AllArgsConstructor
@Slf4j
public class RoleController extends BaseResource {


    private final RoleService roleService;
    private final SystemProp systemProp;
    private final RoleMenuService roleMenuService;
    private final MenuService menuService;

    @PostMapping("pageQuery")
    @ApiOperation("分页条件查询角色(内部测试用)")
    public Object pageQuery(@RequestBody @Valid RolePageParam param) {
        Page<Role> page = page(param, param.getCurrentPage(), param.getPageSize());
        return Response.builder().data(page).build();
    }

    @PostMapping("search")
    @ApiOperation(value = "分页条件查询角色(前端使用)", response = RoleVO.class)
    public Object search(@RequestParam Integer pageIndex,
                         @RequestParam Integer pageSize,
                         @RequestBody @Valid RolePageParam param) {
        param.setPageSize(pageSize);
        param.setCurrentPage(pageIndex);
        Page<Role> page = page(param, param.getCurrentPage(), param.getPageSize());
        return Response.builder().data(PageHelper.convertWithPageInfo(page, RoleVO.class)).build();
    }

    @PostMapping("/getRoleMenu")
    @ApiOperation(value = "分配角色时, 查询当前角色的菜单和权限", response = RoleMenuVo.class)
    public Object getRoleMenu(@RequestBody @Valid RoleMenuFetchParam param) throws Exception {
        RoleMenuVo roleMenuVo = new RoleMenuVo();
        List<MenuTree> menuTreeByCurrentUser = menuService.getMenuTreeByUser(UserContext.getCurrentUser());

        LambdaQueryWrapper<RoleMenu> wrapper = Wrappers.<RoleMenu>lambdaQuery()
            .eq(RoleMenu::getRoleId, param.getRoleId())
            .eq(RoleMenu::getIsParent, 0);
        List<RoleMenu> roleMenus = roleMenuService.list(wrapper);
        Set<Long> menuIds = Sets.newHashSet();
        for (RoleMenu roleMenu : roleMenus) {
            menuIds.add(roleMenu.getMenuId());
        }
        roleMenuVo.setMenuIds(menuIds);
        setRoleHasRight(menuIds, menuTreeByCurrentUser);
        setCurrentIndex(menuTreeByCurrentUser, "");
        roleMenuVo.setMenuTree(menuTreeByCurrentUser);
        return Response.builder().data(roleMenuVo).build();
    }

    @PostMapping("/updateRoleMenu")
    @ApiOperation("分配角色菜单和权限")
    public Object updateRoleMenu(@RequestBody @Valid RoleMenuUpdateParam param) throws Exception {
        roleService.updateRoleMenu(param);
        return Response.builder().build();
    }

    @PostMapping("/add")
    @ApiOperation("添加角色")
    public Object add(@RequestBody @Valid RoleAddParam param) throws Exception {
        roleService.addRole(param);
        return Response.builder().build();
    }

    @PostMapping("update")
    @ApiOperation("修改角色")
    public Object update(@RequestBody @Valid RoleUpdateParam param) throws Exception {
        roleService.updateRole(param);
        return Response.builder().build();
    }

    @PostMapping("/updateStatus")
    @ApiOperation("修改角色状态")
    public Object updateStatus(@RequestBody @Valid RoleUpdateStatusParam param) throws Exception {
        roleService.updateStatus(param.getRoleId(), param.getStatus());
        return Response.builder().build();
    }

    @PostMapping("/delete")
    @ApiOperation("删除角色")
    public Object delete(@RequestBody @Valid CommonParam param) throws Exception {
        for (Long id : param.getIds()) {
            roleService.deleteRole(id);
        }
        return Response.builder().build();
    }

    private Page<Role> page(RolePageParam param, int pageIndex, int pageSize) {
        LambdaQueryWrapper<Role> wrapper = Wrappers.<Role>lambdaQuery()
            .ne(Role::getCode, systemProp.getSuperRoleCode());
        if (StringUtils.isNotEmpty(param.getName())) {
            wrapper.like(Role::getName, param.getName());
        }
        if (StringUtils.isNotEmpty(param.getCode())) {
            wrapper.like(Role::getCode, param.getCode());
        }
        Integer status = null != param.getStatus() && (param.getStatus() == 1 || param.getStatus() == 0) ? param.getStatus() : null;
        if (status != null) {
            wrapper.eq(Role::getStatus, param.getStatus());
        }
        wrapper.orderByDesc(Role::getGmtCreated);
        Page<Role> page = new Page<>(pageIndex, pageSize);
        page = roleService.page(page, wrapper);

        return page;
    }

    private void setRoleHasRight(Set<Long> menuIds, List<MenuTree> list) {
        for (MenuTree menuTree : list) {
            if (CollectionUtils.isNotEmpty(menuTree.getChildren())) {
                setRoleHasRight(menuIds, menuTree.getChildren());
            }
            if (menuIds.contains(menuTree.getId())) {
                menuTree.setRoleHasRight(1);
            } else if (CollectionUtils.isNotEmpty(menuTree.getChildren()) && anyChildrenHasRight(menuIds, menuTree.getChildren())) {
                menuTree.setRoleHasRight(2);
            } else {
                menuTree.setRoleHasRight(0);
            }
        }
    }

    private boolean anyChildrenHasRight(Set<Long> menuIds, List<MenuTree> list) {
        boolean anyHasRight;
        for (MenuTree menuTree : list) {
            if (menuIds.contains(menuTree.getId())) {
                return true;
            }

            if (CollectionUtils.isNotEmpty(menuTree.getChildren())) {
                anyHasRight = anyChildrenHasRight(menuIds, menuTree.getChildren());
                if (anyHasRight) {
                    return true;
                }
            }
        }
        return false;
    }

    private void setCurrentIndex(List<MenuTree> list, String parentIndex) {
        MenuTree menuTree;
        for (int i = 0; i < list.size(); i++) {
            menuTree = list.get(i);
            menuTree.setCurrentIndex(parentIndex + i);
            if (CollectionUtils.isNotEmpty(menuTree.getChildren())) {
                setCurrentIndex(menuTree.getChildren(), parentIndex + i + "-");
            }
        }
    }
}
