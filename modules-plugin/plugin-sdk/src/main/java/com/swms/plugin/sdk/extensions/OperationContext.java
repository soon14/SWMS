package com.swms.plugin.sdk.extensions;

import lombok.Data;

@Data
public class OperationContext<T> {

    private T operationObject;

    public OperationContext(T operationObject) {
        this.operationObject = operationObject;
    }

}
