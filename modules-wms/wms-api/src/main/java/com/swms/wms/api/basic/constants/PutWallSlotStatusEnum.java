package com.swms.wms.api.basic.constants;

import com.swms.common.utils.dictionary.Dictionary;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Dictionary
public enum PutWallSlotStatusEnum {

    IDLE("空闲"),

    WAITING_BINDING("待绑定"),

    BOUND("已绑定"),

    // 数据库没有待分拨状态， 这里当商品扫码后，商品需要投递的已绑定槽口修改状态为 待分拨
    DISPATCH("待分拨"),

    WAITING_SEAL("待封箱");

    private String descCN;
}
