package com.ruijie.toolbox.databasedoc.util;

import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by RJuly on 2017/7/27.
 */
public class WordPomUtil {
    private static final Logger logger = LoggerFactory.getLogger(WordPomUtil.class);

    /**
     * 增加一个表格
     * @param docxDocument 目标文档
     * @param rowSize  表格行大小
     * @param columnSize  表格列大小
     */
    public static XWPFTable addTable(XWPFDocument docxDocument,
                                int rowSize,
                                int columnSize){
        XWPFTable table = docxDocument.createTable(rowSize, columnSize);
        List<XWPFTableRow> rows = table.getRows();
        //表格属性:调整宽度
        CTTblPr tablePr = table.getCTTbl().addNewTblPr();
        CTTblWidth width = tablePr.addNewTblW();
        width.setW(BigInteger.valueOf(8050));
        for (int i=0; i< rows.size(); i++) {
            XWPFTableRow row = rows.get(i);
            //设置行的高度
            row.setHeight(450);
            List<XWPFTableCell> cells = row.getTableCells();
            int cellSize = cells.size();
            for (int j=0; j<cellSize; j++) {
                XWPFTableCell cell = cells.get(j);
                //单元格属性
                CTTcPr cellPr = cell.getCTTc().addNewTcPr();
                cellPr.addNewVAlign().setVal(STVerticalJc.CENTER);
            }
        }
        return table;
    }
    /**
     * 给指定内容加指定等级的标题
     * @param docxDocument 目标文档
     * @param strStyleId 样式名称
     * @param content 需要加标题的内容
     */
    public static void addContentTitle(XWPFDocument docxDocument, String strStyleId,String content){
        XWPFParagraph paragraph = docxDocument.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setBold(true);
        run.setText(content);
        paragraph.setStyle(strStyleId);
        if(strStyleId.equals("title1")){
            run.setFontSize(20);
        }else if(strStyleId.equals("title2")){
            run.setFontSize(18);
        }
    }

    /**
     * 增加自定义标题样式。这里用的是stackoverflow的源码
     * @param docxDocument 目标文档
     * @param strStyleId 样式名称
     * @param headingLevel 样式级别
     */
    public static void addCustomHeadingStyle(XWPFDocument docxDocument, String strStyleId, int headingLevel) throws Exception{

        CTStyle ctStyle = CTStyle.Factory.newInstance();
        ctStyle.setStyleId(strStyleId);

        CTString styleName = CTString.Factory.newInstance();
        styleName.setVal(strStyleId);
        ctStyle.setName(styleName);

        CTDecimalNumber indentNumber = CTDecimalNumber.Factory.newInstance();
        indentNumber.setVal(BigInteger.valueOf(headingLevel));

        // lower number > style is more prominent in the formats bar
        ctStyle.setUiPriority(indentNumber);

        CTOnOff onoffnull = CTOnOff.Factory.newInstance();
        ctStyle.setUnhideWhenUsed(onoffnull);

        // style shows up in the formats bar
        ctStyle.setQFormat(onoffnull);

        // style defines a heading of the given level
        CTPPr ppr = CTPPr.Factory.newInstance();
        ppr.setOutlineLvl(indentNumber);
        ctStyle.setPPr(ppr);

        XWPFStyle style = new XWPFStyle(ctStyle);

        // is a null op if already defined
        XWPFStyles styles = docxDocument.createStyles();

        style.setType(STStyleType.PARAGRAPH);
        styles.addStyle(style);

    }
    /**
     * @Description: 保存文档
     */
    public static void saveDocument(XWPFDocument document, String savePath)
            throws Exception {
        FileOutputStream fos = new FileOutputStream(savePath);
        document.write(fos);
        fos.close();
    }
}
