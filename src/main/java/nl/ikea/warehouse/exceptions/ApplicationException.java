package nl.ikea.warehouse.exceptions;

import lombok.Getter;

/** Generic exception. */
@Getter
public class ApplicationException extends RuntimeException {

  private static final long serialVersionUID = -1629644643880210651L;

  private final String errorCode;

  /** Telescopic constructors definition */
  public ApplicationException() {
    super();
    this.errorCode = null;
  }

  /**
   * {@see ApplicationException#ApplicationException(message)}
   *
   * @param message error message {@link String} representation.
   */
  public ApplicationException(String message) {
    super(message);
    this.errorCode = null;
  }

  /**
   * {@see ApplicationException#ApplicationException()}
   *
   * @param errorCode application error code {@link String} representation.
   * @param message error message {@link String} representation.
   */
  public ApplicationException(String errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

  /**
   * {@see ApplicationException#ApplicationException()}
   *
   * @param errorCode application error code {@link String} representation.
   * @param cause error {@link Throwable} representation.
   */
  public ApplicationException(String errorCode, Throwable cause) {
    super(cause);
    this.errorCode = errorCode;
  }

  /**
   * {@see ApplicationException#ApplicationException()}
   *
   * @param errorCode application error code {@link String} representation.
   * @param message error message {@link String} representation.
   * @param cause error {@link Throwable} representation.
   */
  public ApplicationException(String errorCode, String message, Throwable cause) {
    super(message, cause);
    this.errorCode = errorCode;
  }

  /**
   * {@see ApplicationException#ApplicationException()}
   *
   * @param errorCode application error code {@link String} representation.
   * @param message error message {@link String} representation.
   * @param cause error {@link Throwable} representation.
   * @param enableSuppression boolean flag.
   * @param writableStackTrace boolean flag.
   */
  public ApplicationException(
      String errorCode,
      String message,
      Throwable cause,
      boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
    this.errorCode = errorCode;
  }
}
