package ua.vvygulyarniy.sqlmock.mock;

import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ua.vvygulyarniy.sqlmock.column.ColumnInfo;
import ua.vvygulyarniy.sqlmock.datamap.DataMap;

public class MockResultSetLongTest {
  @DataProvider(name = "longValid")
  public Object[][] getValidData() {
    Date currDate = new Date();
    return new Object[][]{
        {123, 123},
        {"124", 124},
        {new Long(100), 100},
        {null, 0},
        {0, 0}
    };
  }

  @DataProvider(name = "longWrong")
  public Object[][] getWrongData() {
    return new Object[][]{
        {"test"},
        {new Date()}
    };
  }

  @Test(dataProvider = "longValid")
  public void testGetLong(Object value, long expected) throws Exception {
    DataMap map = new DataMap(new ColumnInfo("int1", Types.NUMERIC));
    map.addRow(value);

    MockResultSet rs = new MockResultSet(map);

    Assert.assertTrue(rs.next());
    long indexValue = rs.getLong(1);
    long titleValue = rs.getLong("int1");
    Assert.assertEquals(indexValue, titleValue);
    Assert.assertEquals(rs.getLong(1), expected);
  }

  @Test(expectedExceptions = SQLException.class, dataProvider = "longWrong")
  public void testGetLongWrong(Object value) throws Exception {
    DataMap map = new DataMap(new ColumnInfo("int1", Types.NUMERIC));
    map.addRow(value);

    MockResultSet rs = new MockResultSet(map);

    Assert.assertTrue(rs.next());
    rs.getLong(1);
  }

  @Test(expectedExceptions = SQLException.class, dataProvider = "longWrong")
  public void testGetLongWrongTitle(Object value) throws Exception {
    DataMap map = new DataMap(new ColumnInfo("int1", Types.NUMERIC));
    map.addRow(value);

    MockResultSet rs = new MockResultSet(map);

    Assert.assertTrue(rs.next());
    rs.getLong("int1");
  }
}