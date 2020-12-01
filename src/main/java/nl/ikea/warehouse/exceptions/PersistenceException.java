package nl.ikea.warehouse.exceptions;

/** Persistence layer exception. To be thrown in case of data base malfunction or data absence. */
public class PersistenceException extends ApplicationException {

  private static final long serialVersionUID = -5962976847844000615L;

  /** {@inheritDoc} */
  public PersistenceException() {
    super();
  }

  /**
   * {@inheritDoc}
   *
   * @param errorCode {@link String}
   * @param message {@link String}
   */
  public PersistenceException(String errorCode, String message) {
    super(errorCode, message);
  }

  /**
   * {@inheritDoc}
   *
   * @param errorCode {@link String}
   * @param cause {@link Throwable}
   */
  public PersistenceException(String errorCode, Throwable cause) {
    super(errorCode, cause);
  }

  /**
   * {@inheritDoc}
   *
   * @param errorCode {@link String}
   * @param message {@link String}
   * @param cause {@link Throwable}
   */
  public PersistenceException(String errorCode, String message, Throwable cause) {
    super(errorCode, message, cause);
  }

  /**
   * {@inheritDoc}
   *
   * @param errorCode {@link String}
   * @param message {@link String}
   * @param cause {@link Throwable}
   * @param enableSuppression {@link Boolean}
   * @param writableStackTrace {@link Boolean}
   */
  public PersistenceException(
      String errorCode,
      String message,
      Throwable cause,
      boolean enableSuppression,
      boolean writableStackTrace) {
    super(errorCode, message, cause, enableSuppression, writableStackTrace);
  }
}
