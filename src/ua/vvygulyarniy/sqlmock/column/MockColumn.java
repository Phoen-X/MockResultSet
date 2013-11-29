package ua.vvygulyarniy.sqlmock.column;

import java.util.ArrayList;
import java.util.List;

/** @author Vadim Vygulyarniy @ 22.11.13 10:01 */
public class MockColumn {
  private String title;
  private List<Object> values = new ArrayList<Object>();

  public MockColumn(final String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }

  public void add(Object value) {
    values.add(value);
  }

  public Object get(final int row) {
    if (row > values.size() - 1)
      return null;
    return values.get(row);
  }

  public int getRowCount() {
    return values.size();
  }

  @Override
  public String toString() {
    return "column: " + title + ". " + getRowCount() + "rows";
  }
}
