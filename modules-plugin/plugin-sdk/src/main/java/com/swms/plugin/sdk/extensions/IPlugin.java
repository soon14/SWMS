package com.swms.plugin.sdk.extensions;

import org.pf4j.ExtensionPoint;

public interface IPlugin<T, R> extends ExtensionPoint {

    default void initialize() {
    }

    default void beforeDoOperation(OperationContext<T> operationContext) {

    }

    default R doOperation(OperationContext<T> operationContext) {
        return null;
    }

    default void afterDoOperation(OperationContext<T> operationContext) {

    }

    default void destory() {
    }

}
