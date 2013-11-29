package ua.vvygulyarniy.sqlmock.column;

/** @author Vadim Vygulyarniy @ 22.11.13 10:25 */
public class ColumnInfo {
  private String title;
  private int    type;

  public ColumnInfo(final String title, final int type) {
    this.title = title;
    this.type = type;
  }

  public String getTitle() {
    return title;
  }

  public int getType() {
    return type;
  }
}
