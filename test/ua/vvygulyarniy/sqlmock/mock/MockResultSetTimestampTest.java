package ua.vvygulyarniy.sqlmock.mock;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ua.vvygulyarniy.sqlmock.column.ColumnInfo;
import ua.vvygulyarniy.sqlmock.datamap.DataMap;

public class MockResultSetTimestampTest {
  public MockResultSetTimestampTest() {
  }

  @DataProvider(name = "timestampData")
  public Object[][] getTimestamps() {
    Date currDate = new Date();
    Timestamp currTimestamp = new Timestamp(currDate.getTime());
    final String stringTimestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(currDate);
    return new Object[][]{
        {stringTimestamp, currTimestamp},
        {new Timestamp(currDate.getTime()), currTimestamp},
        {null, null}
    };
  }

  @DataProvider(name = "timestampWrong")
  public Object[][] getWrongTimestamps() {
    return new Object[][]{
        {new Date()},
        {"test"},
        {"20131101 14:00:00"},
        {"2013-01-01"},
        {""},
        {14}
    };
  }

  @Test(dataProvider = "timestampData")
  public void testGetTimestamp(Object value, Timestamp expected) throws Exception {
    DataMap map = new DataMap(new ColumnInfo("date1", Types.TIMESTAMP));
    //noinspection RedundantArrayCreation
    map.addRow(new Object[]{value});

    MockResultSet rs = new MockResultSet(map);

    Assert.assertTrue(rs.next());
    Assert.assertEquals(rs.getTimestamp(1), rs.getTimestamp("date1"));
    Assert.assertEquals(rs.getTimestamp(1), expected);
  }

  @Test(expectedExceptions = SQLException.class, dataProvider = "timestampWrong")
  public void testGetTimestampWrong(Object value) throws Exception {
    DataMap map = new DataMap(new ColumnInfo("date1", Types.TIMESTAMP));
    map.addRow(value);

    MockResultSet rs = new MockResultSet(map);

    Assert.assertTrue(rs.next());
    rs.getTimestamp(1);
  }

  @Test(expectedExceptions = SQLException.class, dataProvider = "timestampWrong")
  public void testGetTimestampWrongTitle(Object value) throws Exception {
    DataMap map = new DataMap(new ColumnInfo("date1", Types.TIMESTAMP));
    map.addRow(value);

    MockResultSet rs = new MockResultSet(map);

    Assert.assertTrue(rs.next());
    rs.getTimestamp("date1");
  }
}