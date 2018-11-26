package com.ruijie.toolbox.databasedoc.pojo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulate the table information.
 * 
 * @author edaigneault
 *
 */
public class TableInfo {

	/**
	 * Different types of tables supported by JDBC
	 * 
	 * @author edaigneault
	 *
	 */
	public enum Type {
		/**
		 * A Normal Table
		 */
		TABLE,
		/**
		 * View
		 */
		VIEW,
		/**
		 * A System Table
		 */
		SYSTEM_TABLE,
		/**
		 * Global Temporary table
		 */
		GLOBAL_TEMPORARY,
		/**
		 * Local Temp table
		 */
		LOCAL_TEMPORARY,
		/**
		 * an Alias to a table
		 */
		ALIAS,
		/**
		 * a Synonym to a Table
		 */
		SYNONYM,
		/**
		 * Table Type was set but could not map to a known value
		 */
		UNKNOWN,

		/**
		 * The table type was not returned (was null)
		 */
		UNDEFINED;

		protected static Type fromDatabaseMetaDataTypeName(String typeName)
		{
			if (typeName == null) return UNDEFINED;
			switch (typeName)
			{
				case "TABLE":
					return TABLE;
				case "VIEW":
					return VIEW;
				case "SYSTEM TABLE":
					return SYSTEM_TABLE;
				case "GLOBAL TEMPORARY":
					return GLOBAL_TEMPORARY;
				case "LOCAL TEMPORARY":
					return LOCAL_TEMPORARY;
				case "ALIAS":
					return ALIAS;
				case "SYNONYM":
					return SYNONYM;
				default:
					return UNKNOWN;
			}
		}

		/**
		 * checks weather this type is one of the known types (not UNKNOWN and not UNDEFINED)
		 * 
		 * @return true if the Type is anything but {@link Type#UNDEFINED} or {@link Type#UNKNOWN}
		 */
		public boolean isKnown()
		{
			return !(this.equals(UNDEFINED) || this.equals(UNKNOWN));
		}
	}

	/**
	 * Primary Key generation types
	 * 
	 * @author edaigneault
	 *
	 */
	public enum PrimaryKeyGeneration
	{
		/**
		 * System generated
		 */
		SYSTEM,
		/**
		 * User Set
		 */
		USER,
		/**
		 * Derived
		 */
		DERIVED,
		/**
		 * Primary Key generation was set but could not map to a known value
		 */
		UNKNOWN,
		/**
		 * API returned null
		 */
		UNDEFINED;

		protected static PrimaryKeyGeneration fromDatabaseMetaDataRefGeneration(String refGenerationName)
		{
			if (refGenerationName == null) return UNDEFINED;
			switch (refGenerationName)
			{
				case "SYSTEM":
					return SYSTEM;
				case "USER":
					return USER;
				case "DERIVED":
					return DERIVED;
				default:
					return UNKNOWN;
			}
		}

		/**
		 * Checks weather this type is one of the known types (not {@link Type#UNDEFINED} and not {@link Type#UNKNOWN})
		 * 
		 * @return true if the Type is anything but {@link Type#UNDEFINED} or {@link Type#UNKNOWN}
		 */
		public boolean isKnown()
		{
			return !(this.equals(UNDEFINED) || this.equals(UNKNOWN));
		}
	}

	public static List<TableInfo> fromDatabaseMetaDataResultSet(ResultSet rs) throws SQLException
	{
		List<TableInfo> infoList = new ArrayList<>();
		if (rs == null) return infoList;

		List<ColumnInfo> ci = ColumnInfo.fromResultSet(rs);

		while (rs.next())
		{
			TableInfo ti = new TableInfo();
			for (ColumnInfo columnInfo : ci) {
				switch (columnInfo.getColumnName().toUpperCase())
				{
					case "TABLE_CAT":
						ti.catalogName = rs.getString(columnInfo.getColumnName());
						break;
					case "TABLE_SCHEM":
						ti.schemaName = rs.getString(columnInfo.getColumnName());
						break;
					case "TABLE_NAME":
						ti.tableName = rs.getString(columnInfo.getColumnName());
						ti.alias = rs.getString(columnInfo.getColumnName());
						break;
					case "TABLE_TYPE":
						ti.tableType = Type.fromDatabaseMetaDataTypeName(rs.getString(columnInfo.getColumnName()));
						break;
					case "REMARKS":
						ti.remarks = rs.getString(columnInfo.getColumnName());
						break;
					case "TYPE_CAT":
						ti.catalogType = rs.getString(columnInfo.getColumnName());
						break;
					case "TYPE_SCHEM":
						ti.schemaType = rs.getString(columnInfo.getColumnName());
						break;
					case "TYPE_NAME":
						ti.typeName = rs.getString(columnInfo.getColumnName());
						break;
					case "SELF_REFERENCING_COL_NAME":
						ti.idColName = rs.getString(columnInfo.getColumnName());
						break;
					case "REF_GENERATION":
						ti.idGeneration = PrimaryKeyGeneration.fromDatabaseMetaDataRefGeneration(rs.getString(columnInfo.getColumnName()));
						break;
				}
			}
			infoList.add(ti);
		}
		return infoList;
	}

	private String catalogName;
	private String schemaName;
	private String tableName;
	private String remarks;
	private Type									tableType;
	private String catalogType;
	private String schemaType;
	private String typeName;
	private String idColName;
	private PrimaryKeyGeneration	idGeneration;
	private List<ColumnInfo> columnInfoList;
	//为了给table编码使用，不改变则为本身
	private String alias;

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @return the CatalogName, null if not set or available
	 */
	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setTableType(Type tableType) {
		this.tableType = tableType;
	}

	/**
	 * @return the name of the schema, null if not available or not set
	 */
	public String getSchemaName() {
		return schemaName;
	}

	/**
	 * @return the name of the Table
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @return remarks, null if not set
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @return the {@link Type} of the table
	 */
	public Type getTableType() {
		return tableType;
	}

	/**
	 * @return the catalogType (free text) or null if not set
	 */
	public String getCatalogType() {
		return catalogType;
	}

	/**
	 * @return the SchemaType (free text) or null if not set
	 */
	public String getSchemaType() {
		return schemaType;
	}

	/**
	 * TODO: should probably be set in the {@link Type} directly and get rid of this accessor
	 * 
	 * @return Table type name
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * @return name of the ID (primary Key) column
	 */
	public String getIdColName() {
		return idColName;
	}

	/**
	 * @return the {@link PrimaryKeyGeneration} used to generate the primaryKey
	 */
	public PrimaryKeyGeneration getIdGeneration() {
		return idGeneration;
	}

	/**
	 * @return the list of all {@link ColumnInfo} for this table
	 */
	public List<ColumnInfo> getColumnInfoList() {
		return columnInfoList;
	}

	public void setColumnInfoList(List<ColumnInfo> columnInfoList) {
		this.columnInfoList = columnInfoList;
	}

	@Override
	public String toString() {
		return "TableInfo [catalogName=" + catalogName + ", schemaName=" + schemaName + ", tableName=" + tableName + ", remarks=" + remarks + ", tableType=" + tableType + ", catalogType=" + catalogType + ", schemaType=" + schemaType + ", typeName=" + typeName + ", idColName=" + idColName + ", idGeneration=" + idGeneration + "]";
	}
}
