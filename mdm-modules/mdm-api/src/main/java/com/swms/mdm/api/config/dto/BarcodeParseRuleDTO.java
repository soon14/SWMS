package com.swms.mdm.api.config.dto;

import com.swms.mdm.api.config.constants.BusinessFlowEnum;
import com.swms.mdm.api.config.constants.ExecuteTimeEnum;
import lombok.Data;

import java.util.List;

@Data
public class BarcodeParseRuleDTO {

    private Long id;

    private String code;
    private String name;

    private String ownerCode;

    private ExecuteTimeEnum executeTime;
    private BusinessFlowEnum businessFlow;

    private String brand;

    private boolean enable;

    private String unionPre;
    private String unionAft;

    private String regularExpression;

    private List<String> resultFields;
}
