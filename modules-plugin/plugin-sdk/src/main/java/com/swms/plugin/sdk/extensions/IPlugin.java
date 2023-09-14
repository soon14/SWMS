package com.swms.plugin.sdk.extensions;

import org.pf4j.ExtensionPoint;

public interface IPlugin<T> extends ExtensionPoint {

    default void initialize() {
    }

    default void beforeDoOperation(OperationContext<T> operationContext) {

    }

    default void afterDoOperation(OperationContext<T> operationContext) {

    }

    void destory();
}
