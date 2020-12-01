package nl.ikea.warehouse.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Data validation exception. To be thrown in case of data transformation error or non-compliance of
 * business rules.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
@ResponseBody
public class BadRequestException extends ApplicationException {

  private static final long serialVersionUID = -7717949087170596361L;

  /** Telescopic constructors definition */
  public BadRequestException() {
    super();
  }

  /**
   * {@see BadRequestException#BadRequestException(errorCode, message)}
   *
   * @param errorCode application error code {@link String} representation.
   * @param message error message {@link String} representation.
   */
  public BadRequestException(String errorCode, String message) {
    super(errorCode, message);
  }

  /**
   * {@see BadRequestException#BadRequestException(errorCode, cause)}
   *
   * @param errorCode application error code {@link String} representation.
   * @param cause error {@link Throwable} representation.
   */
  public BadRequestException(String errorCode, Throwable cause) {
    super(errorCode, cause);
  }

  /**
   * {@see BadRequestException#BadRequestException(errorCode, message, cause)}
   *
   * @param errorCode application error code {@link String} representation.
   * @param message error message {@link String} representation.
   * @param cause error {@link Throwable} representation.
   */
  public BadRequestException(String errorCode, String message, Throwable cause) {
    super(errorCode, message, cause);
  }

  /**
   * {@see BadRequestException#BadRequestException(errorCode, message, cause, enableSuppression,
   * writableStackTrace)}
   *
   * @param errorCode application error code {@link String} representation.
   * @param message error message {@link String} representation.
   * @param cause error {@link Throwable} representation.
   * @param enableSuppression boolean flag.
   * @param writableStackTrace boolean flag.
   */
  public BadRequestException(
      String errorCode,
      String message,
      Throwable cause,
      boolean enableSuppression,
      boolean writableStackTrace) {
    super(errorCode, message, cause, enableSuppression, writableStackTrace);
  }
}
