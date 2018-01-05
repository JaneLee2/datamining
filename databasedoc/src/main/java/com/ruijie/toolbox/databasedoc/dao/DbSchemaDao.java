package com.ruijie.toolbox.databasedoc.dao;

import com.ruijie.toolbox.databasedoc.pojo.ColumnInfo;
import com.ruijie.toolbox.databasedoc.pojo.TableInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.List;

/**
 * Created by RJuly on 2017/7/26.
 */
@Component
public class DbSchemaDao {
    private static final Logger logger = LoggerFactory.getLogger(DbSchemaDao.class);
    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    public static final String[] types = {
            "TABLE"
    };
    //获取数据源的所有表信息(不含字段信息)
    public List<TableInfo> getTableInfoList() throws Exception {
        DatabaseMetaData meta = jdbcTemplate.getDataSource().getConnection().getMetaData();
        ResultSet rs = meta.getTables(null,null, "%", types);
        List<TableInfo> tableInfoList = TableInfo.fromDatabaseMetaDataResultSet(rs);
        return tableInfoList;
    }
    //获取数据源的所有表详细信息(含字段信息)
    public List<TableInfo> getDetailTableInfoList() throws Exception {
        DatabaseMetaData meta = jdbcTemplate.getDataSource().getConnection().getMetaData();
        ResultSet rs = meta.getTables(null,null, "%", types);
        List<TableInfo> tableInfoList = TableInfo.fromDatabaseMetaDataResultSet(rs);
        for(TableInfo tableInfo : tableInfoList){
            try(ResultSet columns = meta.getColumns(null, null, tableInfo.getTableName(), null)){
                tableInfo.setColumnInfoList(ColumnInfo.fromDatabaseMetaDataResultSet(columns));
            }
        }
       return tableInfoList;
    }
}
