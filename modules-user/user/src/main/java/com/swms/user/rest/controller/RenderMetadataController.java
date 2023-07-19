//package com.swms.user.rest.controller;
//
//import com.google.common.collect.Lists;
//import com.google.common.collect.Maps;
//import com.swms.user.api.UserContext;
//import com.swms.user.repository.entity.RenderMetadata;
//import com.swms.user.repository.entity.RenderTemplate;
//import com.swms.user.rest.common.BaseResource;
//import com.swms.user.rest.common.enums.MenuTypeEnum;
//import com.swms.user.rest.common.enums.SystemEnum;
//import com.swms.user.rest.param.rendermetadata.ComponentRenderVO;
//import com.swms.user.rest.param.rendermetadata.ComponentsFetchParam;
//import com.swms.user.rest.param.rendermetadata.RenderMetadataFetchParam;
//import com.swms.user.rest.param.rendermetadata.RenderMetadataParam;
//import com.swms.user.rest.param.rendertemplate.RenderTemplateAddParam;
//import com.swms.user.rest.param.rendertemplate.RenderTemplateFetchParam;
//import com.swms.user.service.MenuService;
//import com.swms.user.service.RenderMetadataService;
//import com.swms.user.service.RenderTemplateService;
//import com.swms.user.service.model.MenuTree;
//import com.swms.utils.http.Response;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import jakarta.validation.Valid;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.util.CollectionUtils;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * @author sws
// * @Date 2021/3/22 15:32
// * @Description:
// */
//@RestController
//@RequestMapping(BaseResource.API + "/render")
//@Api(tags = "渲染接口")
//@AllArgsConstructor
//@Slf4j
//public class RenderMetadataController extends BaseResource {
//
//    private final RenderMetadataService renderMetadataService;
//
//    private final RenderTemplateService renderTemplateService;
//
//    private final MenuService menuService;
//
//    @PostMapping("/getPageMetadata")
//    @ApiOperation(value = "查询渲染元数据", response = RenderMetadata.class)
//    public Object getPageMetadata(@RequestBody @Valid RenderMetadataFetchParam param) {
//        return Response.builder().data(renderMetadataService.getByRenderId(param.getRenderId())).build();
//    }
//
//    @PostMapping("/updatePageMetadata")
//    @ApiOperation(value = "修改渲染元数据")
//    public Object updatePageMetadata(@RequestBody @Valid RenderMetadataParam param) {
//        renderMetadataService.updateByRenderId(param);
//        return Response.builder().build();
//    }
//
//    /**
//     * 获取登录用户的可访问的菜单列表
//     *
//     * @param param
//     *
//     * @return
//     */
//    @PostMapping("/getComponentsMetadata")
//    @ApiOperation(value = "查询渲染菜单列表", response = ComponentRenderVO.class)
//    public Object getComponentsMetadata(@RequestBody ComponentsFetchParam param) throws Exception {
//        //TODO gui 后期可加上缓存，提高效率
//        Map<String, List<ComponentRenderVO>> result = Maps.newHashMap();
//        List<MenuTree> allMenus = menuService.getMenuTreeByUser(UserContext.getCurrentUser());
//        if (CollectionUtils.isEmpty(param.getSystemCodes())) {
//            param.setSystemCodes(SystemEnum.getValues());
//        }
//        for (String sys : param.getSystemCodes()) {
//            List<ComponentRenderVO> list = Lists.newArrayList();
//            allMenus.stream()
//                .filter(u -> sys.equals(u.getSystemCode()))
//                .forEach(u -> {
//                    list.add(convert(u));
//                });
//            result.put(sys, list);
//        }
//        return Response.builder().data(result).build();
//    }
//
//    @PostMapping("/getRenderTemplate")
//    @ApiOperation(value = "查询渲染模板信息（验证schema）", response = RenderTemplate.class)
//    public Object getRenderTemplate(@RequestBody @Valid RenderTemplateFetchParam param) {
//        return Response.builder().data(renderTemplateService.getByCode(param.getCode())).build();
//    }
//
//    @PostMapping("/updateRenderTemplate")
//    @ApiOperation(value = "修改渲染模板（验证schema）")
//    public Object updateRenderTemplate(@RequestBody @Valid RenderTemplateAddParam param) {
//        renderTemplateService.update(param);
//        return Response.builder().build();
//    }
//
//    /**
//     * 实体转换
//     *
//     * @param menuTree
//     *
//     * @return
//     */
//    private ComponentRenderVO convert(MenuTree menuTree) {
//        if (null == menuTree) {
//            return null;
//        }
//        ComponentRenderVO vo = new ComponentRenderVO();
//        vo.setId(menuTree.getId());
//        vo.setTitle(menuTree.getTitle());
//        vo.setLinkUrl(menuTree.getPath());
//        vo.setIcon(menuTree.getIcon());
//        vo.setRenderTemplate(menuTree.getRenderTemplate());
//        vo.setDescription(menuTree.getDescription());
//        vo.setSystemCode(menuTree.getSystemCode());
//        if (menuTree.getType().equals(Integer.valueOf(MenuTypeEnum.MENU.getCode()))) {
//            if (menuTree.getParentId().equals(0L)) {
//                vo.setType("menu");
//            } else {
//                vo.setType("component");
//            }
//        } else if (menuTree.getType().equals(Integer.valueOf(MenuTypeEnum.PAGE.getCode()))) {
//            vo.setType("page");
//        }
//        if (menuTree.getType().equals(Integer.valueOf(MenuTypeEnum.MENU.getCode())) && !CollectionUtils.isEmpty(menuTree.getChildren())) {
//            List<ComponentRenderVO> list = Lists.newArrayList();
//            for (MenuTree mt : menuTree.getChildren()) {
//                list.add(convert(mt));
//            }
//            vo.setChildren(list);
//        }
//        return vo;
//    }
//}
