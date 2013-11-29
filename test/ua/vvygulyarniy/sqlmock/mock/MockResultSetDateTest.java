package ua.vvygulyarniy.sqlmock.mock;

import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ua.vvygulyarniy.sqlmock.column.ColumnInfo;
import ua.vvygulyarniy.sqlmock.datamap.DataMap;

public class MockResultSetDateTest {
  public MockResultSetDateTest() {
  }

  @DataProvider(name = "sqlDateData")
  public Object[][] getSqlDates() {
    Date currDate = new Date();

    final java.sql.Date sqlDate = new java.sql.Date(currDate.getTime());
    final String stringDate = new SimpleDateFormat("yyyy-MM-dd").format(currDate);
    return new Object[][]{
        {sqlDate},
        {stringDate},
        {null}
    };
  }

  @DataProvider(name = "datesWrong")
  public Object[][] getWrongDates() {
    return new Object[][]{
        {new Date()},
        {"test"},
        {"20130101"}
    };
  }

  @Test(dataProvider = "sqlDateData")
  public void testGetDate(Object values) throws Exception {
    DataMap map = new DataMap(new ColumnInfo("date1", Types.TIMESTAMP));

    //noinspection RedundantArrayCreation
    map.addRow(new Object[]{values});

    MockResultSet rs = new MockResultSet(map);

    Assert.assertTrue(rs.next());
    Assert.assertEquals(rs.getDate(1), rs.getDate("date1"));
    Assert.assertEquals(String.valueOf(rs.getDate(1)), String.valueOf(values));
  }

  @Test(expectedExceptions = SQLException.class, dataProvider = "datesWrong")
  public void testGetDateWrong(Object value) throws Exception {
    DataMap map = new DataMap(new ColumnInfo("date1", Types.TIMESTAMP));
    Date currDate = new Date();

    map.addRow(value);

    MockResultSet rs = new MockResultSet(map);

    Assert.assertTrue(rs.next());
    Assert.assertEquals(rs.getDate(1), new java.sql.Date(currDate.getTime()));
  }

  @Test(expectedExceptions = SQLException.class, dataProvider = "datesWrong")
  public void testGetDateWrongTitle(Object value) throws Exception {
    DataMap map = new DataMap(new ColumnInfo("date1", Types.TIMESTAMP));
    Date currDate = new Date();

    map.addRow(value);

    MockResultSet rs = new MockResultSet(map);

    Assert.assertTrue(rs.next());
    Assert.assertEquals(rs.getDate("date1"), new java.sql.Date(currDate.getTime()));
  }
}