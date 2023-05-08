package com.swms.tenant.config.config;

import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class CustomTableNamingStrategy extends CamelCaseToUnderscoresNamingStrategy {

    @Override
    public Identifier toPhysicalTableName(Identifier logicalName, JdbcEnvironment jdbcEnvironment) {

        String text = logicalName.getText();

        if (text.endsWith("PO")) {
            logicalName = Identifier.toIdentifier(text.substring(0, text.length() - 2));
        }
        return super.toPhysicalTableName(logicalName, jdbcEnvironment);
    }
}
