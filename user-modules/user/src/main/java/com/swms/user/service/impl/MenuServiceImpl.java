package com.swms.user.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.swms.user.config.prop.SystemProp;
import com.swms.user.repository.entity.Menu;
import com.swms.user.repository.entity.User;
import com.swms.user.repository.mapper.MenuMapper;
import com.swms.user.rest.common.enums.MenuTypeEnum;
import com.swms.user.rest.common.enums.YesOrNo;
import com.swms.user.rest.param.menu.MenuAddParam;
import com.swms.user.rest.param.menu.MenuUpdatedParam;
import com.swms.user.rest.param.menu.NavigationInfo;
import com.swms.user.rest.param.menu.NavigationVo;
import com.swms.user.service.MenuService;
import com.swms.user.service.RoleMenuService;
import com.swms.user.service.UserService;
import com.swms.user.service.model.MenuTree;
import com.swms.user.utils.AbstractListToTree;
import com.swms.user.api.UserContext;
import com.swms.utils.exception.WmsException;
import com.swms.utils.exception.code_enum.UserErrorDescEnum;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

/**
 * <p>
 * 菜单模型表 服务实现类
 * </p>
 *
 * @author sws
 * @since 2020-12-31
 */
@Service
@AllArgsConstructor
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    private final SystemProp systemProp;
    private final RoleMenuService roleMenuService;
    private final UserService userService;

    @Override
    public NavigationVo getUserNav(Set<String> roleCodes) {
        NavigationVo navigationVo = new NavigationVo();
        if (roleCodes.isEmpty()) {
            return navigationVo;
        }
        List<Menu> menus = null;
        if (roleCodes.contains(systemProp.getSuperRoleCode())) {
            menus = getAllNav(false);
        } else {
            menus = baseMapper.getNavByRoleCodes(roleCodes);
        }

        if (menus == null || menus.isEmpty()) {
            return navigationVo;
        }
        List<NavigationInfo> navigationInfos = Lists.newArrayList();
        Map<Long, Menu> notTopMenus = Maps.newHashMap();
        List<NavigationInfo> topNavigationInfo = Lists.newArrayListWithCapacity(4);

        for (Menu menu : menus) {
            if (menu == null) {
                continue;
            }
            Long parentId = menu.getParentId();
            NavigationInfo navigationInfo = getNavigationVo(menu);
            if (Objects.equals(parentId, 0L)) {
                topNavigationInfo.add(navigationInfo);
            } else {
                if (!notTopMenus.containsKey(parentId)) {
                    notTopMenus.put(parentId, menu);
                }
            }
            // 非插件菜单
            navigationInfos.add(navigationInfo);
        }
        setTopRedirect(topNavigationInfo, notTopMenus);
        navigationVo.setNavigationInfos(navigationInfos);
        return navigationVo;
    }


    @Override
    public List<Menu> getAllNav(boolean haveDisable) {
        LambdaQueryWrapper<Menu> wrapper = Wrappers.<Menu>lambdaQuery()
            .in(Menu::getType, Integer.valueOf(MenuTypeEnum.MENU.getCode()), Integer.valueOf(MenuTypeEnum.PAGE.getCode()));
        if (!haveDisable) {
            wrapper.eq(Menu::getEnable, Integer.valueOf(YesOrNo.YES.getCode()));
        }
        wrapper.orderByAsc(Menu::getOrderNum);
        return list(wrapper);
    }

    @Override
    public List<MenuTree> getMenuTree() {
        List<MenuTree> menus = baseMapper.getMenuTree();
        if (menus == null || menus.isEmpty()) {
            return Collections.emptyList();
        }
        return getListToTree().listToTree(menus);
    }

    @Override
    public List<MenuTree> getAllMenuTreeNoChildren() {
        return baseMapper.getMenuTree();
    }

    @Override
    public List<MenuTree> getMenuTreeByUser(String currentUsername) {
        if (StrUtil.isEmpty(currentUsername)) {
            throw new WmsException(UserErrorDescEnum.NO_AUTHED_USER_FOUND);
        }
        User user = userService.getByUsername(currentUsername);
        if (null == user) {
            return Collections.emptyList();
        }
        Set<String> roleCodes = UserContext.getCurrentRoleCodes();
        boolean containSuperRole = roleCodes.stream().anyMatch(u -> u.equals(systemProp.getSuperRoleCode()));
        if (containSuperRole) {
            // 为超级管理员
            return getMenuTree();
        }
        if (roleCodes.isEmpty()) {
            return Collections.emptyList();
        }
        return getListToTree().listToTree(super.baseMapper.getMenuTreeByRole(roleCodes));
    }

    @Transactional
    @Override
    public void updateStatus(Long menuId, int status) {
        Set<Long> menuIds = Sets.newHashSet(menuId);
        resolveChildMenu(menuId, m -> {
            menuIds.add(m.getId());
        });
        Wrapper<Menu> wrapper = Wrappers.<Menu>lambdaQuery()
            .in(Menu::getId, menuIds);
        Menu menu = new Menu();
        menu.setEnable(status);
        update(menu, wrapper);
    }

    @Transactional
    @Override
    public void removeMenuById(Long menuId) {
        if (null == menuId) {
            return;
        }
        Set<Long> menuIds = Sets.newHashSet(menuId);
        resolveChildMenu(menuId, m -> {
            menuIds.add(m.getId());
        });
        removeByIds(menuIds);
        roleMenuService.removeByMenuId(menuIds);
    }

    @Override
    public void updateMenu(MenuUpdatedParam param) {
        Menu updateBean = new Menu();
        BeanUtils.copyProperties(param, updateBean);
        updateById(updateBean);
    }

    @Transactional
    @Override
    public List<Menu> getChildMenu(Long menuId) {
        if (null == menuId) {
            return Collections.emptyList();
        }
        Wrapper<Menu> wrapper = Wrappers.<Menu>lambdaQuery().eq(Menu::getParentId, menuId);
        return list(wrapper);
    }

    private void resolveChildMenu(Long parentMenuId, Consumer<Menu> consumer) {
        List<Menu> childMenu = getChildMenu(parentMenuId);
        if (childMenu.isEmpty()) {
            return;
        }
        for (Menu menu : childMenu) {
            if (menu == null) {
                continue;
            }
            consumer.accept(menu);
            resolveChildMenu(menu.getId(), consumer);
        }
    }

    @Transactional
    @Override
    public void addMenu(MenuAddParam param) {
        Menu add = new Menu();
        BeanUtils.copyProperties(param, add);
        if (null == add.getParentId()) {
            add.setParentId(0L);
        } else if (StrUtil.isBlank(add.getSystemCode())) {
            add.setSystemCode(getById(add.getParentId()).getSystemCode());
        }
        add.setEnable(Integer.valueOf(YesOrNo.YES.getCode()));
        save(add);
    }

    /**
     * 得到导航模型
     *
     * @param menu 菜单
     *
     * @return NavigationVo
     */
    private NavigationInfo getNavigationVo(Menu menu) {
        NavigationInfo navigationInfo = new NavigationInfo();
        navigationInfo.setId(menu.getId());
        navigationInfo.setParentId(menu.getParentId());
        navigationInfo.setKey(menu.getPermissions());
        navigationInfo.setComponent(menu.getComponent());
        navigationInfo.setPath(menu.getPath());
        NavigationInfo.Meta meta = new NavigationInfo.Meta();
        meta.setTitle(menu.getTitle());
        meta.setShow(true);
        meta.setIcon(menu.getIcon());
        if (Objects.equals(menu.getType(), MenuTypeEnum.PAGE.getCode())) {
            // 菜单
            meta.setTarget(menu.getHtmlTarget());
        }
        navigationInfo.setMeta(meta);
        return navigationInfo;
    }

    /**
     * 设置顶级菜单的跳转路径
     *
     * @param topNavigationInfo 顶级菜单列表
     * @param notTopMenus       非顶级菜单
     */
    private void setTopRedirect(List<NavigationInfo> topNavigationInfo, Map<Long, Menu> notTopMenus) {
        if (topNavigationInfo.isEmpty()) {
            return;
        }
        for (NavigationInfo navigationInfo : topNavigationInfo) {
            String firstRedirect = getFirstRedirect(navigationInfo.getId(), notTopMenus);
            if (StrUtil.isEmpty(firstRedirect)) {
                continue;
            }
            navigationInfo.setRedirect(firstRedirect);
        }
    }

    /**
     * 递归获取第一个菜单型的路径
     *
     * @param menuParentId 菜单的父id
     * @param notTopMenus  非顶级菜单
     *
     * @return 菜单路径
     */
    private String getFirstRedirect(Long menuParentId, Map<Long, Menu> notTopMenus) {
        Menu menu = notTopMenus.get(menuParentId);
        if (menu == null) {
            return null;
        }
        if (Objects.equals(menu.getType(), Integer.valueOf(MenuTypeEnum.PAGE.getCode()))) {
            return menu.getPath();
        } else {
            return getFirstRedirect(menu.getId(), notTopMenus);
        }
    }

    public static AbstractListToTree<MenuTree> getListToTree() {
        return new AbstractListToTree<MenuTree>() {

            @Override
            protected Long getKey(MenuTree node) {
                return node.getId();
            }

            @Override
            protected Long getParentId(MenuTree node) {
                return node.getParentId();
            }

            @Override
            protected List<MenuTree> getChildrenList(MenuTree node) {
                return node.getChildren();
            }

            @Override
            protected void setChildrenList(MenuTree parentNode, List<MenuTree> childNodes) {
                parentNode.setChildren(childNodes);
            }
        };
    }

}
