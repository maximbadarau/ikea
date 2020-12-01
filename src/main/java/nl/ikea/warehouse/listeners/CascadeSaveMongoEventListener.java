package nl.ikea.warehouse.listeners;

import nl.ikea.warehouse.listeners.callbacks.CascadeCallback;
import nl.ikea.warehouse.annotations.CascadeSave;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.util.ReflectionUtils;

/**
 * Mongo event listener. Triggered on cascade persistence of parent entity will explicitly trigger
 * persist for child entities annotated with
 *
 * @see CascadeSave
 * @see AbstractMongoEventListener
 */
public class CascadeSaveMongoEventListener extends AbstractMongoEventListener<Object> {

  private static final Logger LOGGER = LoggerFactory.getLogger(CascadeSaveMongoEventListener.class);

  @Autowired private MongoOperations mongoOperations;

  /**
   * {@inheritDoc}
   *
   * @param event {@link BeforeConvertEvent}
   */
  @Override
  public void onBeforeConvert(BeforeConvertEvent<Object> event) {
    LOGGER.info("Mongo event listener cascade save with: {}", event);
    Object source = event.getSource();
    ReflectionUtils.doWithFields(
        source.getClass(), new CascadeCallback(source, this.mongoOperations));
  }
}
