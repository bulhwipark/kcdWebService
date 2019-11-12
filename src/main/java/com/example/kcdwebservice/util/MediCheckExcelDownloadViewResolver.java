package com.example.kcdwebservice.util;

import com.example.kcdwebservice.vo.CmKexamVo;
import com.example.kcdwebservice.vo.CmMedicineVo;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component("mediCheckExcelDownload")
public class MediCheckExcelDownloadViewResolver extends AbstractXlsxView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Content-Disposition", "attachment; filename=\"MediCheckListExcel.xlsx\"");
        String[] headerNmArr = (String[]) model.get("headerNmArr");
        String sheetNm = (String) model.get("sheetNm");
        List<CmKexamVo> list = (List<CmKexamVo>) model.get("list");

        CellStyle cellStyle = workbook.createCellStyle();

        DataFormat numberDataFormat = workbook.createDataFormat();
        cellStyle.setWrapText(true);

        Sheet sheet = workbook.createSheet(sheetNm);

        //상단 컬럼.
        Row header = sheet.createRow(0);
        for(int i = 0; i<headerNmArr.length; i++){
            Cell headerCell0 = header.createCell(i);
            headerCell0.setCellValue(headerNmArr[i]);
        }
        //상단 컬럼.

       // long start = System.currentTimeMillis();
        for (int i = 0; i < list.size(); i++) {
            /*
            if (i % 20 == 0) {
                System.out.printf("count: %,d, 시간: %,dms \n", i, System.currentTimeMillis() - start);
                start = System.currentTimeMillis();
            }
            */
            CmKexamVo stat = list.get(i);
            Row row = sheet.createRow(i + 1);

            Cell cell0 = row.createCell(0);
            cell0.setCellValue(stat.getKexCd());

            Cell cell1 = row.createCell(1);
            cell1.setCellValue("ko: " + stat.getKexKor() + "\n" + "en: " + stat.getKexEng());
            cell1.setCellStyle(cellStyle);

            Cell cell2 = row.createCell(2);
            //cell2.setCellValue(stat.getSctId() == null ? "-" : stat.getSctId());
            cell2.setCellValue("-");

            Cell cell3 = row.createCell(3);
            cell3.setCellValue("-");

            Cell cell4 = row.createCell(4);
            if (stat.getMapStatCd() != null) {
                //cell4.setCellValue(stat.getMapStatNm() + "(" + stat.getMapStatCd() + ")");
                cell4.setCellValue("" + "(" + stat.getMapStatCd() + ")");
            } else {
                cell4.setCellValue("-");
            }

            Cell cell5 = row.createCell(5);
            //cell5.setCellValue(stat.getUdtDt());
            cell5.setCellValue(new Date());
        }
        // TODO width 강제로 주기
        /*
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
        */
    }
}
