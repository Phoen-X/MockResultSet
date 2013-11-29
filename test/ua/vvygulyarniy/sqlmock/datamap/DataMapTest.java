package ua.vvygulyarniy.sqlmock.datamap;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.io.File;
import java.sql.Types;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ua.vvygulyarniy.sqlmock.column.ColumnInfo;
import ua.vvygulyarniy.sqlmock.column.MockColumn;

/** @author Vadim Vygulyarniy @ 22.11.13 10:51 */
public class DataMapTest {
  DataMap map;

  @BeforeMethod
  public void setUp() {
    final ColumnInfo col1 = new ColumnInfo("stringColumn", Types.VARCHAR);
    final ColumnInfo col2 = new ColumnInfo("intColumn", Types.BIGINT);

    map = new DataMap(col1, col2);
  }

  @AfterMethod
  public void tearDown() {
    map = null;
  }

  @Test
  public void testGetColumn() throws Exception {

    assertNotNull(map.getColumn("stringColumn"));
    assertNotNull(map.getColumn(1));
  }

  @Test
  public void testGettingValue() throws Exception {

    final MockColumn column1 = map.getColumn(0);
    column1.add(1);
    column1.add(3);
    column1.add(5);

    assertEquals(((Integer) map.getValue(1, 0)).intValue(), 3);
  }

  @Test
  public void testAddRow() throws Exception {
    final String testVal = "test";
    map.addRow(testVal, 1);
    assertEquals(map.getValue(0, 0), testVal);
    assertEquals(map.getValue(0, 1), (Object) 1);

  }

  @Test
  public void testAddRowWrongTypes() throws Exception {
    final String value1 = "test1";
    final String value2 = "test2";

    map.addRow(value1, value2);
    assertEquals(map.getValue(0, 0), value1);
    assertEquals(map.getValue(0, 1), value2);
  }

  @Test
  public void testAddManyRows() throws Exception {
    for (int i = 0; i < 20; i++) {
      map.addRow(String.valueOf(i), String.valueOf(i + 1));
    }

    assertEquals(map.getRowCount(), 20, "wrong row count");
    assertEquals(map.getValue(19, 0), String.valueOf(19), "wrong value");
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testColumnCountCheck() throws Exception {
    map.addRow("test1", "test2", 123);
  }

  @Test
  public void testNulls() throws Exception {
    map.addRow("test1", "test2");
    map.addRow("test3");

    assertEquals(map.getValue(1, 0), "test3");
    assertEquals(map.getValue(1, 1), null);
  }

  @Test
  public void testParseObjects() throws Exception {
    Object[][] values = new Object[][]{
        {1, 3},
        {"test1", "test2"}
    };

    map.parse(values);

    assertEquals(map.getValue(0, 0), 1);
    assertEquals(map.getValue(1, 1), "test2");
  }

  @Test
  public void testParseCsv() throws Exception {
    map.parseCsv(new File("parseTest.csv"));

    assertEquals(map.getValue(0, 0), "test1");
    assertEquals(map.getValue(0, 1), "test2");
    assertEquals(map.getValue(1, 0), "3");
    assertEquals(map.getValue(1, 1), "5");
    assertEquals(map.getValue(2, 0), "");
    assertEquals(map.getValue(2, 1), "3");
    assertEquals(map.getValue(3, 0), "");
    assertEquals(map.getValue(3, 1), "1");
  }

  @Test
  public void testParseCsvFromFilename() throws Exception {
    map.parseCsv("parseTest.csv");
  }

}
