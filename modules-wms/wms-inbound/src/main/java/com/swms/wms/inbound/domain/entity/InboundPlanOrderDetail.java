package com.swms.wms.inbound.domain.entity;

import com.swms.wms.inbound.application.check.IInboundOrderDetail;
import lombok.Data;

import java.util.Map;
import java.util.SortedMap;


/**
 * 入库通知单明细
 *
 * @author krystal
 * @date 2023/08/21
 */
@Data
public class InboundPlanOrderDetail implements IInboundOrderDetail {

    /**
     * 明细id
     */
    private Long id;

    /**
     * 入站通知单订单id
     */
    private Long inboundPlanOrderId;

    /**
     * 容器代码
     */
    private String containerCode;
    /**
     * 容器规格
     */
    private String containerSpecCode;
    /**
     * 容器格口
     */
    private String containerSlotCode;

    /**
     * 箱码
     */
    private String boxNo;

    /**
     * 数量进货
     */
    private Integer qtyRestocked;
    /**
     * 数量审核
     */
    private Integer qtyAccepted;
    /**
     * 未收货数量
     */
    private Integer qtyUnreceived;

    /**
     * 数量异常
     */
    private Integer qtyAbnormal;

    /**
     * 异常原因
     */
    private String abnormalReason;

    /**
     * 负责人
     */
    private String responsibleParty;

    /**
     * sku码
     */
    private String skuCode;

    /**
     * sku id
     */
    private Long skuId;
    /**
     * sku名字
     */
    private String skuName;
    /**
     * 风格
     */
    private String style;
    /**
     * 颜色
     */
    private String color;
    /**
     * 大小
     */
    private String size;
    /**
     * 品牌
     */
    private String brand;

    /**
     * 批处理属性
     */
    private SortedMap<String, Object> batchAttributes;

    /**
     * 扩展字段
     */
    private Map<String, Object> extendFields;

}
