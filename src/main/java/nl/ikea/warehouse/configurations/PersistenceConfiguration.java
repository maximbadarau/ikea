package nl.ikea.warehouse.configurations;

import com.mongodb.MongoClientSettings;
import com.mongodb.connection.SocketSettings;
import nl.ikea.warehouse.listeners.CascadeSaveMongoEventListener;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.concurrent.TimeUnit;

/** MongoDB persistence configuration. */
@Configuration
@EnableMongoRepositories(basePackages = "nl.ikea.warehouse.repositories")
public class PersistenceConfiguration {

  /**
   * {@link MongoClient} bean definition
   *
   * @return {@link MongoClient}
   */
  @Bean
  public MongoClient mongo() {
    MongoClientSettings.Builder builder = MongoClientSettings.builder();
    builder.applyToSocketSettings(
        s ->
            s.connectTimeout(360000, TimeUnit.MILLISECONDS)
                .applySettings(
                    SocketSettings.builder()
                        .connectTimeout(360000, TimeUnit.MILLISECONDS)
                        .readTimeout(360000, TimeUnit.MILLISECONDS)
                        .build()));
    return MongoClients.create(builder.build());
  }

  /**
   * {@link MongoTemplate} bean definition
   *
   * @return {@link MongoTemplate}
   * @throws Exception {@link Exception}
   */
  @Bean
  public MongoTemplate mongoTemplate() throws Exception {
    return new MongoTemplate(mongo(), "warehouse");
  }

  /**
   * {@link CascadeSaveMongoEventListener} bean definition
   *
   * @return {@link CascadeSaveMongoEventListener}
   */
  @Bean
  public CascadeSaveMongoEventListener cascadingMongoEventListener() {
    return new CascadeSaveMongoEventListener();
  }
}
