package nl.ikea.warehouse.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Service or business layer exception. To be thrown in case of not found resource path error or
 * non-compliance of business rules.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends ApplicationException {

  private static final long serialVersionUID = -5621511388315122255L;

  /** Telescopic constructors definition */
  public ResourceNotFoundException() {
    super();
  }

  /**
   * {@see ServiceException#ServiceException()}
   *
   * @param errorCode application error code {@link String} representation.
   * @param message error message {@link String} representation.
   */
  public ResourceNotFoundException(String errorCode, String message) {
    super(errorCode, message);
  }

  /**
   * {@see ServiceException#ServiceException()}
   *
   * @param errorCode application error code {@link String} representation.
   * @param cause error {@link Throwable} representation.
   */
  public ResourceNotFoundException(String errorCode, Throwable cause) {
    super(errorCode, cause);
  }

  /**
   * {@see ServiceException#ServiceException()}
   *
   * @param errorCode application error code {@link String} representation.
   * @param message error message {@link String} representation.
   * @param cause error {@link Throwable} representation.
   */
  public ResourceNotFoundException(String errorCode, String message, Throwable cause) {
    super(errorCode, message, cause);
  }

  /**
   * {@see ServiceException#ServiceException()}
   *
   * @param errorCode application error code {@link String} representation.
   * @param message error message {@link String} representation.
   * @param cause error {@link Throwable} representation.
   * @param enableSuppression boolean flag.
   * @param writableStackTrace boolean flag.
   */
  public ResourceNotFoundException(
      String errorCode,
      String message,
      Throwable cause,
      boolean enableSuppression,
      boolean writableStackTrace) {
    super(errorCode, message, cause, enableSuppression, writableStackTrace);
  }
}
