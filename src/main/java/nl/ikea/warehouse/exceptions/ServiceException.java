package nl.ikea.warehouse.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Service or business layer exception. To be thrown in case of data transformation error or
 * non-compliance of business rules.
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ServiceException extends ApplicationException {

  private static final long serialVersionUID = -1394734680224014442L;

  /** Telescopic constructors definition */
  public ServiceException(String errorCode) {
    super();
  }

  /**
   * {@see ServiceException#ServiceException()}
   *
   * @param errorCode application error code {@link String} representation.
   * @param message error message {@link String} representation.
   */
  public ServiceException(String errorCode, String message) {
    super(errorCode, message);
  }

  /**
   * {@see ServiceException#ServiceException()}
   *
   * @param errorCode application error code {@link String} representation.
   * @param cause error {@link Throwable} representation.
   */
  public ServiceException(String errorCode, Throwable cause) {
    super(errorCode, cause);
  }

  /**
   * {@see ServiceException#ServiceException()}
   *
   * @param errorCode application error code {@link String} representation.
   * @param message error message {@link String} representation.
   * @param cause error {@link Throwable} representation.
   */
  public ServiceException(String errorCode, String message, Throwable cause) {
    super(errorCode, message, cause);
  }

  /**
   * {@see ServiceException#ServiceException()}
   *
   * @param message error message {@link String} representation.
   * @param errorCode application error code {@link String} representation.
   * @param message error message {@link String} representation.
   * @param cause error {@link Throwable} representation.
   * @param enableSuppression boolean flag.
   * @param writableStackTrace boolean flag.
   */
  public ServiceException(
      String errorCode,
      String message,
      Throwable cause,
      boolean enableSuppression,
      boolean writableStackTrace) {
    super(errorCode, message, cause, enableSuppression, writableStackTrace);
  }
}
