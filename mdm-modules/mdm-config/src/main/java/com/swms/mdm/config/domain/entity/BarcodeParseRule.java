package com.swms.mdm.config.domain.entity;

import com.google.common.collect.Lists;
import com.swms.mdm.api.config.constants.BusinessFlowEnum;
import com.swms.mdm.api.config.constants.ExecuteTimeEnum;
import com.swms.mdm.api.config.dto.BarcodeParseResult;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@Slf4j
public class BarcodeParseRule {

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

    public List<BarcodeParseResult> parse(String barcode) {
        String unionBarcode = union(barcode);
        List<String> compileResult = compile(unionBarcode);
        return buildResult(compileResult);
    }


    private String union(String barcode) {
        return unionPre + barcode + unionAft;
    }

    private List<String> compile(String unionBarcode) {
        Pattern pattern = Pattern.compile(regularExpression);
        Matcher matcher = pattern.matcher(unionBarcode);
        List<String> result = Lists.newArrayListWithCapacity(resultFields.size());
        if (matcher.find()) {
            try {
                for (int i = 1; i <= resultFields.size(); i++) {
                    result.add(matcher.group(i));
                }
            } catch (Exception e) {
                log.error("barcode rule parse error,regex={},parameter={}size={}", regularExpression, unionBarcode, resultFields.size(), e);
            }
        }
        return result;
    }

    private List<BarcodeParseResult> buildResult(List<String> compileResult) {
        if (CollectionUtils.isEmpty(compileResult)) {
            return null;
        }
        if (compileResult.size() != resultFields.size()) {
            return null;
        }

        List<BarcodeParseResult> barcodeParseResults = Lists.newArrayListWithCapacity(resultFields.size());
        for (int i = 0; i < resultFields.size(); i++) {
            String field = resultFields.get(i);
            String result = compileResult.get(i);
            barcodeParseResults.add(BarcodeParseResult.builder().fileName(field).fileValue(result).build());
        }
        return barcodeParseResults;
    }

    public void enable() {
        this.enable = true;
    }

    public void disable() {
        this.enable = false;
    }
}
