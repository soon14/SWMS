package com.swms.user.service;

import com.swms.user.repository.entity.Menu;
import com.swms.user.rest.param.menu.MenuAddParam;
import com.swms.user.rest.param.menu.MenuUpdatedParam;
import com.swms.user.rest.param.menu.NavigationVo;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 菜单模型表 服务类
 * </p>
 *
 * @author sws
 * @since 2020-12-31
 */
public interface MenuService {

    /**
     * 得到当前认证的用户导航
     *
     * @param roleCodes 角色列表
     *
     * @return 导航列表
     */
    NavigationVo getUserNav(Set<String> roleCodes);

    /**
     * 获取全部的导航结合
     *
     * @param haveDisable 是否包含禁用的导航
     *
     * @return List 导航集合
     */
    List<Menu> getAllNav(boolean haveDisable);

    /**
     * 得到系统导航树
     *
     * @return List
     */
    List<Menu> getMenuTree();

    /**
     * 根据当前登录的用户得到系统导航树
     *
     * @param currentUsername 用户名
     *
     * @return List
     *
     * @throws Exception 获取异常
     */
    List<Menu> getMenuTreeByUser(String currentUsername) throws Exception;

    /**
     * 修改状态
     *
     * @param menuId 菜单id
     * @param status 1 启用, 0 禁用
     */
    void updateStatus(Long menuId, int status);

    /**
     * 通过菜单id删除菜单
     *
     * @param menuId 菜单id
     */
    void removeMenuById(Long menuId);

    /**
     * 修改菜单
     *
     * @param menu 修改的菜单bean
     */
    void updateMenu(MenuUpdatedParam menu);

    /**
     * 得到子菜单
     *
     * @param menuId 菜单id
     *
     * @return 子菜单集合
     */
    List<Menu> getChildMenu(Long menuId);

    /**
     * 添加菜单
     *
     * @param menu 添加的菜单bean
     */
    void addMenu(MenuAddParam menu);
}
