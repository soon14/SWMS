//package com.swms.user.service.impl;
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.core.toolkit.Wrappers;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.swms.user.repository.entity.RenderMetadata;
//import com.swms.user.repository.mapper.RenderMetadataMapper;
//import com.swms.user.rest.param.rendermetadata.RenderMetadataParam;
//import com.swms.user.service.RenderMetadataService;
//import org.springframework.beans.BeanUtils;
//import org.springframework.stereotype.Service;
//
///**
// * @author sws
// * @Date 2021/3/22 15:15
// * @Description:
// */
//@Service
//public class RenderMetadataServiceImpl extends ServiceImpl<RenderMetadataMapper, RenderMetadata> implements RenderMetadataService {
//    @Override
//    public RenderMetadata getByRenderId(Long renderId) {
//        LambdaQueryWrapper<RenderMetadata> wrapper = Wrappers.<RenderMetadata>lambdaQuery();
//        wrapper.eq(RenderMetadata::getRenderId, renderId);
//        return getOne(wrapper);
//    }
//
//    @Override
//    public void updateByRenderId(RenderMetadataParam param) {
//        RenderMetadata renderMetadata = getByRenderId(param.getRenderId());
//        RenderMetadata renderMetadata2 = new RenderMetadata();
//        if (null != renderMetadata) {
//            renderMetadata2.setId(renderMetadata.getId());
//        }
//        BeanUtils.copyProperties(param, renderMetadata2);
//        saveOrUpdate(renderMetadata2);
//    }
//}
