package ua.vvygulyarniy.sqlmock.exception;

/** @author Vadim Vygulyarniy @ 22.11.13 11:03 */
public class ColumnNotFoundException extends Exception {
  private String column;

  public ColumnNotFoundException(String columnName) {
    this.column = columnName;
  }

  @Override
  public String getMessage() {
    return "Column " + column + " not found";
  }
}
