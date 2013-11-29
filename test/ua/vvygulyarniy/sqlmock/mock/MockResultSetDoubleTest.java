package ua.vvygulyarniy.sqlmock.mock;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ua.vvygulyarniy.sqlmock.column.ColumnInfo;
import ua.vvygulyarniy.sqlmock.datamap.DataMap;

public class MockResultSetDoubleTest {
  @DataProvider(name = "doubleValid")
  public Object[][] getValidData() {
    return new Object[][]{
        {123.01, 123.01},
        {100, 100},
        {100.0, 100},
        {"13.25", 13.25},
        {"125", 125},
        {100.123132, 100.123132},
        {"100.123123", 100.123123},
        {null, 0}
    };
  }

  @DataProvider(name = "doubleWrong")
  public Object[][] getWrongData() {
    return new Object[][]{
        {"test"},
        {new Date()},
        {"100.25 w"}
    };
  }

  @Test(dataProvider = "doubleValid")
  public void testGetDouble(Object value, double expected) throws Exception {
    DataMap map = new DataMap(new ColumnInfo("double1", Types.NUMERIC));
    map.addRow(value);

    MockResultSet rs = new MockResultSet(map);

    assertTrue(rs.next());
    double indexValue = rs.getDouble(1);
    double titleValue = rs.getDouble("double1");
    assertEquals(indexValue, titleValue);
    assertEquals(titleValue, expected);
  }

  @Test(expectedExceptions = SQLException.class, dataProvider = "doubleWrong")
  public void testGetDoubleWrong(Object value) throws Exception {
    DataMap map = new DataMap(new ColumnInfo("double1", Types.NUMERIC));
    map.addRow(value);

    MockResultSet rs = new MockResultSet(map);

    assertTrue(rs.next());
    rs.getDouble(1);
  }

  @Test(expectedExceptions = SQLException.class, dataProvider = "doubleWrong")
  public void testGetDoubleWrongTitle(Object value) throws Exception {
    DataMap map = new DataMap(new ColumnInfo("double1", Types.NUMERIC));
    map.addRow(value);

    MockResultSet rs = new MockResultSet(map);

    assertTrue(rs.next());
    rs.getDouble("double1");
  }
}