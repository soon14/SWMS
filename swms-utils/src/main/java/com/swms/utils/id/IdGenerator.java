package com.swms.utils.id;

import lombok.NoArgsConstructor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class IdGenerator implements IdentifierGenerator {

    private static Snowflake snowflake;

    @Autowired
    public IdGenerator(Snowflake snowflake) {
        IdGenerator.snowflake = snowflake;
    }

    public static long generateId() {
        return snowflake.nextId();
    }

    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {
        return generateId();
    }
}
