package com.swms.mdm.extend;

import com.swms.mdm.api.config.dto.BarcodeParseRuleDTO;

public interface IBarcodeParseRulePlugin {
    void doAfterCreateBarcode(BarcodeParseRuleDTO rule);
}
