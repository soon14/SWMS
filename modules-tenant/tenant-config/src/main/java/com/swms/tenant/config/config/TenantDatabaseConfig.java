package com.swms.tenant.config.config;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This is the tenant data sources configuration which sets up the multitenancy.
 */
@Configuration
@EnableTransactionManagement
public class TenantDatabaseConfig {

    @Bean(name = "tenantJpaVendorAdapter")
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    @Primary
    public EntityManagerFactory entityManagerFactory(LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
        return entityManagerFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    /**
     * The multi tenant connection provider
     *
     * @return
     */
    @Bean
    public MultiTenantConnectionProvider multiTenantConnectionProvider() {
        // Autowire the multi connection provider
        return new DataSourceBasedMultiTenantConnectionProviderImpl();
    }

    /**
     * The current tenant identifier resolver
     *
     * @return
     */
    @Bean
    public CurrentTenantIdentifierResolver currentTenantIdentifierResolver() {
        return new CurrentTenantIdentifierResolverImpl();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
        AbstractDataSourceBasedMultiTenantConnectionProviderImpl multiTenantConnectionProvider,
        CurrentTenantIdentifierResolver currentTenantIdentifierResolver, JpaProperties jpaProperties) {

        Map<String, Object> hibernateProps = new LinkedHashMap<>(jpaProperties.getProperties());
        hibernateProps.put(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider);
        hibernateProps.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolver);
        hibernateProps.put(AvailableSettings.PHYSICAL_NAMING_STRATEGY, CustomTableNamingStrategy.class.getName());
        hibernateProps.put("hibernate.hbm2ddl.auto", "update");
        hibernateProps.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setPackagesToScan("com.swms");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaPropertyMap(hibernateProps);

        return em;
    }
}
