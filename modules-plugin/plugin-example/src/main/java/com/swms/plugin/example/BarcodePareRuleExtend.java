package com.swms.plugin.example;

import com.swms.mdm.api.config.dto.BarcodeParseRuleDTO;
import com.swms.mdm.extend.IBarcodeParseRulePlugin;
import com.swms.plugin.sdk.config.TenantPluginConfig;
import lombok.extern.slf4j.Slf4j;
import org.pf4j.Extension;

@Slf4j
@Extension
public class BarcodePareRuleExtend implements IBarcodeParseRulePlugin {

    private static final String pluginId = "barcodePlugin-0.0.1";

    @Override
    public void doAfterCreateBarcode(BarcodeParseRuleDTO rule) {
        BarcodeConfig barcodeConfig = TenantPluginConfig.getTenantConfig(pluginId, BarcodeConfig.class);
        log.info("Extending Barcode : doAfterCreateBarcode. bar:{}", barcodeConfig.getNumber());
    }

}
