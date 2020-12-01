package nl.ikea.warehouse.configurations;

import nl.ikea.warehouse.factories.IServiceFactoryBean;
import nl.ikea.warehouse.factories.types.ITypeService;

import java.util.Properties;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Application general configuration.
 */
@Configuration
public class ApplicationConfiguration {

    /**
     * Factory method definition. Manage {@link ITypeService} beans injection
     *
     * @param properties {@link Properties} containing mapping between document extension type and
     *                   bean name.
     * @return {@link FactoryBean}
     */
    @Bean
    public FactoryBean iServiceFactoryBean(
            @Autowired @Qualifier(value = "mapping") Properties properties) {
        ServiceLocatorFactoryBean serviceLocatorFactoryBean = new ServiceLocatorFactoryBean();
        serviceLocatorFactoryBean.setServiceLocatorInterface(IServiceFactoryBean.class);
        serviceLocatorFactoryBean.setServiceMappings(properties);
        return serviceLocatorFactoryBean;
    }

    /**
     * Collect mapping between document extension type and bean name.
     *
     * @return {@link Properties} containing mapping.
     */
    @Bean(name = "mapping")
    public Properties serviceMappings() {
        Properties properties = new Properties();
        properties.setProperty("A", "serviceA");
        properties.setProperty("B", "serviceB");
        return properties;
    }
}
