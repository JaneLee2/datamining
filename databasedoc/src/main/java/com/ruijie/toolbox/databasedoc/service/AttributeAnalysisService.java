package com.ruijie.toolbox.databasedoc.service;

import com.ruijie.toolbox.databasedoc.dao.DbSchemaDao;
import com.ruijie.toolbox.databasedoc.pojo.ColumnInfo;
import com.ruijie.toolbox.databasedoc.pojo.TableInfo;
import com.ruijie.toolbox.databasedoc.util.WordPomUtil;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by RJuly on 2017/7/26.
 */
@Service("attributeAnalysisService")
public class AttributeAnalysisService {
    private static final Logger logger = LoggerFactory.getLogger(AttributeAnalysisService.class);
    public static String[] Table = new String[]{"序号","表名称","表说明"};
    public static String[] TableDetail = new String[]{"字段名称","字段说明","字段类型（长度）","可否为空","是否主键","注释","引用标准"};
    @Autowired
    private DbSchemaDao dbSchemaDao;

    @Value("${targetPath}")
    private String targetPath;

    public void start() throws Exception{

        List<TableInfo> tableInfos = dbSchemaDao.getDetailTableInfoList();

        XWPFDocument docxDocument = new XWPFDocument();
        WordPomUtil.addCustomHeadingStyle(docxDocument, "title1", 1);
        WordPomUtil.addCustomHeadingStyle(docxDocument, "title2", 2);
        WordPomUtil.addContentTitle(docxDocument,"title1","1 数据总表");

        //添加数据库总表信息
        int tableSize = tableInfos.size();
        XWPFTable table = WordPomUtil.addTable(docxDocument, tableSize + 1, 3);
        List<XWPFTableRow> rows = table.getRows();
        for (int i=0; i< rows.size(); i++) {
            XWPFTableRow row = rows.get(i);
            List<XWPFTableCell> cells = row.getTableCells();
            TableInfo tableInfo = null;
            if(i!=0){
                tableInfo = tableInfos.get(i-1);
            }
            for (int j=0; j<cells.size(); j++) {
                XWPFTableCell cell = cells.get(j);
                if(i==0){
                    switch (j){
                        case 0:
                            cell.setText(Table[0]);
                            break;
                        case 1:
                            cell.setText(Table[1]);
                            break;
                        case 2:
                            cell.setText(Table[2]);
                            break;
                        default:
                    logger.error("undefined column:"+(j+1));
                }
                }else{
                    switch (j){
                        case 0:
                            cell.setText(i+"");
                            break;
                        case 1:
                            cell.setText(tableInfo.getTableName());
                            break;
                        case 2:
                            cell.setText(tableInfo.getRemarks());
                            break;
                        default:
                            logger.error("undefined column:"+(j+1));
                    }
                }

            }
        }

        //数据库表的详细开始一个一级标题，每个表都是一个二级标题
        WordPomUtil.addContentTitle(docxDocument,"title1","2 各表详细信息");
        for(int k=0;k<tableInfos.size();k++ ){
            TableInfo tableInfo = tableInfos.get(k);
            String tableNameDetail = "("+(k+1)+") "+tableInfo.getTableName()+" ("+tableInfo.getRemarks()+")";
            WordPomUtil.addContentTitle(docxDocument,"title2",tableNameDetail);

            //添加表详细信息
            List<ColumnInfo> columnInfos = tableInfo.getColumnInfoList();
            int colSize = columnInfos.size();
            XWPFTable tableDetail = WordPomUtil.addTable(docxDocument, colSize + 1, 7);
            List<XWPFTableRow> tableRows = tableDetail.getRows();
            for (int i=0; i< tableRows.size(); i++) {
                XWPFTableRow row = tableRows.get(i);
                List<XWPFTableCell> cells = row.getTableCells();
                ColumnInfo columnInfo = null;
                if(i!=0){
                    columnInfo = columnInfos.get(i-1);
                }
                for (int j=0; j<cells.size(); j++) {
                    XWPFTableCell cell = cells.get(j);
                    if(i==0){
                        switch (j){
                            case 0:
                                cell.setText(TableDetail[0]);
                                break;
                            case 1:
                                cell.setText(TableDetail[1]);
                                break;
                            case 2:
                                cell.setText(TableDetail[2]);
                                break;
                            case 3:
                                cell.setText(TableDetail[3]);
                                break;
                            case 4:
                                cell.setText(TableDetail[4]);
                                break;
                            case 5:
                                cell.setText(TableDetail[5]);
                                break;
                            case 6:
                                cell.setText(TableDetail[6]);
                                break;
                            default:
                                logger.error("undefined column:"+(j+1));
                        }
                    }else{
                        switch (j){
                            case 0:
                                cell.setText(columnInfo.getColumnName());
                                break;
                            case 1:
                                cell.setText(columnInfo.getRemarks());
                                break;
                            case 2:
                                cell.setText(columnInfo.getColumTypeName().toLowerCase()+"("+columnInfo.getPrecision()+")");
                                break;
                            case 3:
                                cell.setText(isNullString(columnInfo.getNullability()));
                                break;
                            case 4:
                                cell.setText(isPrimaryKey(columnInfo.isAutoIncrement()));
                                break;
                            case 5:
                                cell.setText("");
                                break;
                            case 6:
                                cell.setText("");
                                break;
                            default:
                                logger.error("undefined column:"+(j+1));
                        }
                    }

                }
            }
        }

        // word写入到文件
        WordPomUtil.saveDocument(docxDocument,targetPath+".docx");

    }

    /**
     * 返回字段是否为空
     * @param nullability
     */
    public static String isNullString(ColumnInfo.Nullability nullability){
        String nullString = "";
        switch (nullability) {
            case notNullable:
                nullString = "不可为空";
                break;
            default:
        }
        return nullString;
    }

    /**
     * 此处只判断自增字段的主键
     * @param key
     */
    public static String isPrimaryKey(boolean key){
        if(key){
            return "是";
        }else{
            return "";
        }
    }
}
