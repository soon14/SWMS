package com.swms.mdm.api.config.dto;

import com.swms.mdm.api.config.constants.BusinessFlowEnum;
import com.swms.mdm.api.config.constants.ExecuteTimeEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class BarcodeParseRuleDTO {

    private Long id;

    @NotEmpty
    private String code;
    @NotEmpty
    private String name;
    @NotNull
    private ExecuteTimeEnum executeTime;
    @NotNull
    private BusinessFlowEnum businessFlow;

    private String ownerCode;
    private String brand;

    private boolean enable;

    private String unionPre;
    private String unionAft;

    private String regularExpression;

    @NotEmpty
    private List<String> resultFields;

    private Long version;
}
