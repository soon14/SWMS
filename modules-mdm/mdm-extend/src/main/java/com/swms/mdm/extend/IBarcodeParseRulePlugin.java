package com.swms.mdm.extend;

import com.swms.mdm.api.config.dto.BarcodeParseRuleDTO;
import org.pf4j.ExtensionPoint;

public interface IBarcodeParseRulePlugin extends ExtensionPoint {
    void doAfterCreateBarcode(BarcodeParseRuleDTO rule);
}
