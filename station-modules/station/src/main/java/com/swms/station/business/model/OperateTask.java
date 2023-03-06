package com.swms.station.business.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class OperateTask {
    private String skuCode;
    private String skuName;
    private String skuUrl;
    private Long skuBatchId;
    private Map<String, Object> batchAttributeJson;
//    private List<SnInfo> snInfos;
//    private List<PageConfigDetail> skuDesc;

    private String sourceContainerCode;
    private String sourceContainerSlot;

    private boolean processed;

    private Integer requiredQty;
    private Integer operatedQty;
    private Integer toBeOperatedQty;

    private String targetLocationCode;
    private String targetContainerCode;
    private String targetContainerSlot;
}
