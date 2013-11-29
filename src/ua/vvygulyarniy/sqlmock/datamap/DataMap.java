package ua.vvygulyarniy.sqlmock.datamap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import ua.vvygulyarniy.sqlmock.column.ColumnInfo;
import ua.vvygulyarniy.sqlmock.column.MockColumn;
import ua.vvygulyarniy.sqlmock.exception.ColumnNotFoundException;

/** @author Vadim Vygulyarniy @ 22.11.13 10:17 */
public class DataMap {

  private ArrayList<MockColumn> columnList;
  int rows = 0;
  private static final String NULL_VALUE = "(null)".intern();

  public DataMap(final ColumnInfo... columns) {
    if (columns == null)
      throw new IllegalArgumentException("columns should not be null");

    this.columnList = new ArrayList<MockColumn>(columns.length);
    for (ColumnInfo column : columns) {
      columnList.add(getColumnMock(column));
    }
  }

  public DataMap(final String... columns) {
    if (columns == null)
      throw new IllegalArgumentException("columns should not be null");

    this.columnList = new ArrayList<MockColumn>(columns.length);
    for (String column : columns) {
      columnList.add(getColumnMock(new ColumnInfo(column, Types.VARCHAR)));
    }
  }

  private MockColumn getColumnMock(final ColumnInfo columnInfo) {
    final String title = columnInfo.getTitle();
    return new MockColumn(title);
  }

  public MockColumn getColumn(int index) throws ColumnNotFoundException {
    try {
      final MockColumn mockColumn = columnList.get(index);
      if (mockColumn == null)
        throw new Exception(String.valueOf(index));
      return mockColumn;
    } catch (Exception e) {
      throw new ColumnNotFoundException(String.valueOf(index));
    }
  }

  public MockColumn getColumn(String title) throws ColumnNotFoundException {
    for (MockColumn mockColumn : columnList) {
      if (mockColumn.getTitle().equals(title))
        return mockColumn;
    }

    throw new ColumnNotFoundException(title);
  }

  public Object getValue(int row, int column) throws ColumnNotFoundException {
    return getColumn(column).get(row);
  }

  public Object getValue(int row, String columnTitle) throws ColumnNotFoundException {
    return getColumn(columnTitle).get(row);
  }

  public void addRow(Object... rowData) {
    if (rowData.length > columnList.size())
      throw new IllegalArgumentException("given values count is more than columns count");
    for (int i = 0; i < columnList.size(); i++) {
      MockColumn column = columnList.get(i);
      if (i < rowData.length)
        column.add(rowData[i]);
      else
        column.add(null);
    }
    rows++;
  }

  public int getRowCount() {
    return rows;
  }

  public int getColumnIndex(final String columnLabel) {
    for (int i = 0; i < columnList.size(); i++) {
      MockColumn col = columnList.get(i);
      if (col.getTitle().equals(columnLabel))
        return i;
    }
    return -100500;
  }

  public void parse(Object[][] data) {
    for (Object[] objects : data) {
      addRow(objects);
    }
  }

  public void parseCsv(File file) throws IOException {
    parseCsv(new BufferedReader(new FileReader(file)));
  }

  public void parseCsv(String fileName) throws IOException {
    parseCsv(new File(fileName));
  }

  public void parseCsv(Reader reader) throws IOException {
    CSVParser parser = null;
    try {
      parser = new CSVParser(reader, CSVFormat.EXCEL);

      List<CSVRecord> records = parser.getRecords();
      for (CSVRecord record : records) {
        String[] row = new String[record.size()];
        for (int i = 0; i < record.size(); i++) {
          row[i] = record.get(i);
        }
        addRow(row);
      }
    } finally {
      if (parser != null) {
        parser.close();
      }
    }
  }
}
