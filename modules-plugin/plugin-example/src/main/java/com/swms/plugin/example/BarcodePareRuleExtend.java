package com.swms.plugin.example;

import com.swms.mdm.api.config.dto.BarcodeParseRuleDTO;
import com.swms.mdm.extend.IBarcodeParseRulePlugin;
import lombok.extern.slf4j.Slf4j;
import org.pf4j.Extension;

@Slf4j
@Extension
public class BarcodePareRuleExtend implements IBarcodeParseRulePlugin {

    @Override
    public void doAfterCreateBarcode(BarcodeParseRuleDTO rule) {

        log.info("Extending Barcode : doAfterCreateBarcode.");
    }
}
