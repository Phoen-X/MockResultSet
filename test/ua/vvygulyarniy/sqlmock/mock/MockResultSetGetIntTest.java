package ua.vvygulyarniy.sqlmock.mock;

import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ua.vvygulyarniy.sqlmock.column.ColumnInfo;
import ua.vvygulyarniy.sqlmock.datamap.DataMap;

public class MockResultSetGetIntTest {

  @DataProvider(name = "intPositive")
  public Object[][] getPositiveData() {
    return new Object[][]{
        {123, 123},
        {"456", 456},
        {new Integer(123), 123},
        {null, 0},
        {0, 0}
    };
  }

  @DataProvider(name = "intWrong")
  public Object[][] getWrongData() {
    return new Object[][]{
        {"test"},
        {new Date()}
    };
  }

  @Test(dataProvider = "intPositive")
  public void testGetInt(Object value, int expected) throws Exception {
    DataMap map = new DataMap(new ColumnInfo("int1", Types.NUMERIC));
    //noinspection RedundantArrayCreation
    map.addRow(value);

    MockResultSet rs = new MockResultSet(map);

    Assert.assertTrue(rs.next());
    Assert.assertEquals(rs.getInt(1), rs.getInt("int1"));
    Assert.assertEquals(rs.getInt(1), expected);
  }

  @Test(expectedExceptions = SQLException.class, dataProvider = "intWrong")
  public void testGetIntNegative(Object value) throws Exception {
    DataMap map = new DataMap(new ColumnInfo("int1", Types.NUMERIC));
    map.addRow(value);

    MockResultSet rs = new MockResultSet(map);

    Assert.assertTrue(rs.next());
    rs.getInt(1);
  }

  @Test(expectedExceptions = SQLException.class, dataProvider = "intWrong")
  public void testGetIntNegativeTitle(Object value) throws Exception {
    DataMap map = new DataMap(new ColumnInfo("int1", Types.NUMERIC));
    map.addRow(value);

    MockResultSet rs = new MockResultSet(map);

    Assert.assertTrue(rs.next());
    rs.getInt("int1");
  }
}