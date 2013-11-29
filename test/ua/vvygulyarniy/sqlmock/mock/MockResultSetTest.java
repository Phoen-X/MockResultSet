package ua.vvygulyarniy.sqlmock.mock;

import static java.sql.Types.BOOLEAN;
import static java.sql.Types.NUMERIC;
import static java.sql.Types.TIMESTAMP;
import static java.sql.Types.VARCHAR;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.sql.SQLException;

import org.testng.annotations.Test;
import ua.vvygulyarniy.sqlmock.column.ColumnInfo;
import ua.vvygulyarniy.sqlmock.datamap.DataMap;

/** @author Vadim Vygulyarniy @ 22.11.13 14:05 */
public class MockResultSetTest {

  @Test
  public void testIteration() throws Exception {
    final DataMap map = new DataMap(new ColumnInfo("str1", VARCHAR),
                                    new ColumnInfo("str2", VARCHAR));
    map.addRow("a", "b");
    map.addRow("c", "d");

    MockResultSet rs = new MockResultSet(map);
    assertTrue(rs.isBeforeFirst());
    assertFalse(rs.isFirst());
    assertFalse(rs.isLast());
    assertFalse(rs.isAfterLast());
    assertTrue(rs.next(), "first element next() error result");
    assertFalse(rs.isBeforeFirst());
    assertTrue(rs.isFirst());
    assertFalse(rs.isLast());
    assertFalse(rs.isAfterLast());
    assertTrue(rs.next(), "second element next() error result");
    assertFalse(rs.isBeforeFirst());
    assertFalse(rs.isFirst());
    assertTrue(rs.isLast());
    assertFalse(rs.isAfterLast());
    assertFalse(rs.next(), "third element next() error result");
    assertFalse(rs.isBeforeFirst());
    assertFalse(rs.isFirst());
    assertFalse(rs.isLast());
    assertTrue(rs.isAfterLast());
  }

  @Test(expectedExceptions = SQLException.class)
  public void testClose() throws Exception {
    DataMap map = new DataMap(new ColumnInfo("str1", VARCHAR),
                              new ColumnInfo("str2", VARCHAR));

    map.addRow("a", "b");
    map.addRow("c", "d");
    MockResultSet rs = new MockResultSet(map);
    assertTrue(rs.next());
    rs.close();
    rs.next();
  }

  @Test
  public void testWasNullPositive() throws Exception {
    DataMap map = new DataMap(new ColumnInfo("date1", TIMESTAMP));

    map.addRow(new Object[]{null});

    MockResultSet rs = new MockResultSet(map);

    assertTrue(rs.next());
    assertEquals(rs.getDate(1), null);
    assertTrue(rs.wasNull());
  }

  @Test
  public void testWasNullNegative() throws Exception {
    DataMap map = new DataMap(new ColumnInfo("date1", TIMESTAMP));

    map.addRow("test");

    MockResultSet rs = new MockResultSet(map);

    assertTrue(rs.next());
    assertEquals(rs.getString(1), "test");
    assertFalse(rs.wasNull());
  }

  @Test
  public void testGetBoolean() throws Exception {
    DataMap map = new DataMap(new ColumnInfo("bool1", BOOLEAN));

    map.addRow(true);
    map.addRow("true");
    map.addRow("false");
    map.addRow(new Object[]{null});
    map.addRow(Boolean.TRUE);

    MockResultSet rs = new MockResultSet(map);

    assertTrue(rs.next());
    assertTrue(rs.getBoolean(1));
    assertTrue(rs.getBoolean("bool1"));
    assertTrue(rs.next());
    assertTrue(rs.getBoolean(1));
    assertTrue(rs.getBoolean("bool1"));
    assertTrue(rs.next());
    assertFalse(rs.getBoolean(1));
    assertFalse(rs.getBoolean("bool1"));
    assertTrue(rs.next());
    assertFalse(rs.getBoolean(1));
    assertFalse(rs.getBoolean("bool1"));
  }

  @Test
  public void testGetShort() throws Exception {
    DataMap map = new DataMap(new ColumnInfo("short1", NUMERIC));

    map.addRow(1);
    map.addRow("2");
    map.addRow(1 + 2);
    map.addRow(new Object[]{null});

    MockResultSet rs = new MockResultSet(map);

    assertTrue(rs.next());
    assertEquals(rs.getShort(1), 1);
    assertTrue(rs.next());
    assertEquals(rs.getShort(1), 2);
    assertTrue(rs.next());
    assertEquals(rs.getShort("short1"), 3);
    assertTrue(rs.next());
    assertEquals(rs.getShort(1), 0);
  }

  @Test(expectedExceptions = SQLException.class)
  public void testGetShortWrong() throws Exception {
    DataMap map = new DataMap(new ColumnInfo("short1", NUMERIC));

    map.addRow("test");

    MockResultSet rs = new MockResultSet(map);

    assertTrue(rs.next());
    rs.getShort(1);
  }

  @Test
  public void testGetFloat() throws Exception {
    DataMap map = new DataMap(new ColumnInfo("float1", NUMERIC));

    map.addRow(1.2f);
    map.addRow("2.35f");
    map.addRow(1 + 2);
    map.addRow(new Object[]{null});

    MockResultSet rs = new MockResultSet(map);

    assertTrue(rs.next());
    assertEquals(rs.getFloat(1), 1.2f);
    assertTrue(rs.next());
    assertEquals(rs.getFloat(1), 2.35f);
    assertTrue(rs.next());
    assertEquals(rs.getFloat("float1"), 3f);
    assertTrue(rs.next());
    assertEquals(rs.getShort(1), 0);
  }

  @Test(expectedExceptions = SQLException.class)
  public void testGetFloatWrong() throws Exception {
    DataMap map = new DataMap(new ColumnInfo("float1", NUMERIC));

    map.addRow("test");

    MockResultSet rs = new MockResultSet(map);

    assertTrue(rs.next());
    rs.getFloat(1);
  }

}
