package ua.vvygulyarniy.sqlmock.mock;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.StringReader;
import java.sql.ResultSet;

import org.testng.annotations.Test;

/**
 * Class description here.
 * @author Vadim Vygulyarniy. 28.11.13 15:26
 */
public class UsageTest {
  @Test
  public void testCreationFromData() throws Exception {
    ResultSet rs = new MockResultSet(new Object[][]{
        {"row1-1", "row2-2"},
        {"row2-1", "row2-2"}
    }, "col1", "col2");
    assertTrue(rs.next());
    assertTrue(rs.next());
    assertEquals("row2-1", rs.getString(1));
    assertEquals("row2-2", rs.getString("col2"));
  }

  @Test
  public void testCreationFromReader() throws Exception {
    final StringReader data = new StringReader(
        "row1-1,row2-2\n" +
        "row2-1,row2-2");
    ResultSet rs = new MockResultSet(data, "col1", "col2");
    assertTrue(rs.next());
    assertTrue(rs.next());
    assertEquals("row2-1", rs.getString(1));
    assertEquals("row2-2", rs.getString("col2"));
  }

  @Test
  public void testCreationFromFile() throws Exception {
    ResultSet rs = new MockResultSet(new File("parseTest.csv"), "col1", "col2");
    assertTrue(rs.next());
    assertTrue(rs.next());
    assertEquals("3", rs.getString(1));
    assertEquals("5", rs.getString("col2"));
  }
}
