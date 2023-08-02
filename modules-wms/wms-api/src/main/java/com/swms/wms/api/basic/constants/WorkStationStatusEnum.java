package com.swms.wms.api.basic.constants;

import com.swms.common.utils.dictionary.Dictionary;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 操作台状态
 *
 * @author sws
 */
@AllArgsConstructor
@Getter
@Dictionary
public enum WorkStationStatusEnum {

    ONLINE("在线"),

    PAUSED("暂停"),

    OFFLINE("离线");

    private final String descCN;
}
