package com.example.kcdwebservice.util;

import com.example.kcdwebservice.vo.CmKcdVo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component("ExcelDownload")
public class ExcelDownloadViewResolver extends AbstractXlsxView {
    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Content-Disposition", "attachment; filename=\"KCDListExcel.xlsx\"");

        List<CmKcdVo> list = (List<CmKcdVo>) model.get("list");

        CellStyle cellStyle = workbook.createCellStyle();

        DataFormat numberDataFormat = workbook.createDataFormat();
        cellStyle.setWrapText(true);

        Sheet sheet = workbook.createSheet("Kcd 목록");

        //상단 컬럼.
        Row header = sheet.createRow(0);
        Cell headerCell0 = header.createCell(0);
        headerCell0.setCellValue("KCD코드");
        Cell headerCell1 = header.createCell(1);
        headerCell1.setCellValue("한글명/영문명");
        Cell headerCell2 = header.createCell(2);
        headerCell2.setCellValue("SCTID");
        Cell headerCell3 = header.createCell(3);
        headerCell3.setCellValue("Description");
        Cell headerCell4 = header.createCell(4);
        headerCell4.setCellValue("Flag");
        //상단 컬럼.

        for (int i = 0; i < list.size(); i++) {
            CmKcdVo stat = list.get(i);
            Row row = sheet.createRow(i+1);

            Cell cell0 = row.createCell(0);
            cell0.setCellValue(stat.getKcdCd());

            Cell cell1 = row.createCell(1);
            cell1.setCellValue("ko: "+stat.getKcdKor()+"\n"+ "en: " +stat.getKcdEng());
            cell1.setCellStyle(cellStyle);

            Cell cell2 = row.createCell(2);
            cell2.setCellValue(stat.getSctId()== null?"-":stat.getSctId());

            Cell cell3 = row.createCell(3);
            cell3.setCellValue("-");

            Cell cell4 = row.createCell(4);
            cell4.setCellValue("-");

            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
        }

    }
}
