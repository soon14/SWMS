package com.swms.utils.id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IdGenerator {

    private static Snowflake snowflake;

    @Autowired
    public IdGenerator(Snowflake snowflake) {
        IdGenerator.snowflake = snowflake;
    }

    public static long generateId() {
        return snowflake.nextId();
    }
}
