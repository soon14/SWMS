package com.swms.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.swms.user.repository.entity.RenderMetadata;
import com.swms.user.rest.param.rendermetadata.RenderMetadataParam;

/**
 * @author sws
 * @Date 2021/3/22 14:56
 * @Description:
 */
public interface RenderMetadataService extends IService<RenderMetadata> {
    /**
     * 获取渲染元数据
     *
     * @param renderId 页面ID，对应menu表中的id(type=2)
     *
     * @return
     */
    RenderMetadata getByRenderId(Long renderId);

    /**
     * 更新渲染元数据
     *
     * @param param
     */
    void updateByRenderId(RenderMetadataParam param);
}
