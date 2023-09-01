package com.swms.common.utils.exception.code_enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PluginErrorDescEnum implements IBaseError {

    PLUGIN_INSTALL_ERROR("PL001001", "plugin {0} install error"),
    PLUGIN_START_ERROR("PL001002", "plugin {0} start error"),
    PLUGIN_STOP_ERROR("PL001003", "plugin {0} stop error");


    private final String code;

    private final String desc;
}
