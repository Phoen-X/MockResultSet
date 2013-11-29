package ua.vvygulyarniy.sqlmock.column;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

/** @author Vadim Vygulyarniy @ 22.11.13 10:09 */
public class MockColumnTest {

  public static final String TEST_TITLE = "my_title";

  @Test
  public void testGetTitle() throws Exception {
    MockColumn column = new MockColumn(TEST_TITLE);
    assertEquals(TEST_TITLE, column.getTitle());
  }

  @Test
  public void testSize() throws Exception {
    MockColumn column = new MockColumn(TEST_TITLE);
    column.add(1);
    column.add(2);
    column.add(3);

    assertEquals(3, column.getRowCount());
  }

  @Test
  public void testAdd() throws Exception {
    MockColumn column = new MockColumn(TEST_TITLE);
    column.add(1);
    column.add(4);
    column.add(5);

    assertEquals(4, column.get(1));

  }

  @Test
  public void testGet() throws Exception {
    MockColumn column = new MockColumn(TEST_TITLE);
    column.add(1);
    column.add(4);
    column.add(5);

    assertEquals(1, column.get(0));
    assertEquals(5, column.get(2));
    assertEquals(null, column.get(18));
  }
}
