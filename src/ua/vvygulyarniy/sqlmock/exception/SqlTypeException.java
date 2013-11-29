package ua.vvygulyarniy.sqlmock.exception;

/** @author Vadim Vygulyarniy @ 22.11.13 9:39 */
public class SqlTypeException extends Exception {
  public SqlTypeException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public SqlTypeException(final String message) {
    super(message);
  }

  public SqlTypeException(final Throwable cause) {
    super(cause);
  }
}
