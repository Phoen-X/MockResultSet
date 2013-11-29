package ua.vvygulyarniy.sqlmock.mock;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

import ua.vvygulyarniy.sqlmock.datamap.DataMap;
import ua.vvygulyarniy.sqlmock.datamap.DataMapBuilder;

/** @author Vadim Vygulyarniy @ 22.11.13 12:10 */
public class MockResultSet implements ResultSet {
  public static final int BEFORE_FIRST_INDEX = -1;
  private DataMap data;
  private boolean closed;
  private int currentRow = BEFORE_FIRST_INDEX;
  private boolean wasNull;
  private String  cursorName;

  public MockResultSet(final File dataFile, String... columns) throws IOException {
    this(new BufferedReader(new FileReader(dataFile)), columns);
  }

  public MockResultSet(final Reader reader, String... columns) throws IOException {
    this(new DataMapBuilder().withColumns(columns).fromReader(reader).build());
  }

  public MockResultSet(final Object[][] data, String... columns) throws IOException {
    this(new DataMapBuilder().withColumns(columns).withData(data).build());
  }

  public MockResultSet(final DataMap data) {
    this.data = data;
  }

  public void setCursorName(final String cursorName) {
    this.cursorName = cursorName;
  }

  @Override
  public boolean next() throws SQLException {
    if (closed)
      throw new SQLException("ResultSet is closed");
    currentRow++;
    return currentRow < data.getRowCount();
  }

  @Override
  public void close() throws SQLException {
    closed = true;
  }

  @Override
  public boolean wasNull() throws SQLException {
    return wasNull;
  }

  @Override
  public String getString(final int columnIndex) throws SQLException {
    try {
      Object value = data.getValue(currentRow, columnIndex - 1);
      wasNull = value == null;
      if (wasNull)
        return null;
      return value instanceof String ? (String) value : String.valueOf(value);
    } catch (Exception e) {
      throw new SQLException(e.getMessage());
    }
  }

  @Override
  public boolean getBoolean(final int columnIndex) throws SQLException {
    try {
      Object value = data.getValue(currentRow, columnIndex - 1);
      wasNull = value == null;
      if (value == null) {
        return false;
      }
      return value instanceof Boolean ? (Boolean) value : Boolean.valueOf(String.valueOf(value));
    } catch (Exception e) {
      throw new SQLException(e.getMessage());
    }
  }

  @Override
  public byte getByte(final int columnIndex) throws SQLException {
    try {
      Object value = data.getValue(currentRow, columnIndex - 1);
      wasNull = value == null;
      if (value == null) {
        return 0;
      }
      return value instanceof Byte ? (Byte) value : Byte.valueOf(String.valueOf(value));
    } catch (Exception e) {
      throw new SQLException(e.getMessage());
    }
  }

  @Override
  public short getShort(final int columnIndex) throws SQLException {
    try {
      Object value = data.getValue(currentRow, columnIndex - 1);
      wasNull = value == null;
      if (value == null) {
        return 0;
      }
      return value instanceof Short ? (Short) value : Short.valueOf(String.valueOf(value));
    } catch (Exception e) {
      throw new SQLException(e.getMessage());
    }
  }

  @Override
  public int getInt(final int columnIndex) throws SQLException {
    try {
      Object value = data.getValue(currentRow, columnIndex - 1);
      wasNull = value == null;
      if (value == null) {
        return 0;
      }
      return value instanceof Integer ? (Integer) value : Integer.valueOf(String.valueOf(value));
    } catch (Exception e) {
      throw new SQLException(e.getMessage());
    }
  }

  @Override
  public long getLong(final int columnIndex) throws SQLException {
    try {
      Object value = data.getValue(currentRow, columnIndex - 1);
      wasNull = value == null;
      if (value == null) {
        return 0;
      }
      return value instanceof Long ? (Long) value : Long.valueOf(String.valueOf(value));
    } catch (Exception e) {
      throw new SQLException(e.getMessage());
    }
  }

  @Override
  public float getFloat(final int columnIndex) throws SQLException {
    try {
      Object value = data.getValue(currentRow, columnIndex - 1);
      wasNull = value == null;
      if (value == null) {
        return 0;
      }
      return value instanceof Float ? (Float) value : Float.valueOf(String.valueOf(value));
    } catch (Exception e) {
      throw new SQLException(e.getMessage());
    }
  }

  @Override
  public double getDouble(final int columnIndex) throws SQLException {
    try {
      Object value = data.getValue(currentRow, columnIndex - 1);
      wasNull = value == null;
      if (value == null) {
        return 0;
      }
      return value instanceof Double ? (Double) value : Double.valueOf(String.valueOf(value));
    } catch (Exception e) {
      throw new SQLException(e.getMessage());
    }
  }

  @Override
  public BigDecimal getBigDecimal(final int columnIndex, final int scale) throws SQLException {
    //FIXME implement logic with scale
    return getBigDecimal(columnIndex);
  }

  @Override
  public byte[] getBytes(final int columnIndex) throws SQLException {
    throw new UnsupportedOperationException("not implemented");
  }

  @Override
  public Date getDate(final int columnIndex) throws SQLException {
    try {
      Object value = data.getValue(currentRow, columnIndex - 1);
      wasNull = value == null;
      if (value == null) {
        return null;
      }
      if (value instanceof Date)
        return (Date) value;
      else
        return Date.valueOf(String.valueOf(value));
    } catch (Exception e) {
      throw new SQLException(e.getMessage());
    }
  }

  @Override
  public Time getTime(final int columnIndex) throws SQLException {
    try {
      Object value = data.getValue(currentRow, columnIndex - 1);
      wasNull = value == null;
      if (value == null) {
        return null;
      }
      if (value instanceof Time)
        return (Time) value;
      else
        return Time.valueOf(String.valueOf(value));
    } catch (Exception e) {
      throw new SQLException(e.getMessage());
    }
  }

  @Override
  public Timestamp getTimestamp(final int columnIndex) throws SQLException {
    try {
      Object value = data.getValue(currentRow, columnIndex - 1);
      wasNull = value == null;
      if (value == null) {
        return null;
      }
      if (value instanceof Timestamp)
        return (Timestamp) value;
      else
        return Timestamp.valueOf(String.valueOf(value));
    } catch (Exception e) {
      throw new SQLException(e.getMessage());
    }
  }

  @Override
  public InputStream getAsciiStream(final int columnIndex) throws SQLException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public InputStream getUnicodeStream(final int columnIndex) throws SQLException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public InputStream getBinaryStream(final int columnIndex) throws SQLException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public String getString(final String columnLabel) throws SQLException {
    try {
      Object value = data.getValue(currentRow, columnLabel);
      wasNull = value == null;
      if (value == null) {
        return null;
      }
      if (value instanceof String)
        return (String) value;
      else
        return String.valueOf(value);
    } catch (Exception e) {
      throw new SQLException(e.getMessage());
    }
  }

  @Override
  public boolean getBoolean(final String columnLabel) throws SQLException {
    try {
      Object value = data.getValue(currentRow, columnLabel);
      wasNull = value == null;
      if (value == null) {
        return false;
      }
      if (value instanceof Boolean)
        return (Boolean) value;
      else
        return Boolean.valueOf(String.valueOf(value));
    } catch (Exception e) {
      throw new SQLException(e.getMessage());
    }
  }

  @Override
  public byte getByte(final String columnLabel) throws SQLException {
    try {
      Object value = data.getValue(currentRow, columnLabel);
      wasNull = value == null;
      if (value == null) {
        return 0;
      }
      if (value instanceof Byte)
        return (Byte) value;
      else
        return Byte.valueOf(String.valueOf(value));
    } catch (Exception e) {
      throw new SQLException(e.getMessage());
    }
  }

  @Override
  public short getShort(final String columnLabel) throws SQLException {
    try {
      Object value = data.getValue(currentRow, columnLabel);
      wasNull = value == null;
      if (value == null) {
        return 0;
      }
      if (value instanceof Short)
        return (Short) value;
      else
        return Short.valueOf(String.valueOf(value));
    } catch (Exception e) {
      throw new SQLException(e.getMessage());
    }
  }

  @Override
  public int getInt(final String columnLabel) throws SQLException {
    try {
      Object value = data.getValue(currentRow, columnLabel);
      wasNull = value == null;
      if (value == null) {
        return 0;
      }
      if (value instanceof Integer)
        return (Integer) value;
      else
        return Integer.valueOf(String.valueOf(value));
    } catch (Exception e) {
      throw new SQLException(e.getMessage());
    }
  }

  @Override
  public long getLong(final String columnLabel) throws SQLException {
    try {
      Object value = data.getValue(currentRow, columnLabel);
      wasNull = value == null;
      if (value == null) {
        return 0;
      }
      if (value instanceof Long)
        return (Long) value;
      else
        return Long.valueOf(String.valueOf(value));
    } catch (Exception e) {
      throw new SQLException(e.getMessage());
    }
  }

  @Override
  public float getFloat(final String columnLabel) throws SQLException {
    try {
      Object value = data.getValue(currentRow, columnLabel);
      wasNull = value == null;
      if (value == null) {
        return 0;
      }
      if (value instanceof Float)
        return (Float) value;
      else
        return Float.valueOf(String.valueOf(value));
    } catch (Exception e) {
      throw new SQLException(e.getMessage());
    }
  }

  @Override
  public double getDouble(final String columnLabel) throws SQLException {
    try {
      Object value = data.getValue(currentRow, columnLabel);
      wasNull = value == null;
      if (value == null) {
        return 0;
      }
      if (value instanceof Double)
        return (Double) value;
      else
        return Double.valueOf(String.valueOf(value));
    } catch (Exception e) {
      throw new SQLException(e.getMessage());
    }
  }

  @Override
  public BigDecimal getBigDecimal(final String columnLabel, final int scale) throws SQLException {
    //FIXME scale param is not processed
    return getBigDecimal(columnLabel);
  }

  @Override
  public byte[] getBytes(final String columnLabel) throws SQLException {
    return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public Date getDate(final String columnLabel) throws SQLException {
    try {
      Object value = data.getValue(currentRow, columnLabel);
      wasNull = value == null;
      if (value == null) {
        return null;
      }
      if (value instanceof Date)
        return (Date) value;
      else
        return Date.valueOf(String.valueOf(value));
    } catch (Exception e) {
      throw new SQLException(e.getMessage());
    }
  }

  @Override
  public Time getTime(final String columnLabel) throws SQLException {
    try {
      Object value = data.getValue(currentRow, columnLabel);
      wasNull = value == null;
      if (value == null) {
        return null;
      }
      if (value instanceof Time)
        return (Time) value;
      else
        return Time.valueOf(String.valueOf(value));
    } catch (Exception e) {
      throw new SQLException(e.getMessage());
    }
  }

  @Override
  public Timestamp getTimestamp(final String columnLabel) throws SQLException {
    try {
      Object value = data.getValue(currentRow, columnLabel);
      wasNull = value == null;
      if (value == null) {
        return null;
      }
      if (value instanceof Timestamp)
        return (Timestamp) value;
      else
        return Timestamp.valueOf(String.valueOf(value));
    } catch (Exception e) {
      throw new SQLException(e.getMessage());
    }
  }

  @Override
  public InputStream getAsciiStream(final String columnLabel) throws SQLException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public InputStream getUnicodeStream(final String columnLabel) throws SQLException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public InputStream getBinaryStream(final String columnLabel) throws SQLException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public SQLWarning getWarnings() throws SQLException {
    return null;
  }

  @Override
  public void clearWarnings() throws SQLException {

  }

  @Override
  public String getCursorName() throws SQLException {
    return cursorName;
  }

  @Override
  public ResultSetMetaData getMetaData() throws SQLException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public Object getObject(final int columnIndex) throws SQLException {
    try {
      Object value = data.getValue(currentRow, columnIndex - 1);
      wasNull = value == null;
      return value;
    } catch (Exception e) {
      throw new SQLException(e.getMessage());
    }
  }

  @Override
  public Object getObject(final String columnLabel) throws SQLException {
    try {
      Object value = data.getValue(currentRow, columnLabel);
      wasNull = value == null;
      return value;
    } catch (Exception e) {
      throw new SQLException(e.getMessage());
    }
  }

  @Override
  public int findColumn(final String columnLabel) throws SQLException {
    int index = data.getColumnIndex(columnLabel);
    if (index < 0)
      throw new SQLException("column with label " + columnLabel + " not found");
    return index;
  }

  @Override
  public Reader getCharacterStream(final int columnIndex) throws SQLException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public Reader getCharacterStream(final String columnLabel) throws SQLException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public BigDecimal getBigDecimal(final int columnIndex) throws SQLException {
    try {
      Object value = data.getValue(currentRow, columnIndex - 1);
      wasNull = value == null;
      if (value == null) {
        return null;
      }
      if (value instanceof BigDecimal)
        return (BigDecimal) value;
      else
        return BigDecimal.valueOf(Double.valueOf(String.valueOf(value)));
    } catch (Exception e) {
      throw new SQLException(e.getMessage());
    }
  }

  @Override
  public BigDecimal getBigDecimal(final String columnLabel) throws SQLException {
    try {
      Object value = data.getValue(currentRow, columnLabel);
      wasNull = value == null;
      if (value == null) {
        return null;
      }
      if (value instanceof BigDecimal)
        return (BigDecimal) value;
      else
        return BigDecimal.valueOf(Double.valueOf(String.valueOf(value)));
    } catch (Exception e) {
      throw new SQLException(e.getMessage());
    }
  }

  @Override
  public boolean isBeforeFirst() throws SQLException {
    return currentRow == BEFORE_FIRST_INDEX;
  }

  @Override
  public boolean isAfterLast() throws SQLException {
    return currentRow >= data.getRowCount();
  }

  @Override
  public boolean isFirst() throws SQLException {
    return currentRow == 0;
  }

  @Override
  public boolean isLast() throws SQLException {
    return currentRow == data.getRowCount() - 1;
  }

  @Override
  public void beforeFirst() throws SQLException {
    currentRow = BEFORE_FIRST_INDEX;
  }

  @Override
  public void afterLast() throws SQLException {
    currentRow = data.getRowCount();
  }

  @Override
  public boolean first() throws SQLException {
    if (data.getRowCount() > 0) {
      currentRow = 0;
      return true;
    }
    return false;
  }

  @Override
  public boolean last() throws SQLException {
    if (data.getRowCount() > 0) {
      currentRow = data.getRowCount() - 1;
      return true;
    }
    return false;
  }

  @Override
  public int getRow() throws SQLException {
    return currentRow + 1;
  }

  @Override
  public boolean absolute(final int row) throws SQLException {
    return false;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public boolean relative(final int rows) throws SQLException {
    return false;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public boolean previous() throws SQLException {
    return false;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void setFetchDirection(final int direction) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public int getFetchDirection() throws SQLException {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void setFetchSize(final int rows) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public int getFetchSize() throws SQLException {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public int getType() throws SQLException {
    return TYPE_FORWARD_ONLY;
  }

  @Override
  public int getConcurrency() throws SQLException {
    return CONCUR_READ_ONLY;
  }

  @Override
  public boolean rowUpdated() throws SQLException {
    return false;
  }

  @Override
  public boolean rowInserted() throws SQLException {
    return false;
  }

  @Override
  public boolean rowDeleted() throws SQLException {
    return false;
  }

  @Override
  public void updateNull(final int columnIndex) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateBoolean(final int columnIndex, final boolean x) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateByte(final int columnIndex, final byte x) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateShort(final int columnIndex, final short x) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateInt(final int columnIndex, final int x) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateLong(final int columnIndex, final long x) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateFloat(final int columnIndex, final float x) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateDouble(final int columnIndex, final double x) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateBigDecimal(final int columnIndex, final BigDecimal x) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateString(final int columnIndex, final String x) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateBytes(final int columnIndex, final byte[] x) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateDate(final int columnIndex, final Date x) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateTime(final int columnIndex, final Time x) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateTimestamp(final int columnIndex, final Timestamp x) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateAsciiStream(final int columnIndex, final InputStream x, final int length) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateBinaryStream(final int columnIndex, final InputStream x, final int length) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateCharacterStream(final int columnIndex, final Reader x, final int length) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateObject(final int columnIndex, final Object x, final int scaleOrLength) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateObject(final int columnIndex, final Object x) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateNull(final String columnLabel) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateBoolean(final String columnLabel, final boolean x) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateByte(final String columnLabel, final byte x) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateShort(final String columnLabel, final short x) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateInt(final String columnLabel, final int x) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateLong(final String columnLabel, final long x) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateFloat(final String columnLabel, final float x) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateDouble(final String columnLabel, final double x) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateBigDecimal(final String columnLabel, final BigDecimal x) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateString(final String columnLabel, final String x) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateBytes(final String columnLabel, final byte[] x) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateDate(final String columnLabel, final Date x) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateTime(final String columnLabel, final Time x) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateTimestamp(final String columnLabel, final Timestamp x) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateAsciiStream(final String columnLabel, final InputStream x, final int length) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateBinaryStream(final String columnLabel, final InputStream x, final int length) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateCharacterStream(final String columnLabel, final Reader reader, final int length)
  throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateObject(final String columnLabel, final Object x, final int scaleOrLength) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateObject(final String columnLabel, final Object x) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void insertRow() throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateRow() throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void deleteRow() throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void refreshRow() throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void cancelRowUpdates() throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void moveToInsertRow() throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void moveToCurrentRow() throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public Statement getStatement() throws SQLException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public Object getObject(final int columnIndex, final Map<String, Class<?>> map) throws SQLException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public Ref getRef(final int columnIndex) throws SQLException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public Blob getBlob(final int columnIndex) throws SQLException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public Clob getClob(final int columnIndex) throws SQLException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public Array getArray(final int columnIndex) throws SQLException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public Object getObject(final String columnLabel, final Map<String, Class<?>> map) throws SQLException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public Ref getRef(final String columnLabel) throws SQLException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public Blob getBlob(final String columnLabel) throws SQLException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public Clob getClob(final String columnLabel) throws SQLException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public Array getArray(final String columnLabel) throws SQLException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public Date getDate(final int columnIndex, final Calendar cal) throws SQLException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public Date getDate(final String columnLabel, final Calendar cal) throws SQLException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public Time getTime(final int columnIndex, final Calendar cal) throws SQLException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public Time getTime(final String columnLabel, final Calendar cal) throws SQLException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public Timestamp getTimestamp(final int columnIndex, final Calendar cal) throws SQLException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public Timestamp getTimestamp(final String columnLabel, final Calendar cal) throws SQLException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public URL getURL(final int columnIndex) throws SQLException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public URL getURL(final String columnLabel) throws SQLException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateRef(final int columnIndex, final Ref x) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateRef(final String columnLabel, final Ref x) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateBlob(final int columnIndex, final Blob x) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateBlob(final String columnLabel, final Blob x) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateClob(final int columnIndex, final Clob x) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateClob(final String columnLabel, final Clob x) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateArray(final int columnIndex, final Array x) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateArray(final String columnLabel, final Array x) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public RowId getRowId(final int columnIndex) throws SQLException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public RowId getRowId(final String columnLabel) throws SQLException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateRowId(final int columnIndex, final RowId x) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateRowId(final String columnLabel, final RowId x) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public int getHoldability() throws SQLException {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public boolean isClosed() throws SQLException {
    return false;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateNString(final int columnIndex, final String nString) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateNString(final String columnLabel, final String nString) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateNClob(final int columnIndex, final NClob nClob) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateNClob(final String columnLabel, final NClob nClob) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public NClob getNClob(final int columnIndex) throws SQLException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public NClob getNClob(final String columnLabel) throws SQLException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public SQLXML getSQLXML(final int columnIndex) throws SQLException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public SQLXML getSQLXML(final String columnLabel) throws SQLException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateSQLXML(final int columnIndex, final SQLXML xmlObject) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateSQLXML(final String columnLabel, final SQLXML xmlObject) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public String getNString(final int columnIndex) throws SQLException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public String getNString(final String columnLabel) throws SQLException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public Reader getNCharacterStream(final int columnIndex) throws SQLException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public Reader getNCharacterStream(final String columnLabel) throws SQLException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateNCharacterStream(final int columnIndex, final Reader x, final long length) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateNCharacterStream(final String columnLabel, final Reader reader, final long length)
  throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateAsciiStream(final int columnIndex, final InputStream x, final long length) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateBinaryStream(final int columnIndex, final InputStream x, final long length) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateCharacterStream(final int columnIndex, final Reader x, final long length) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateAsciiStream(final String columnLabel, final InputStream x, final long length) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateBinaryStream(final String columnLabel, final InputStream x, final long length) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateCharacterStream(final String columnLabel, final Reader reader, final long length)
  throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateBlob(final int columnIndex, final InputStream inputStream, final long length) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateBlob(final String columnLabel, final InputStream inputStream, final long length)
  throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateClob(final int columnIndex, final Reader reader, final long length) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateClob(final String columnLabel, final Reader reader, final long length) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateNClob(final int columnIndex, final Reader reader, final long length) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateNClob(final String columnLabel, final Reader reader, final long length) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateNCharacterStream(final int columnIndex, final Reader x) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateNCharacterStream(final String columnLabel, final Reader reader) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateAsciiStream(final int columnIndex, final InputStream x) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateBinaryStream(final int columnIndex, final InputStream x) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateCharacterStream(final int columnIndex, final Reader x) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateAsciiStream(final String columnLabel, final InputStream x) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateBinaryStream(final String columnLabel, final InputStream x) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateCharacterStream(final String columnLabel, final Reader reader) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateBlob(final int columnIndex, final InputStream inputStream) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateBlob(final String columnLabel, final InputStream inputStream) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateClob(final int columnIndex, final Reader reader) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateClob(final String columnLabel, final Reader reader) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateNClob(final int columnIndex, final Reader reader) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void updateNClob(final String columnLabel, final Reader reader) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public <T> T unwrap(final Class<T> iface) throws SQLException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public boolean isWrapperFor(final Class<?> iface) throws SQLException {
    return false;  //To change body of implemented methods use File | Settings | File Templates.
  }
}
