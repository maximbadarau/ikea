package nl.ikea.warehouse.listeners.callbacks;

import java.lang.reflect.Field;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.util.ReflectionUtils;

/**
 * Annotated field/property markup callback.
 *
 * @see ReflectionUtils.FieldCallback
 */
@Getter
public class FieldCallback implements ReflectionUtils.FieldCallback {

  private boolean idFound;

  /**
   * {@inheritDoc}
   *
   * @param field {@link Field}
   */
  @Override
  public void doWith(final Field field) throws IllegalArgumentException, IllegalAccessException {
    ReflectionUtils.makeAccessible(field);
    if (field.isAnnotationPresent(Id.class)) {
      this.idFound = true;
    }
  }
}
