package com.swms.mdm.api.config.constants.copy;

import com.swms.common.utils.dictionary.Dictionary;
import com.swms.common.utils.dictionary.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author sws
 * @Date 2020/12/9 18:00
 * @Description: 是否类型枚举
 */
@SuppressWarnings("ALL")
@Getter
@AllArgsConstructor
@Dictionary
public enum YesOrNo implements IEnum {
    YES("1", "是"),
    NO("0", "否");

    private String code;
    private String desc;

    @Override
    public String getValue() {
        return code;
    }

    @Override
    public String getLabel() {
        return desc;
    }
}
