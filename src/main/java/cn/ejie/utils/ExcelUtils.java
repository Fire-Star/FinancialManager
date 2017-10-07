package cn.ejie.utils;

import cn.ejie.exception.SimpleException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelUtils {
    public static void createExcel(String bathPath, String fileName, List<List<String>> insertData,String sheetName,String titleName[]) throws SimpleException, IOException {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(sheetName);
        Row titleRow = sheet.createRow(1);

        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);

        for (int i = 0; i < titleName.length; i++) {
            String tempTitle = titleName[i];
            Cell tempCell = titleRow.createCell(i+1);
            tempCell.setCellValue(tempTitle);
            tempCell.setCellStyle(cellStyle);
        }
        int allCellCount = titleName.length;
        int startRowIndex = 2;
        int allRow = insertData.size();
        for (int row = 0; row < allRow; row++) {
            Row tempRow = sheet.createRow(row+startRowIndex);
            List<String> tempData = insertData.get(row);
            for (int cell = 0; cell < allCellCount; cell++) {

                Cell targetCell = tempRow.createCell(cell+1);
                if(cell == 0){
                    targetCell.setCellValue(row+1);
                }else{
                    targetCell.setCellValue(tempData.get(cell-1));
                }
            }
        }
        try {
            wb.write(new FileOutputStream(bathPath+fileName));
        } catch (IOException e) {
            throw new SimpleException("errorType","创建Excel失败！");
        }
        wb.close();
    }
}
