package com.swms.user.repository.mapper;

import com.swms.user.repository.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * <p>
 * 菜单模型表 Mapper 接口
 * </p>
 *
 * @author sws
 * @since 2020-12-31
 */
public interface MenuMapper extends JpaRepository<Menu, Long> {
    List<Menu> findByTypeInAndEnableOrderByOrderNum(List<Integer> types, Integer enable);

    List<Menu> findByParentId(Long parentId);
}
