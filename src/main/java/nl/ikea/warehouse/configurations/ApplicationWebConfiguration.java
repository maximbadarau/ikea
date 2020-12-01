package nl.ikea.warehouse.configurations;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class ApplicationWebConfiguration extends WebMvcConfigurationSupport {

  /**
   * {@link Docket} bean definition.
   *
   * @return {@link Docket}
   */
  @Bean
  public Docket generalAPIs() {
    return new Docket(DocumentationType.SWAGGER_2)
        .useDefaultResponseMessages(false)
        .apiInfo(
            new ApiInfoBuilder()
                .title("Ikea Warehouse Service.")
                .description("Warehouse Service REST API documentation.")
                .build())
        .select()
        .apis(RequestHandlerSelectors.basePackage("nl.ikea.warehouse.controllers"))
        .paths(PathSelectors.ant("/**"))
        .build();
  }

  /**
   * Adding resource handlers for serving static resources.
   *
   * @see ResourceHandlerRegistry
   */
  @Override
  protected void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry
        .addResourceHandler("swagger-ui.html")
        .addResourceLocations("classpath:/META-INF/resources/");
    registry
        .addResourceHandler("/webjars/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/");
  }

  /**
   * Fixing JSON mapping exception in regards of custom data and enums. Modifying the list of
   * converters after it was configured. This may be useful for example to allow default converters
   * to be registered and inserting custom converters to extend functionality.
   *
   * @param converters the list of configured converters to extend
   */
  @Override
  public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    for (HttpMessageConverter converter : converters) {
      if (converter instanceof AbstractJackson2HttpMessageConverter) {
        final ObjectMapper objectMapper =
            ((AbstractJackson2HttpMessageConverter) converter).getObjectMapper();
        objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        objectMapper.setSerializationInclusion(Include.NON_EMPTY);
      }
    }
  }
}
