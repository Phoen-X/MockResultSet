package ua.vvygulyarniy.sqlmock.mock;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.StringReader;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.testng.annotations.Test;
import ua.vvygulyarniy.sqlmock.datamap.DataMap;

/** @author Vadim Vygulyarniy @ 25.11.13 11:21 */
public class IntegrationTest {
  @Test
  public void testObjects() throws Exception {
    long time = System.currentTimeMillis();
    DataMap map = new DataMap("str1", "int2");
    map.parse(new Object[][]{
        {"test1", 1},
        {"test2", 2},
        {new Timestamp(time), new Date(time)}
    });
    ResultSet rs = new MockResultSet(map);

    assertTrue(rs.next());
    assertEquals(rs.getString(1), "test1");
    assertEquals(rs.getInt(2), 1);
    assertTrue(rs.next());
    assertTrue(rs.next());
    assertEquals(rs.getTimestamp(1), new Timestamp(time));
    assertEquals(rs.getDate(2), new Date(time));
  }

  @Test
  public void testParsedObjects() throws Exception {
    DataMap map = new DataMap("col1", "col2");
    map.parseCsv(new StringReader("2013-04-01,2013-01-01 12:00:00"));
    ResultSet rs = new MockResultSet(map);
    assertTrue(rs.next());
    assertEquals(rs.getDate(1), Date.valueOf("2013-04-01"));
    assertEquals(rs.getTimestamp(2), Timestamp.valueOf("2013-01-01 12:00:00"));
  }
}
