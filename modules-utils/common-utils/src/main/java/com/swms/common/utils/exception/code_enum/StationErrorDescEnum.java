package com.swms.common.utils.exception.code_enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StationErrorDescEnum implements IBaseError {

    //station error SAT01
    STATION_BASE_ERROR("SAT010001", "station base error"),

    //work station location error SAT02


    //put wall error SAT03
    PUT_WALL_SLOT_ORDERS_EXIST("SAT030001", "put wall slot orders exist"),
    PUT_WALL_SLOT_NOT_EXIST("SAT030002", "put wall slot {0} not exist"),
    PUT_WALL_SLOT_STATUS_ABNORMAL("SAT030004", "put wall slot {0} status {1} abnormal"),


    //work station config error SAT04


    //work station rules error SAT05

    ;


    private final String code;
    private final String desc;
}
