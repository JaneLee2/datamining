package com.ruijie.toolbox.databasedoc.pojo;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

//TODO: Split this class in two, ColumnInfo and ResultSetInfo to differentiate as the available info is not the same

/**
 * Information about a Table Column
 * 
 * @author edaigneault
 *
 */
public class ColumnInfo implements Serializable, Comparable {

	/**
	 * Nullability of the column
	 * 
	 * @author edaigneault
	 *
	 */
	public enum Nullability {
		/**
		 * Column is not nullable
		 */
		notNullable,
		/**
		 * Column can be null
		 */
		nullable,
		/**
		 * Nullability was set but was not one of known values
		 */
		unknown,
		/**
		 * Nullability was null (how Ironic!!)
		 */
		undefined;

		protected static Nullability fromResultSetMetadataInt(int nullable) {
			switch (nullable) {
				case ResultSetMetaData.columnNoNulls:
					return Nullability.notNullable;
				case ResultSetMetaData.columnNullable:
					return Nullability.nullable;
				default:
					return Nullability.unknown;
			}
		}

		/**
		 * checks weather this type is one of the known types (not unknown and not undefined)
		 * 
		 * @return true if the Type is anything but {@link Nullability#undefined} or {@link Nullability#unknown}
		 */
		public boolean isKnown()
		{
			return !(this.equals(undefined) || this.equals(unknown));
		}
	}

	/**
	 * SQL Datatypes
	 * 
	 * @author edaigneault
	 *
	 */
	public enum SQLType {
		/**
		 * Array
		 */
		ARRAY,
		/**
		 * Binary
		 */
		BINARY,
		/**
		 * Bit
		 */
		BIT,
		/**
		 * TinyInt
		 */
		TINYINT,
		/**
		 * SmallInt
		 */
		SMALLINT,
		/**
		 * Integer
		 */
		INTEGER,
		/**
		 * BigInt
		 */
		BIGINT,
		/**
		 * Float
		 */
		FLOAT,
		/**
		 * Real
		 */
		REAL,
		/**
		 * Double
		 */
		DOUBLE,
		/**
		 * Numeric
		 */
		NUMERIC,
		/**
		 * Decimal
		 */
		DECIMAL,
		/**
		 * Char
		 */
		CHAR,
		/**
		 * Varchar
		 */
		VARCHAR,
		/**
		 * Varchar2
		 */
		VARCHAR2,
		/**
		 * LongVarchar
		 */
		LONGVARCHAR,
		/**
		 * Date
		 */
		DATE,
		/**
		 * Time
		 */
		TIME,
		/**
		 * Timestamp
		 */
		TIMESTAMP,
		/**
		 * VarBinary
		 */
		VARBINARY,
		/**
		 * LongVarBinary
		 */
		LONGVARBINARY,
		/**
		 * Null
		 */
		NULL,
		/**
		 * Other
		 */
		OTHER,
		/**
		 * Java Object
		 */
		JAVA_OBJECT,
		/**
		 * Distinct
		 */
		DISTINCT,
		/**
		 * Struct
		 */
		STRUCT,
		/**
		 * Blob
		 */
		BLOB,
		/**
		 * Clob
		 */
		CLOB,
		/**
		 * Ref
		 */
		REF,
		/**
		 * Datalink
		 */
		DATALINK,
		/**
		 * Boolean
		 */
		BOOLEAN,
		/**
		 * RowID
		 */
		ROWID,
		/**
		 * NChar
		 */
		NCHAR,
		/**
		 * NVarchar
		 */
		NVARCHAR,
		/**
		 * LongNVarchar
		 */
		LONGNVARCHAR,
		/**
		 * NClob
		 */
		NCLOB,
		/**
		 * SQLXML
		 */
		SQLXML,
		/**
		 * RefCursor
		 */
		REF_CURSOR,
		/**
		 * Time With TimeZone
		 */
		TIME_WITH_TIMEZONE,
		/**
		 * Timestamp with Timezone
		 */
		TIMESTAMP_WITH_TIMEZONE,
		/**
		 * NUMBER
		 */
		NUMBER,
		/**
		 * Type was set but was not one of the known types
		 */
		UNKNOWN;
		
		public static SQLType fromJavaSqlTypes2(String type){
			if(type.equals("NUMBER")){
				return SQLType.NUMBER;
			}else if(type.equals("VARCHAR2")){
				return SQLType.VARCHAR2;
			}else{
				return SQLType.UNKNOWN;
			}
		}

		protected static SQLType fromJavaSqlTypes(int typeNumber)
		{
			switch (typeNumber) {
				case Types.ARRAY:
					return SQLType.ARRAY;
				case Types.BIGINT:
					return SQLType.BIGINT;
				case Types.BINARY:
					return SQLType.BINARY;
				case Types.BIT:
					return SQLType.BIT;
				case Types.BLOB:
					return SQLType.BLOB;
				case Types.BOOLEAN:
					return SQLType.BOOLEAN;
				case Types.CHAR:
					return SQLType.CHAR;
				case Types.CLOB:
					return SQLType.CLOB;
				case Types.DATALINK:
					return SQLType.DATALINK;
				case Types.DATE:
					return SQLType.DATE;
				case Types.DECIMAL:
					return SQLType.DECIMAL;
				case Types.DISTINCT:
					return SQLType.DISTINCT;
				case Types.DOUBLE:
					return SQLType.DOUBLE;
				case Types.FLOAT:
					return SQLType.FLOAT;
				case Types.INTEGER:
					return SQLType.INTEGER;
				case Types.JAVA_OBJECT:
					return SQLType.JAVA_OBJECT;
				case Types.LONGNVARCHAR:
					return SQLType.JAVA_OBJECT;
				case Types.LONGVARBINARY:
					return SQLType.LONGVARBINARY;
				case Types.NCHAR:
					return SQLType.NCHAR;
				case Types.NCLOB:
					return SQLType.NCLOB;
				case Types.NULL:
					return SQLType.NULL;
				case Types.NUMERIC:
					return SQLType.NUMERIC;
				case Types.NVARCHAR:
					return SQLType.NVARCHAR;
				case Types.OTHER:
					return SQLType.OTHER;
				case Types.REAL:
					return SQLType.REAL;
				case Types.REF:
					return SQLType.REF;
				case Types.REF_CURSOR:
					return SQLType.REF_CURSOR;
				case Types.ROWID:
					return SQLType.ROWID;
				case Types.SMALLINT:
					return SQLType.SMALLINT;
				case Types.SQLXML:
					return SQLType.SQLXML;
				case Types.STRUCT:
					return SQLType.SQLXML;
				case Types.TIME:
					return SQLType.SQLXML;
				case Types.TIME_WITH_TIMEZONE:
					return SQLType.TIME_WITH_TIMEZONE;
				case Types.TIMESTAMP:
					return SQLType.TIMESTAMP;
				case Types.TINYINT:
					return SQLType.TINYINT;
				case Types.VARBINARY:
					return SQLType.VARBINARY;
				case Types.VARCHAR:
					return SQLType.VARCHAR;
				default:
					return SQLType.UNKNOWN;
			}
		}

		/**
		 * checks weather this type is one of the known types (not UNKNOWN)
		 * 
		 * Note here that {@link SQLType#OTHER} is a considered a known type even though it<s not quite clear what it is !
		 * 
		 * @return true if the Type is anything but {@link SQLType#UNKNOWN}
		 */
		public boolean isKnown()
		{
			return !(this.equals(UNKNOWN));
		}
	}

	private int					columnNumber;
	private SQLType			columnType;
	private int					dislpaySize;
	private String columnLabel;
	public String columnName;
	public String columTypeName;
	public int					precision;
	private int					scale;
	private Nullability	nullability;
	private String catalogName;
	private String columnClassName;
	private String schemaName;
	private String tableName;
	private String remarks;
	private boolean			isAutoIncrement;
	private boolean			isCaseSensitive;
	private boolean			isCurrency;
	private boolean			isDefinitivelyWritable;
	private boolean			isReadOnly;
	private boolean			isSearchable;
	private boolean			isSigned;
	private boolean			isWritable;
	
	private String alias;

	/**
	 * Returns a list of ColumnInfo that are present in this ResultSet
	 * 
	 * @param rs
	 *          the result set to analyze
	 * @return the list of column info present in the Result Set
	 * @throws SQLException
	 *           if there's a problem
	 */
	public static List<ColumnInfo> fromResultSet(ResultSet rs) throws SQLException
	{
		List<ColumnInfo> infoList = new ArrayList<>();
		if (rs == null) return infoList;

		ResultSetMetaData rsmd = rs.getMetaData();
		for (int i = 1; i <= rsmd.getColumnCount(); i++)
		{
			ColumnInfo ci = new ColumnInfo();
			ci.columnNumber = i;
			// Create proper enum to encapsulate this
			int ii = rsmd.getColumnType(i);
			ci.columnType = SQLType.fromJavaSqlTypes(rsmd.getColumnType(i));
			ci.dislpaySize = rsmd.getColumnDisplaySize(i);
			ci.columnLabel = rsmd.getColumnLabel(i);
			ci.columnName = rsmd.getColumnName(i);
			ci.columTypeName = rsmd.getColumnTypeName(i);
			ci.precision = rsmd.getPrecision(i);
			ci.scale = rsmd.getScale(i);
			ci.nullability = Nullability.fromResultSetMetadataInt(rsmd.isNullable(i));
			// ci.catalogName = rsmd.getCatalogName(i);
			ci.columnClassName = rsmd.getColumnClassName(i);
			// ci.schemaName = rsmd.getSchemaName(i);
			ci.isAutoIncrement = rsmd.isAutoIncrement(i);
			ci.isCaseSensitive = rsmd.isCaseSensitive(i);
			ci.isCurrency = rsmd.isCurrency(i);
			// Sci.isDefinitivelyWritable = rsmd.isDefinitelyWritable(i);
//			ci.isReadOnly = rsmd.isReadOnly(i);
			// ci.isSearchable = rsmd.isSearchable(i);
			// ci.isSigned = rsmd.isSigned(i);
			// ci.isWritable = rsmd.isWritable(i);

			infoList.add(ci);
		}

		return infoList;
	}

	/**
	 * Creates the Column Info from a result set that orignated from a call to Database MetaData
	 * 
	 * @param rs
	 *          the REsultSet to analyze
	 * @return the ColumnInfo in this resultSet
	 * @throws SQLException
	 *           boom !
	 */
	public static List<ColumnInfo> fromDatabaseMetaDataResultSet(ResultSet rs) throws SQLException
	{
		/*-
		 * 1.TABLE_CAT String => table catalog (may be null)
		 * 2.TABLE_SCHEM String => table schema (may be null)
		 * 3.TABLE_NAME String => table name
		 * 4.COLUMN_NAME String => column name
		 * 5.DATA_TYPE int => SQL type from java.sql.Types
		 * 6.TYPE_NAME String => Data source dependent type name, for a UDT the type name is fully qualified 
		 * 7.COLUMN_SIZE int => column size.
		 * 8.BUFFER_LENGTH is not used.
		 * 9.DECIMAL_DIGITS int => the number of fractional digits. Null is returned for data types where DECIMAL_DIGITS is not applicable. 
		 * 10.NUM_PREC_RADIX int => Radix (typically either 10 or 2) 
		 * 11.NULLABLE int => is NULL allowed. 
		 *   �� columnNoNulls - might not allow NULL values 
		 *   �� columnNullable - definitely allows NULL values 
		 *   �� columnNullableUnknown - nullability unknown
		 * 12.REMARKS String => comment describing column (may be null)
		 * 13.COLUMN_DEF String => default value for the column, which should be
		 *     interpreted as a string when the value is enclosed in single quotes (may be null) 
		 * 14.SQL_DATA_TYPE int => unused 
		 * 15.SQL_DATETIME_SUB int => unused
		 * 16.CHAR_OCTET_LENGTH int => for char types the maximum number of bytes in the column 
		 * 17.ORDINAL_POSITION int => index of column in table (starting at 1) 
		 * 18.IS_NULLABLE String => ISO rules are used to determine the nullability for a column. 
		 *   �� YES --- if the column can include NULLs 
		 *   �� NO  --- if the column cannot include NULLs 
		 *   �� empty string --- if the nullability for the column is unknown
		 * 19.SCOPE_CATALOG String => catalog of table that is the scope of a reference attribute (null if DATA_TYPE isn't REF) 
		 * 20.SCOPE_SCHEMA String => schema of table that is the scope of a reference attribute (null if the DATA_TYPE isn't REF) 
		 * 21.SCOPE_TABLE String => table name that this the scope of a reference attribute (null if the DATA_TYPE isn't REF)
		 * 22.SOURCE_DATA_TYPE short => source type of a distinct type or user-generated Ref type, 
		 *     SQL type from java.sql.Types (null if DATA_TYPE isn't DISTINCT or user-generated REF) 
		 * 23.IS_AUTOINCREMENT String => Indicates whether this column is auto incremented 
		 *   �� YES --- if the column is auto incremented 
		 *   �� NO --- if the column is not auto incremented 
		 *   �� empty string --- if it cannot be determined whether the column is auto incremented
		 * 24.IS_GENERATEDCOLUMN String => Indicates whether this is a generated column 
		 *   �� YES --- if this a generated column 
		 *   �� NO --- if this not a generated column 
		 *   �� empty string --- if it cannot be determined whether this is a generated column
		 */
		List<ColumnInfo> ciList = new ArrayList<>();
		if (rs == null) return ciList;

		List<ColumnInfo> ci = ColumnInfo.fromResultSet(rs);

		while (rs.next())
		{
			ColumnInfo i = new ColumnInfo();
			for (ColumnInfo columnInfo : ci) {
				switch (columnInfo.getColumnName())
				{
					case "TABLE_CAT":
						i.catalogName = rs.getString(columnInfo.getColumnName());
						break;
					case "TABLE_SCHEM":
						i.schemaName = rs.getString(columnInfo.getColumnName());
						break;
					case "TABLE_NAME":
						i.tableName = rs.getString(columnInfo.getColumnName());
						break;
					case "COLUMN_NAME":
						i.columnName = rs.getString(columnInfo.getColumnName());
						i.alias = rs.getString(columnInfo.getColumnName());
						break;
					case "DATA_TYPE":
						i.columnType = SQLType.fromJavaSqlTypes(rs.getInt(columnInfo.getColumnName()));
						break;
					case "TYPE_NAME":
						i.columTypeName = rs.getString(columnInfo.getColumnName());
						break;
					case "COLUMN_SIZE":
						i.precision = rs.getInt(columnInfo.getColumnName());
						break;
					case "NULLABLE":
						i.nullability = Nullability.fromResultSetMetadataInt(rs.getInt(columnInfo.getColumnName()));
						break;
					case "REMARKS":
						i.remarks = rs.getString(columnInfo.getColumnName());
						break;
					case "IS_AUTOINCREMENT":
						String autoInc = rs.getString(columnInfo.getColumnName());
						i.isAutoIncrement = autoInc != null && "YES".equals(autoInc.toUpperCase());
						break;
					case "ORDINAL_POSITION":
						i.columnNumber = rs.getInt(columnInfo.getColumnName());
						break;
				}
			}
			ciList.add(i);
		}
		return ciList;
	}

	/**
	 * Column Number in the result set.
	 * 
	 * Columns are addressable by index, this is the index for this column
	 * 
	 * @return the index in the result set of this column
	 */
	public int getColumnNumber() {
		return columnNumber;
	}

	/**
	 * @return the declared Type of this column
	 */
	public SQLType getColumnType() {
		return columnType;
	}

	/**
	 * @return displaySize for this column
	 */
	public int getDislpaySize() {
		return dislpaySize;
	}

	/**
	 * @return text Label for this column
	 */
	public String getColumnLabel() {
		return columnLabel;
	}

	/**
	 * @return actual name of this column
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * TODO: this should probably be merged with the enum and get rid of this accessor
	 * 
	 * @return the type name of this column
	 */
	public String getColumTypeName() {
		return columTypeName;
	}

	/**
	 * @return Precision of the data within
	 */
	public int getPrecision() {
		return precision;
	}

	/**
	 * @return the Scale
	 */
	public int getScale() {
		return scale;
	}

	/**
	 * @return Nullability of the column
	 */
	public Nullability getNullability() {
		return nullability;
	}

	/**
	 * @return the CatalogName
	 */
	public String getCatalogName() {
		return catalogName;
	}

	/**
	 * @return Class name for this column
	 */
	public String getColumnClassName() {
		return columnClassName;
	}

	/**
	 * @return the schema Name
	 */
	public String getSchemaName() {
		return schemaName;
	}

	/**
	 * @return the Table Name this column belongs to
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @return Remarks for this column
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @return true if this column is set to auto-increment
	 */
	public boolean isAutoIncrement() {
		return isAutoIncrement;
	}

	/**
	 * @return true if sensitive to casing
	 */
	public boolean isCaseSensitive() {
		return isCaseSensitive;
	}

	/**
	 * @return if it reprensents a currency information
	 */
	public boolean isCurrency() {
		return isCurrency;
	}

	/**
	 * @return if writable
	 */
	public boolean isDefinitivelyWritable() {
		return isDefinitivelyWritable;
	}

	/**
	 * @return Column is read-only
	 */
	public boolean isReadOnly() {
		return isReadOnly;
	}

	/**
	 * @return true if searchable
	 */
	public boolean isSearchable() {
		return isSearchable;
	}

	/**
	 * @return true if signed
	 */
	public boolean isSigned() {
		return isSigned;
	}

	/**
	 * @return true if writable
	 */
	public boolean isWritable() {
		return isWritable;
	}
	

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public void setColumnType(SQLType columnType) {
		this.columnType = columnType;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public void setColumTypeName(String columTypeName) {
		this.columTypeName = columTypeName;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public String toString() {
		return "ColumnInfo [columnName=" + columnName + ", columTypeName=" + columTypeName + ", precision=" + precision + "]";
	}

	@Override
	public int compareTo(Object o) {
		ColumnInfo columnInfo = (ColumnInfo) o;
		String columnName = columnInfo.getColumnName();
		return this.columnName.compareTo(columnName);
	}
}
