package com.swms.mdm.main.data.controller.parameter;

import com.swms.mdm.api.main.data.dto.BarcodeDTO;
import com.swms.mdm.api.main.data.dto.SkuAttributeDTO;
import com.swms.mdm.api.main.data.dto.SkuConfigDTO;
import com.swms.mdm.api.main.data.dto.SkuMainDataDTO;
import com.swms.mdm.api.main.data.dto.SkuPackageDTO;
import com.swms.mdm.api.main.data.dto.VolumeDTO;
import com.swms.mdm.api.main.data.dto.WeightDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class SkuMainDataVO extends SkuMainDataDTO {

    private Long grossWeight;
    private Long netWeight;

    private Long volume;
    private Long height;
    private Long width;
    private Long length;
    private String imageUrl;
    private String unit;

    private String skuFirstCategory;
    private String skuSecondCategory;
    private String skuThirdCategory;

    private String skuAttributeCategory;
    private String skuAttributeSubCategory;

    private boolean enableSn;
    private boolean enableEffective;

    private Integer shelfLife;
    private Integer effectiveDays;

    private String barcodeRuleCode;

    private String heat;
    private boolean calculateHeat;

    private boolean noBarcode;

    private List<String> barcodes;

    private List<SkuPackageDTO.SkuPackageDetail> skuPackageDetails;


    @Override
    public WeightDTO getWeight() {
        return new WeightDTO(grossWeight, netWeight);
    }

    @Override
    public VolumeDTO getVolumeDTO() {
        return new VolumeDTO(volume, height, width, length);
    }

    @Override
    public SkuAttributeDTO getSkuAttribute() {
        return new SkuAttributeDTO(imageUrl, unit, skuFirstCategory, skuSecondCategory,
            skuThirdCategory, skuAttributeCategory, skuAttributeSubCategory);
    }

    @Override
    public SkuConfigDTO getSkuConfig() {
        return new SkuConfigDTO(enableSn, enableEffective, shelfLife, effectiveDays, barcodeRuleCode, heat, calculateHeat, noBarcode);
    }

    @Override
    public SkuPackageDTO getSkuPackage() {
        return new SkuPackageDTO(skuPackageDetails);
    }

    @Override
    public BarcodeDTO getSkuBarcode() {
        return new BarcodeDTO(barcodes);
    }

}
