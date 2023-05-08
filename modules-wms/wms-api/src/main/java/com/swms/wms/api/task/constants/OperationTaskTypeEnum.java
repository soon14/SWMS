package com.swms.wms.api.task.constants;

public enum OperationTaskTypeEnum {
    RECEIVING,
    PUT_AWAY,
    PICKING,
    SORTING,
    ONE_STEP_RELOCATION,

    // pick sku from a container in the storage area to relocation area.
    RELOCATION,
    COUNTING,
    RECHECK,
    ;
}
