package com.swms.mdm.config.controller.parameter;

import com.swms.utils.http.parameter.UserSearchParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DictionarySearchParameter extends UserSearchParam {

    private String code;
    private String name;
}
