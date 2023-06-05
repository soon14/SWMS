package com.swms.mdm.query.main.data.parameter;

import com.swms.utils.http.parameter.UserSearchParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class WarehouseSearchParam extends UserSearchParam {

    private String warehouseCode;
    private String warehouseName;
}
