package com.swms.mdm.extend;

import com.swms.mdm.api.config.dto.BarcodeParseRuleDTO;
import com.swms.plugin.sdk.extensions.IPlugin;

public interface IBarcodeParseRulePlugin extends IPlugin<BarcodeParseRuleDTO, Void> {
    void afterDoOperation(BarcodeParseRuleDTO barcodeParseRuleDTO);
}
