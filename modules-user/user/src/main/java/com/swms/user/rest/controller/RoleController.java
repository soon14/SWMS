package com.swms.user.rest.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.swms.user.repository.entity.Menu;
import com.swms.user.repository.entity.RoleMenu;
import com.swms.user.repository.mapper.RoleMenuMapper;
import com.swms.user.rest.common.BaseResource;
import com.swms.user.rest.common.vo.RoleMenuVo;
import com.swms.user.rest.param.role.RoleAddParam;
import com.swms.user.rest.param.role.RoleMenuUpdateParam;
import com.swms.user.rest.param.role.RoleUpdateParam;
import com.swms.user.service.MenuService;
import com.swms.user.service.RoleService;
import com.swms.utils.http.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
    private final MenuService menuService;
    private final RoleMenuMapper roleMenuMapper;

    @GetMapping("/getRoleMenu/{id}")
    @ApiOperation(value = "分配角色时, 查询当前角色的菜单和权限", response = RoleMenuVo.class)
    public Object getRoleMenu(@PathVariable Long id) {
        List<Menu> menuTree = menuService.getMenuTree();
        List<RoleMenu> roleMenus = roleMenuMapper.findByRoleIdIn(Lists.newArrayList(id));
        Set<Long> menuIds = roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toSet());
        Map<String, Object> result = Maps.newHashMap();
        result.put("value", StringUtils.join(menuIds, ","));
        result.put("options", menuTree);
        return Response.builder().data(result).build();
    }

    @PostMapping("/updateRoleMenu/{id}")
    @ApiOperation("分配角色菜单和权限")
    public Object updateRoleMenu(@PathVariable long id, @RequestBody @Valid RoleMenuUpdateParam param) throws Exception {
        param.setRoleId(id);
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

    @DeleteMapping("{id}")
    @ApiOperation("删除角色")
    public Object delete(@PathVariable Long id) throws Exception {
        roleService.deleteRole(id);
        return Response.builder().build();
    }

}
