package ua.vvygulyarniy.sqlmock.mock;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ua.vvygulyarniy.sqlmock.column.ColumnInfo;
import ua.vvygulyarniy.sqlmock.datamap.DataMap;

public class MockResultBigDecimalTest {

  @DataProvider(name = "validData")
  public Object[][] getValidData() {
    return new Object[][]{
        {BigDecimal.ZERO, BigDecimal.ZERO},
        {new BigDecimal(3.3), new BigDecimal(3.3)},
        {null, null},
        {"3.25", new BigDecimal(3.25)}
    };
  }

  @DataProvider(name = "invalidData")
  public Object[][] getInvalidData() {
    return new Object[][]{
        {"test"},
        {new Date()}
    };
  }

  @Test(dataProvider = "validData")
  public void testGetBigDecimal(Object data, BigDecimal expected) throws Exception {
    DataMap map = new DataMap(new ColumnInfo("dec1", Types.NUMERIC));
    map.addRow(data);
    MockResultSet rs = new MockResultSet(map);

    Assert.assertTrue(rs.next());
    Assert.assertEquals(rs.getBigDecimal(1), expected);
  }

  @Test(dataProvider = "validData")
  public void testGetBigDecimalByName(Object data, BigDecimal expected) throws Exception {
    DataMap map = new DataMap(new ColumnInfo("dec1", Types.NUMERIC));
    map.addRow(data);
    MockResultSet rs = new MockResultSet(map);

    Assert.assertTrue(rs.next());
    Assert.assertEquals(rs.getBigDecimal("dec1"), expected);
  }

  @Test(expectedExceptions = SQLException.class,
        dataProvider = "invalidData")
  public void testGetBigDecimalWrong(Object data) throws Exception {
    DataMap map = new DataMap(new ColumnInfo("dec1", Types.NUMERIC));
    map.addRow(data);

    MockResultSet rs = new MockResultSet(map);

    Assert.assertTrue(rs.next());
    rs.getBigDecimal(1);
  }

  @Test(expectedExceptions = SQLException.class,
        dataProvider = "invalidData")
  public void testGetBigDecimalWrongByName(Object data) throws Exception {
    DataMap map = new DataMap(new ColumnInfo("dec1", Types.NUMERIC));
    map.addRow(data);

    MockResultSet rs = new MockResultSet(map);

    Assert.assertTrue(rs.next());
    rs.getBigDecimal("dec1");
  }
}