package ua.vvygulyarniy.sqlmock.datamap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/** @author Vadim Vygulyarniy @ 25.11.13 11:32 */
public class DataMapBuilder {
  private String[]   columns;
  private Reader     reader;
  private Object[][] data;

  public DataMapBuilder withColumns(String... columnNames) {
    columns = columnNames;
    return this;
  }

  public DataMapBuilder fromFile(File file) throws FileNotFoundException {
    reader = new BufferedReader(new FileReader(file));
    data = null;
    return this;
  }

  public DataMapBuilder fromFile(String fileName) throws FileNotFoundException {
    fromFile(new File(fileName));
    data = null;
    return this;
  }

  public DataMapBuilder fromReader(Reader reader) {
    this.reader = reader;
    data = null;
    return this;
  }

  public DataMapBuilder withData(Object[][] data) {
    this.data = data;
    return this;
  }

  public DataMap build() throws IOException {
    DataMap map = new DataMap(columns);
    if (data != null) {
      map.parse(data);
    } else {
      map.parseCsv(reader);
    }
    return map;
  }

}
