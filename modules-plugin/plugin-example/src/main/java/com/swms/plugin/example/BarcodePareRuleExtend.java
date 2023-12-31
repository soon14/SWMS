package com.swms.plugin.example;

import com.swms.mdm.api.config.dto.BarcodeParseRuleDTO;
import com.swms.plugin.extend.mdm.config.IBarcodeParseRulePlugin;
import com.swms.plugin.sdk.config.TenantPluginConfig;
import lombok.extern.slf4j.Slf4j;
import org.pf4j.Extension;

@Slf4j
@Extension
public class BarcodePareRuleExtend implements IBarcodeParseRulePlugin {

    private static final String PLUGIN_ID = "barcodePlugin-0.0.1";

    @Override
    public void afterDoOperation(BarcodeParseRuleDTO rule) {
        BarcodeConfig barcodeConfig = TenantPluginConfig.getTenantConfig(PLUGIN_ID, BarcodeConfig.class);
        log.info("Extending Barcode : doAfterCreateBarcode. bar:{}", barcodeConfig.getNumber());
    }

}
