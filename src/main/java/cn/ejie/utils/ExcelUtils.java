package cn.ejie.utils;

import cn.ejie.exception.SimpleException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.LinkedList;
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

    public static List<List<String>> objectProToStrList(List<?> insertData,String [] objectProName){
        //你害怕时间从你的指尖逝去，所以你不敢敲代码太入迷.
        List<List<String>> result = new LinkedList<>();
        for (Object tempInsert : insertData) {
            Class tempClazz = tempInsert.getClass();
            List<String> tempItem = new LinkedList<>();

            for (String tempPro : objectProName) {
                try {
                    Field tempField = getTargetField(tempPro,tempClazz);
                    tempField.setAccessible(true);
                    String tempProValue = (String) tempField.get(tempInsert);
                    tempItem.add(tempProValue);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            result.add(tempItem);
        }
        return result;
    }

    public static Field getTargetField(String fieldName,Class targetClazz){
        Field result = null;
        while (targetClazz != null){
            try {
                result = targetClazz.getDeclaredField(fieldName);
                return result;
            } catch (NoSuchFieldException e) {
                targetClazz = targetClazz.getSuperclass();
            }
        }
        return result;
    }
}
