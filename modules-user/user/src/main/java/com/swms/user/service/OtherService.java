package com.swms.user.service;

import com.swms.user.service.model.EnumDTO;

import java.util.List;
import java.util.Map;

/**
 * @author sws
 * @Date 2020/12/18 14:17
 * @Description: 系统服务
 */
public interface OtherService {
    /**
     * 获取枚举
     *
     * @param enumName
     *
     * @return
     */
    Map<String, List<EnumDTO>> getEnums(List<String> enumName);
}
