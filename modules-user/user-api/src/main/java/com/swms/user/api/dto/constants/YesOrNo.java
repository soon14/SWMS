package com.swms.user.api.dto.constants;

import com.swms.common.utils.dictionary.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author sws
 */
@SuppressWarnings("ALL")
@Getter
@AllArgsConstructor
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
