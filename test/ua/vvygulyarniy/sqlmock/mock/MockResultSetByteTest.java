package ua.vvygulyarniy.sqlmock.mock;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.sql.SQLException;
import java.sql.Types;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ua.vvygulyarniy.sqlmock.column.ColumnInfo;
import ua.vvygulyarniy.sqlmock.datamap.DataMap;

public class MockResultSetByteTest {
  @DataProvider(name = "validData")
  public Object[][] getValidData() {
    //noinspection UnnecessaryBoxing
    return new Object[][]{
        {1, (byte) 1},
        {"2", (byte) 2},
        {1 + 2, (byte) 3},
        {null, (byte) 0},
        {127, (byte) 127},
        {-128, (byte) -128},
        {new Byte((byte) 2), (byte) 2}
    };
  }

  @DataProvider(name = "wrongData")
  public Object[][] getWrongData() {
    return new Object[][]{
        {-200}, {"200"}, {128}, {-129},
    };
  }

  @Test(dataProvider = "validData")
  public void testGetByte(Object value, byte expected) throws Exception {
    DataMap map = new DataMap(new ColumnInfo("byte1", Types.NUMERIC));
    map.addRow(value);
    MockResultSet rs = new MockResultSet(map);
    assertTrue(rs.next());
    assertEquals(rs.getByte(1), expected);
  }

  @Test(dataProvider = "validData")
  public void testGetByteByName(Object value, byte expected) throws Exception {
    DataMap map = new DataMap(new ColumnInfo("byte1", Types.NUMERIC));
    map.addRow(value);
    MockResultSet rs = new MockResultSet(map);
    assertTrue(rs.next());
    assertEquals(rs.getByte("byte1"), expected);
  }

  @Test(expectedExceptions = SQLException.class, dataProvider = "wrongData")
  public void testGetByteWrong(Object value) throws Exception {
    DataMap map = new DataMap(new ColumnInfo("byte1", Types.NUMERIC));
    map.addRow(value);
    MockResultSet rs = new MockResultSet(map);
    assertTrue(rs.next());
    rs.getByte(1);
  }
}