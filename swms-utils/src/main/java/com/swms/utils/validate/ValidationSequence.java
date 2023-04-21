package com.swms.utils.validate;

import jakarta.validation.GroupSequence;

@GroupSequence({ValidationSequence.FirstGroup.class, ValidationSequence.SecondGroup.class, ValidationSequence.ThirdGroup.class})
public interface ValidationSequence {

    public interface FirstGroup {
    }

    public interface SecondGroup {
    }

    public interface ThirdGroup {
    }
}
