package com.swms.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 操作台状态
 *
 * @author pengboran
 */
@AllArgsConstructor
@Getter
public enum WorkStationStatusEnum {

    ONLINE("在线"),

    PAUSED("暂停"),

    OFFLINE("离线");

    private final String descCN;
}
