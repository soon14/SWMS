//package com.swms.user.service.impl;
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.core.toolkit.Wrappers;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.swms.user.repository.entity.RenderTemplate;
//import com.swms.user.repository.mapper.RenderTemplateMapper;
//import com.swms.user.rest.param.rendertemplate.RenderTemplateAddParam;
//import com.swms.user.service.RenderTemplateService;
//import org.springframework.beans.BeanUtils;
//import org.springframework.stereotype.Service;
//
///**
// * @author sws
// * @Date 2021/3/22 14:56
// * @Description:
// */
//@Service
//public class RenderTemplateServiceImpl extends ServiceImpl<RenderTemplateMapper, RenderTemplate> implements RenderTemplateService {
//    @Override
//    public RenderTemplate getByCode(String code) {
//        LambdaQueryWrapper<RenderTemplate> wrapper = Wrappers.<RenderTemplate>lambdaQuery();
//        wrapper.eq(RenderTemplate::getCode, code);
//        return getOne(wrapper);
//    }
//
//    @Override
//    public void update(RenderTemplateAddParam param) {
//        RenderTemplate renderTemplate = getByCode(param.getCode());
//        RenderTemplate renderTemplate2 = new RenderTemplate();
//        if (null != renderTemplate) {
//            renderTemplate2.setId(renderTemplate.getId());
//        }
//        BeanUtils.copyProperties(param, renderTemplate2);
//        saveOrUpdate(renderTemplate2);
//    }
//}
