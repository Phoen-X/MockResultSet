package ua.vvygulyarniy.sqlmock.mock;

import static org.testng.Assert.assertTrue;

import java.awt.*;
import java.sql.SQLException;
import java.sql.Types;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ua.vvygulyarniy.sqlmock.column.ColumnInfo;
import ua.vvygulyarniy.sqlmock.datamap.DataMap;

public class MockResultSetGetStringTest {
  @DataProvider(name = "stringData")
  public Object[][] getStringDat() {
    return new Object[][]{
        {"test1", "test1"},
        {1, "1"},
        {null, null},
        {new Point(1, 1), new Point(1, 1).toString()}
    };
  }

  @Test(dataProvider = "stringData")
  public void testGetString(Object value, String expected) throws Exception {
    MockResultSet rs = createResultSet(value);

    assertTrue(rs.next());
    Assert.assertEquals(rs.getString(1), rs.getString("str1"));
    Assert.assertEquals(rs.getString(1), expected);
  }

  private MockResultSet createResultSet(final Object value) {
    DataMap map = new DataMap(new ColumnInfo("str1", Types.VARCHAR));
    map.addRow(value);

    return new MockResultSet(map);
  }

  @Test(expectedExceptions = SQLException.class)
  public void testNotInRange() throws Exception {
    MockResultSet rs = createResultSet("test");
    assertTrue(rs.next());
    rs.getString(3);
  }

  @Test(expectedExceptions = SQLException.class)
  public void testNotInRangeTitle() throws Exception {
    MockResultSet rs = createResultSet("test");
    assertTrue(rs.next());
    rs.getString("not_valid_title");
  }
}