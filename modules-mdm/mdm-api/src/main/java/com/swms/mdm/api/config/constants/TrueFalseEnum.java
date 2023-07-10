package com.swms.mdm.api.config.constants;

import com.swms.utils.dictionary.Dictionary;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Dictionary
public enum TrueFalseEnum {
    TRUE(true), FALSE(false);
    private boolean value;
}
