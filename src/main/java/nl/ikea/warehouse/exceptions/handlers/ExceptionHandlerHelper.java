package nl.ikea.warehouse.exceptions.handlers;

import nl.ikea.warehouse.exceptions.errors.ErrorItem;
import nl.ikea.warehouse.exceptions.errors.ApplicationCustomError;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.util.Collection;
import java.util.function.Function;
import javax.validation.ConstraintViolation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/** Exception handler helper class */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExceptionHandlerHelper {

  /**
   * Wrap {@link ConstraintViolation} into application internal {@link ErrorItem} object.
   *
   * @param constraintViolation {@link ConstraintViolation}
   * @return {@link ErrorItem}
   */
  public static ErrorItem getErrorItem(ConstraintViolation constraintViolation) {
    final String applicationCode = "MSG_IKEA_400";
    return new ErrorItem(
        HttpStatus.BAD_REQUEST.value(), applicationCode, constraintViolation.getMessage());
  }

  /**
   * Generate error payload
   *
   * @param collection violations {@link Collection}
   * @param function {@link Function} functional interface
   * @param <T> type parameter
   * @return {@link T}
   */
  public static <T> ApplicationCustomError generateErrorPayload(
      Collection<T> collection, Function<T, ErrorItem> function) {
    Builder<ErrorItem> builder = ImmutableList.builder();
    collection.forEach(
        obj -> {
          ErrorItem errorItem = function.apply(obj);
          if (errorItem != null) {
            builder.add(errorItem);
          }
        });
    return new ApplicationCustomError(builder.build());
  }
}
