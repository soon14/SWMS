package com.swms.mdm.api.config.dto;

import com.swms.mdm.api.config.constants.BusinessFlowEnum;
import com.swms.mdm.api.config.constants.ExecuteTimeEnum;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BarcodeParseRuleDTO {

    private Long id;

    private String code;
    private String name;

    private String customerCode;

    private ExecuteTimeEnum executeTime;
    private BusinessFlowEnum businessFlow;

    private String brand;

    private boolean enable;

    private String unionPre;
    private String unionAft;

    private String regularExpression;

    private List<String> resultFields;
}
