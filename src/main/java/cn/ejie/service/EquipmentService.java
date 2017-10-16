package cn.ejie.service;

import cn.ejie.dao.EquipmentMapper;
import cn.ejie.dao.EquipmentNameMapper;
import cn.ejie.dao.EquipmentStateMapper;
import cn.ejie.exception.EquipmentException;
import cn.ejie.exception.SimpleException;
import cn.ejie.po.MaxValue;
import cn.ejie.pocustom.EquipmentCustom;
import cn.ejie.pocustom.EquipmentNameCustom;
import cn.ejie.utils.BeanPropertyValidateUtils;
import cn.ejie.utils.ExcelUtils;
import cn.ejie.utils.SimpleBeanUtils;
import cn.ejie.utils.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/21.
 */
public class EquipmentService {
    private static final String inserState = "闲置";
    public static String BASE_PATH = "C:\\";
    public static final String UPLOAD_DIR = BASE_PATH;
    private static final String EQ_MODEL_FILE = BASE_PATH+"设备模板.xlsx";
    public static final long MAX_FILE_SISE = 61440; //为 60 MB
    private String basePathLocation;//

    public String getBasePath() {
        return BASE_PATH;
    }

    public void setBasePath(String basePath) {
        BASE_PATH = basePath;
    }

    @Autowired
    private EquipmentMapper equipmentMapper;

    @Autowired
    private EquipmentStateMapper equipmentStateMapper;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private CityService cityService;

    @Autowired
    private EquipmentTypeService equipmentTypeService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private EquipmentNameService equipmentNameService;

    @Autowired
    private EquipmentNameMapper equipmentNameMapper;

    @Autowired
    private MaxValueService maxValueService;

    private String errorType = "errorType";

    public void insertSingleEquipment(EquipmentCustom equipmentCustom) throws Exception{
        BeanPropertyValidateUtils.validateIsEmptyProperty(equipmentCustom);//验证Bean属性是否为空！！！
        String countStr = equipmentCustom.getBuyCount();

        int len = countStr.length();
        for(int i=0;i<len;i++){
            if(!Character.isDigit(countStr.charAt(i))){
                throw new SimpleException(errorType,"购买数量只能够为整数！");
            }
        }
        Integer iCount = Integer.parseInt(countStr);

        String supplier = equipmentCustom.getSupplier();//供应商数据校验
        String supplierID = supplierService.findSupIdBySupName(supplier);
        if(supplierID == null){
            throw new SimpleException(errorType,"设备供应商不存在！");
        }
        String eqType = equipmentCustom.getEqType();//设备类型数据校验
        String eqTypeID = equipmentTypeService.findEquipmentTypeIDByTypeName(eqType);
        if(eqTypeID == null){
            throw new SimpleException(errorType,"设备类型不存在！");
        }
        String eqName = equipmentCustom.getEqName();
        EquipmentNameCustom equipmentNameCustom = new EquipmentNameCustom();
        equipmentNameCustom.setEqTypeId(eqTypeID);
        equipmentNameCustom.setEqName(eqName);
        Integer eqNameCount = equipmentNameService.findEquipmentNameCountByEquipmentNameAndType(equipmentNameCustom);
        if(eqNameCount <= 0){
            throw new SimpleException(errorType,"设备名称不存在！");
        }

        String stateID = equipmentStateMapper.findStateIDByStateName(inserState);//查找ID并设置ID
        equipmentCustom.setEqStateId(stateID);


        //将字符串转换为ID
        String purchaseCityID = cityService.findCityIDByCity(equipmentCustom.getBuyCity());
        String purchaseDepartID = departmentService.findDepartIDByCityStrAndDepartStr(equipmentCustom.getBuyCity(),equipmentCustom.getPurchasDepart());

        String belongCityID = cityService.findCityIDByCity(equipmentCustom.getCity());
        String belongDepartID = departmentService.findDepartIDByCityStrAndDepartStr(equipmentCustom.getCity(),equipmentCustom.getBelongDepart());

        equipmentCustom.setBuyCity(purchaseCityID);
        equipmentCustom.setPurchasDepart(purchaseDepartID);

        equipmentCustom.setCity(belongCityID);
        equipmentCustom.setBelongDepart(belongDepartID);


        equipmentCustom.setPurchasTime(StringUtils.zhDateStrToENDateStr(equipmentCustom.getPurchasTime()));//将时间转换并设置

        String cityOtherID = cityService.findCityOtherIDByCityID(belongCityID);//查找出城市ID

        String eqTypeOtherId = equipmentTypeService.findEquipmentTypeOtherIDByTypeName(equipmentCustom.getEqType());//查找出设备类型ID

        int counti = iCount.intValue();
        int countSuccessi = counti;
        List<EquipmentCustom> insertSuccessFile = new LinkedList<>();
        while (counti-->0){
            String eqMaxValue = null;
            try {
                eqMaxValue = String.valueOf((Integer.parseInt(maxValueService.findValueByKey("EqMaxValue"))+1));
            }catch (Exception e){
                if(eqMaxValue == null){
                    maxValueService.updateState("EqMaxValue","1");
                    eqMaxValue = "1";
                }
            }

            String eqOtherIdAfter = StringUtils.fillPreString(eqMaxValue,'0',4);//计算出设备ID

            equipmentCustom.setEqOtherId(cityOtherID+eqTypeOtherId+eqOtherIdAfter);
            equipmentMapper.insertSingleEquipment(equipmentCustom);
            maxValueService.updateState("EqMaxValue",eqMaxValue);
        }
        String buyCityID = equipmentCustom.getBuyCity();
        String buyDepID = equipmentCustom.getPurchasDepart();
        String belongCityIDx = equipmentCustom.getCity();
        String belongDepartIDx = equipmentCustom.getBelongDepart();
        String stateIDx = equipmentCustom.getEqStateId();

        String buyCityStr = cityService.findCityNameByCityID(buyCityID);
        String buyDepStr = departmentService.findDepartNameByDepId(buyDepID);
        String belongCityStr = cityService.findCityNameByCityID(belongCityIDx);
        String belongDepartStr = departmentService.findDepartNameByDepId(belongDepartIDx);
        String stateStr = equipmentStateMapper.findStateNameById(stateIDx);

        equipmentCustom.setBuyCity(buyCityStr);
        equipmentCustom.setPurchasDepart(buyDepStr);
        equipmentCustom.setCity(belongCityStr);
        equipmentCustom.setBelongDepart(belongDepartStr);
        equipmentCustom.setEqStateId(stateStr);

        equipmentCustom.setBuyCount("1台");

        while (countSuccessi-->0){
            insertSuccessFile.add(equipmentCustom);
        }

        createInsertEqSuccessExcel(insertSuccessFile);
    }

    public List<EquipmentCustom> findAllBySql(String sql) throws Exception{
        List<EquipmentCustom> list = new ArrayList<EquipmentCustom>();
        try {
            list = equipmentMapper.findAllBySql(sql);
        }catch (Exception e){
            e.printStackTrace();
            throw new EquipmentException("equipmentErrorType","数据库发生错误！");
        }
        return list;
    }

    public List<EquipmentCustom> findAll() throws Exception{
        List<EquipmentCustom> list = new ArrayList<EquipmentCustom>();
        try{
            list = equipmentMapper.findAll();
        }catch (Exception e){
            e.printStackTrace();
            throw new EquipmentException("equipmentErrorType","数据库发生错误！");
        }
        return list;
    }

    public EquipmentCustom findById(String id) throws Exception{
        EquipmentCustom equipmentCustom = new EquipmentCustom();
        try {
            equipmentCustom = equipmentMapper.findById(id);
        }catch (Exception e){
            e.printStackTrace();
            throw new EquipmentException("equipmentErrorType","数据库发生错误！");
        }
        return equipmentCustom;
    }

    public void delEquipmentByEqTypeAndEqName(EquipmentCustom equipmentCustom) throws Exception{
        String eqType = equipmentCustom.getEqType();
        String eqName = equipmentCustom.getEqName();
        if(eqType==null || eqType.equals("")){
            throw new SimpleException("equipmentErrorType","删除设备时，设备类型不能为空！！！");
        }
        if(eqName == null || eqName.equals("")){
            throw new SimpleException("equipmentErrorType","删除设备时，设备名称不能为空！！！");
        }

        equipmentMapper.delEquipmentByEqTypeAndEqName(equipmentCustom);
    }

    public void delEquipmentByEqType(String eqType) throws Exception{
        if(eqType == null || eqType.equals("")){
            throw new SimpleException("equipmentErrorType","删除设备时，设备类型不能为空！！！");
        }
        equipmentMapper.delEquipmentByEqType(eqType);
    }

    public Integer countEquipmentByEqTypeAndEqName(EquipmentCustom equipmentCustom) throws Exception{
        String eqType = equipmentCustom.getEqType();
        String eqName = equipmentCustom.getEqName();
        if(eqType==null || eqType.equals("")){
            throw new SimpleException("equipmentErrorType","查询设备时，设备类型不能为空！！！");
        }
        if(eqName == null || eqName.equals("")){
            throw new SimpleException("equipmentErrorType","查询设备时，设备名称不能为空！！！");
        }
        Integer count = equipmentMapper.countEquipmentByEqTypeAndEqName(equipmentCustom);
        if(count == null){
            throw new SimpleException("equipmentErrorType","系统数据库发生错误，请通知管理员！！！");
        }
        return count;
    }

    public Integer countEquipmentByEqType(String eqType) throws Exception{
        if(eqType==null || eqType.equals("")){
            throw new SimpleException("equipmentErrorType","查询设备时，设备类型不能为空！！！");
        }
        Integer count = equipmentMapper.countEquipmentByEqType(eqType);
        if(count == null){
            throw new SimpleException("equipmentErrorType","系统数据库发生错误，请通知管理员！！！");
        }
        return  count;
    }
    public Integer countEquipment(String city) throws Exception{
        if(city==null || city.equals("")){
            throw new SimpleException(errorType,"查询公司设备时，城市不能为空！");
        }
        Integer count = equipmentMapper.countEquipmentByCity(city);
        if(count==null){
            throw new SimpleException(errorType,"查询公司设备时，系统发生故障！");
        }
        return count;
    }

    public Integer countEquipmentByDepartment(String department) throws Exception{
        if(department == null || department.equals("")){
            throw new SimpleException(errorType,"查询公司设备时，部门不能为空！");
        }
        Integer count = equipmentMapper.countEquipmentByDepartment(department);
        if(count==null){
            throw new SimpleException(errorType,"查询公司设备时，系统发生故障！");
        }
        return count;
    }

    public Integer countAnyEquipmentByCity(String cityID) throws Exception{
        if(cityID == null || cityID.equals("")){
            throw new SimpleException(errorType,"查询公司设备时，公司名称不能为空！");
        }
        Integer count = equipmentMapper.countEquipmentByCity(cityID);
        if(count == null){
            throw new SimpleException(errorType,"通过城市查询设备数量时，系统发生错误！！！");
        }
        return count;
    }

    public void updateEquipment(EquipmentCustom equipmentCustom) throws Exception{
        try {
            String cityOtherID = cityService.findCityOtherIDByCityID(equipmentCustom.getCity());//查找出城市ID
            String eqTypeOtherId = equipmentTypeService.findEquipmentTypeOtherIDByTypeName(equipmentCustom.getEqType());//查找出设备类型ID
            String eqMaxValue = null;
            try {
                eqMaxValue = String.valueOf((Integer.parseInt(maxValueService.findValueByKey("EqMaxValue"))+1));
            }catch (Exception e){
                if(eqMaxValue == null){
                    maxValueService.updateState("EqMaxValue","1");
                    eqMaxValue = "1";
                }
            }
            String eqOtherIdAfter = StringUtils.fillPreString(eqMaxValue,'0',4);//计算出设备ID
            equipmentCustom.setEqOtherId(cityOtherID+eqTypeOtherId+eqOtherIdAfter);
            maxValueService.updateState("EqMaxValue",eqMaxValue);
            equipmentMapper.updateEquipment(equipmentCustom);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<EquipmentCustom> findAllKindsEq() throws Exception{
        List<EquipmentCustom> equipmentCustomList = new ArrayList<EquipmentCustom>();
        try {
            equipmentCustomList = equipmentMapper.findAllKindsEq();
        }catch (Exception e){
            e.printStackTrace();
        }
        return equipmentCustomList;
    }

    public List<EquipmentCustom> findAllKindsEqByCityId(String cityId) throws Exception{
        List<EquipmentCustom> equipmentCustomList = new ArrayList<EquipmentCustom>();
        try {
            equipmentCustomList = equipmentMapper.findAllKindsEqByCityId(cityId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return equipmentCustomList;
    }

    public Integer countEqForStatis(String eqName,String brand,String cityId) throws Exception{
        Integer num = 0;
        try {
            num = equipmentMapper.countEqForStatis(eqName,brand,cityId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return num;
    }

    public Double sumEqMoneyForStatis(String eqName,String brand,String cityId) throws Exception{
        Double sum = 0.0;
        try {
            sum = equipmentMapper.sumEqMoneyForStatis(eqName,brand,cityId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return sum;
    }

    public Integer countEqForStatisByState(String eqName,String brand,String cityId,String stateId) throws Exception{
        Integer num = 0;
        try {
            num = equipmentMapper.countEqForStatisByState(eqName,brand,cityId,stateId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return num;
    }

    public void uploadFile(MultipartFile file) throws Exception {
        //获取文件类型
        String contentType = file.getContentType();


        if(!contentType.equals("")) {
            //可以对文件类型进行检查
        }
        //获取input域的name属性
        String name = file.getName();
        //获取文件名，带扩展名
        String originFileName = file.getOriginalFilename();
        //获取文件扩展名
        String extension = originFileName.substring(originFileName.lastIndexOf("."));
        if(!".xlsx".equals(extension)){
            throw new SimpleException(errorType,"上传文件类型错误，必须为xlsx格式！");
        }
        //获取文件大小，单位字节
        long site = file.getSize();
        if(site > MAX_FILE_SISE) {
            //可以对文件大小进行检查
        }

        //构造文件上传后的文件绝对路径，这里取系统时间戳＋文件名作为文件名
        //不推荐这么写，这里只是举例子，这么写会有并发问题
        //应该采用一定的算法生成独一无二的的文件名
        originFileName = originFileName.substring(0,originFileName.lastIndexOf("."));
        String fileSimpleName = originFileName+"-"+String.valueOf(System.currentTimeMillis()) + extension;
        String fileName = UPLOAD_DIR + fileSimpleName;
        try {
            file.transferTo(new File(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String findValueFileName = null;
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            findValueFileName = maxValueService.findValueByKey(userName+"-eqTargetSource");
        } catch (Exception e) {}
        if(findValueFileName == null){
            MaxValue maxValue = new MaxValue();
            maxValue.setKey(userName+"-eqTargetSource");
            maxValue.setValue("1");
            maxValueService.insertMaxValue(maxValue);
        }else if(!findValueFileName.equals("")&&!findValueFileName.equals("1")){
            File targetFileSource = new File(BASE_PATH+findValueFileName);
            targetFileSource.delete();
        }
        //不管是否为 null ，都会更新数据库的上传文件名称，但是不包含路径。
        MaxValue updateParam = new MaxValue();
        updateParam.setKey(userName+"-eqTargetSource");
        updateParam.setValue(fileSimpleName);
        maxValueService.updataMaxValue(updateParam);

        insertXslEquipment(fileName);
    }

    private String[] eqCellName = {"eqType","eqName","brandName","supplier","buyCity","purchasDepart","city","belongDepart","eqStateId","purchasPrice","purchasTime","buyCount"};
    private String[] eqCellTitleName ={"序列号","设备类型","设备名称","品牌","供应商","采购城市","采购部门","归属城市","归属部门","状态","采购价格","采购时间","备注","错误信息"};
    private String[] titleSuccessNameStr = {"序列号","设备类型","设备名称","品牌","供应商","采购城市","采购部门","归属城市","归属部门","状态","采购价格","采购时间","备注"};

    /**
     * 对于excel的要求，第一行必须是列头
     * @param fileName
     */
    private void insertXslEquipment(String fileName) throws Exception{
        List<EquipmentCustom> allEquipments = new LinkedList<>();
        try {
            XSSFWorkbook wb = null;
            try{
                wb = new XSSFWorkbook(fileName);
            }catch (Exception e){
                throw new SimpleException(errorType,"你上传的文件不符合系统要求，请重新上传！");
            }
            int sheetCount = 1;//获取xls中的Sheet页数
            for (int sheet = 0; sheet < sheetCount; sheet++) {
                XSSFSheet tempSheet = wb.getSheetAt(sheet);
                if(tempSheet == null){
                    throw new SimpleException(errorType,"excel内容错误，不存在数据表！");
                }

                Row titleRow = tempSheet.getRow(1);
                if(titleRow==null){
                    throw new SimpleException(errorType,"设备信息不满足要求，第二行必须为标题！");
                }
                ExcelUtils.valatileExcelTitle(titleRow,errorType,titleSuccessNameStr,"设备");

                int rowCount = tempSheet.getLastRowNum()+1;//获取当前Sheet页中包括的行数
                for (int row = 0; row < rowCount; row++) {
                    XSSFRow tempRow = tempSheet.getRow(row);//获取当前页中的每一行
                    if(row == 0 && tempRow != null){
                        throw new SimpleException(errorType,"excel格式错误，excel第一行必须曾经未加入过数据，并且现在为空。解决方式为：新插入行，删除旧行，请修改文件后再上传！");
                    }
                    if(row == 1 && tempRow == null){
                        throw new SimpleException(errorType,"第二行必须为要插入数据的标题，不能为空！");
                    }else if(row == 1 && tempRow != null){
                        continue;
                    }
                    if(tempRow == null){//每一行都可能为null，因为在xls中可以删除当前行。
                        continue;
                    }
                    EquipmentCustom tempEquipment = new EquipmentCustom();//创建一个容器用来装载数据

                    int cellCount = tempRow.getLastCellNum();//获取列数
                    for (int cell = 0; cell < cellCount; cell++) {
                        XSSFCell tempCell = tempRow.getCell(cell);
                        if(cell == 0 && tempCell != null){
                            throw new SimpleException(errorType,"excel格式错误，excel第一列必须未加入过数据，并且现在为空。解决方式为：新插入列，删除旧列，请修改文件后再上传！");
                        }
                        if(cell == 1){
                            continue;
                        }
                        if(tempCell == null){//当前单元格也可能为null，因为可以在excell中被删除。
                            continue;
                        }
                        int index = cell-2;//获取下标
                        if(index>=eqCellName.length){
                            break;
                        }
                        String fieldName = eqCellName[index];//获取列名
                        String fieldValue = "";
                        if(fieldName.equals("purchasTime")){
                            Date tempDate = null;
                            try {
                                tempDate = tempCell.getDateCellValue();
                            }catch (Exception e){
                                    String time = tempCell.toString();
                                    if(time == null || time.equals("")){
                                        continue;
                                    }else if(StringUtils.isNormalTime(time)){
                                        tempDate = StringUtils.paseNormalTime(time);
                                    }
                            }
                            if(tempDate == null){
                                continue;
                            }
                            fieldValue = StringUtils.getNormalTime(tempDate);
                        } else {
                            fieldValue = tempCell.toString();
                        }
                        SimpleBeanUtils.setTargetFieldValue(tempEquipment,fieldName,fieldValue);//设置属性值
                    }
                    tempEquipment.setX(row);
                    tempEquipment.setY(14);
                    allEquipments.add(tempEquipment);
                }
            }
            wb.close();//关闭流，释放资源

            List<EquipmentCustom> errorEquipments = filterEquipmentDateAndInsert(allEquipments);

            XSSFWorkbook wb2 = new XSSFWorkbook();
            XSSFSheet sheet = wb2.createSheet("设备信息 ");

            XSSFRow rowTitle = sheet.createRow(1);
            int titleCount = eqCellTitleName.length;
            CellStyle cellStyle = wb2.createCellStyle();
            cellStyle.setFillBackgroundColor(IndexedColors.ROYAL_BLUE.getIndex());
            cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);

            for (int title = 0; title < titleCount; title++) {
                sheet.autoSizeColumn(title+1);//设置水平自适应宽度
                XSSFCell cell = rowTitle.createCell(title+1);
                cell.setCellValue(eqCellTitleName[title]);
                cell.setCellStyle(cellStyle);
            }
            if(sheet == null){
                throw new SimpleException(errorType,"excel内容错误，不存在数据表！");
            }
            int len = allEquipments.size();
            boolean hasError = false;
            int index = 0;
            List<EquipmentCustom> insertSuccess = new LinkedList<>();

            for (int i = 0; i < len; i++) {
                EquipmentCustom equipmentCustom = allEquipments.get(i);

                String errorMessage = equipmentCustom.getMessage();
                if(errorMessage != null && !errorMessage.equals("")){
                    hasError = true;
                    if(equipmentCustom.getPurchasTime()!=null&&!equipmentCustom.getPurchasTime().equals("")&&StringUtils.isZhDateFormate(equipmentCustom.getPurchasTime())){
                        equipmentCustom.setPurchasTime(StringUtils.zhDateStrToENDateStr(equipmentCustom.getPurchasTime()));
                    }
                    insertErrorEqToExcel(equipmentCustom,sheet,index+2,index+1);
                    index++;
                }else{
                    insertSuccess.add(equipmentCustom);
                }
            }
            createInsertEqSuccessExcel(insertSuccess);
            //只要插入信息之后，不管插入的设备信息是否有错，我都会删除上一个错误文件。
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            String errorFileNamePro = null;
            try {
                errorFileNamePro = maxValueService.findValueByKey(userName);
            }catch (Exception e){}
            if(errorFileNamePro!=null&&!errorFileNamePro.equals("")&&!errorFileNamePro.equals("1")){
                File file = new File(BASE_PATH+errorFileNamePro);
                file.delete();
            }

            String errorFileName = "1";//同时也会修改数据库中当前用户错误文件为空！
            if(hasError){
                errorFileName = "新设备未插入"+new Date().getTime()+".xlsx";

                OutputStream outputStream = new FileOutputStream(BASE_PATH+errorFileName);
                wb2.write(outputStream);
                outputStream.close();
            }
            MaxValue maxValue = new MaxValue();//将当前的错误excel名称持久化！，可能为空也可能不为空！也就是是否错误！
            maxValue.setKey(userName);
            maxValue.setValue(errorFileName);
            if(errorFileNamePro == null){
                maxValueService.insertMaxValue(maxValue);
            }else{
                maxValueService.updataMaxValue(maxValue);
            }
            wb2.close();
            if(hasError){
                throw new SimpleException("hasInsertError","要插入文件中，由于设备信息不全，少量或大量设备未插入，请下载为插入设备信息，修改后重新上传！");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new SimpleException(errorType,e.getMessage());
        }
    }

    private void createInsertEqSuccessExcel(List<EquipmentCustom> insertSuccess) throws Exception {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("设备添加成功列表");
        XSSFRow rowTitle = sheet.createRow(1);

        int titleCount = eqCellTitleName.length;
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFillBackgroundColor(IndexedColors.ROYAL_BLUE.getIndex());
        cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);

        for (int title = 0; title < titleCount-1; title++) {
            sheet.autoSizeColumn(title+1);//设置水平自适应宽度
            XSSFCell cell = rowTitle.createCell(title+1);
            cell.setCellValue(eqCellTitleName[title]);
            cell.setCellStyle(cellStyle);
        }
        int len = insertSuccess.size();
        boolean bInsertSuccess = false;
        for (int i = 0; i < len; i++) {
            bInsertSuccess = true;

            EquipmentCustom equipmentCustom = insertSuccess.get(i);

            insertErrorEqToExcel(equipmentCustom,sheet,i+2,i+1);
        }
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();//获取当前用户的用户名
        String eqSuccessValue = null;
        try {
            eqSuccessValue = maxValueService.findValueByKey(userName+"-eqsuccess");
        } catch (Exception e) {
            MaxValue value = new MaxValue();
            value.setKey(userName+"-eqsuccess");
            value.setValue("1");
            maxValueService.insertMaxValue(value);
        }
        String eqSuccessFileName = "插入成功设备名单"+new Date().getTime()+".xlsx";
        if(eqSuccessValue != null && !eqSuccessValue.equals("") && !eqSuccessValue.equals("1")){
            File file = new File(BASE_PATH+eqSuccessValue);
            file.delete();
        }
        eqSuccessValue = eqSuccessFileName;
        OutputStream outputStream = new FileOutputStream(BASE_PATH+eqSuccessFileName);
        wb.write(outputStream);
        outputStream.close();
        wb.close();
        MaxValue value = new MaxValue();
        value.setKey(userName+"-eqsuccess");
        value.setValue(eqSuccessValue);
        maxValueService.updataMaxValue(value);

        try {
            maxValueService.findValueByKey(userName+"-eqsuccessState");
        } catch (Exception e) {
            MaxValue valueState = new MaxValue();
            valueState.setKey(userName+"-eqsuccessState");
            valueState.setValue("1");
            maxValueService.insertMaxValue(valueState);
        }
        MaxValue valueState = new MaxValue();
        valueState.setKey(userName+"-eqsuccessState");
        valueState.setValue(bInsertSuccess+"");
        maxValueService.updataMaxValue(valueState);
    }

    private void insertErrorEqToExcel(EquipmentCustom equipmentCustom, XSSFSheet sheet, int curRow, int start) {
        XSSFRow xssfRow = sheet.createRow(curRow);
        xssfRow.createCell(1).setCellValue(start);
        xssfRow.createCell(2).setCellValue(equipmentCustom.getEqType());
        xssfRow.createCell(3).setCellValue(equipmentCustom.getEqName());
        xssfRow.createCell(4).setCellValue(equipmentCustom.getBrandName());
        xssfRow.createCell(5).setCellValue(equipmentCustom.getSupplier());
        xssfRow.createCell(6).setCellValue(equipmentCustom.getBuyCity());
        xssfRow.createCell(7).setCellValue(equipmentCustom.getPurchasDepart());
        xssfRow.createCell(8).setCellValue(equipmentCustom.getCity());
        xssfRow.createCell(9).setCellValue(equipmentCustom.getBelongDepart());
        xssfRow.createCell(10).setCellValue(equipmentCustom.getEqStateId());
        xssfRow.createCell(11).setCellValue(equipmentCustom.getPurchasPrice());
        xssfRow.createCell(12).setCellValue(equipmentCustom.getPurchasTime());
        xssfRow.createCell(13).setCellValue(equipmentCustom.getBuyCount());
        xssfRow.createCell(14).setCellValue(equipmentCustom.getMessage());
    }

    private List<EquipmentCustom> filterEquipmentDateAndInsert(List<EquipmentCustom> allEquipments) throws SimpleException {
        List<EquipmentCustom> errorEquipments = new LinkedList<>();//错误的设备内容

        for (EquipmentCustom allEquipment : allEquipments) {
            String buyCount = allEquipment.getBuyCount();
            if(buyCount == null || buyCount.equals("")){
                allEquipment.setBuyCount("1");
            }
            buyCount = StringUtils.getStrPreNum(buyCount);
            if(buyCount.equals("")){
                buyCount = "1";
            }
            String time = allEquipment.getPurchasTime();
            if(time == null || time.equals("")){
                allEquipment.setMessage("采购时间不能为空！");
                continue;
            }
            allEquipment.setBuyCount(buyCount);
            allEquipment.setPurchasTime(StringUtils.enDateStrToZHDateStr(allEquipment.getPurchasTime()));
            String eqState = allEquipment.getEqStateId();
            String city = allEquipment.getCity();
            String buyCity = allEquipment.getBuyCity();

            if(eqState == null || eqState.equals("")){
                eqState = "闲置";
            }
            String eqStateID = equipmentStateMapper.findStateIDByStateName(eqState);
            if(eqStateID == null){
                allEquipment.setMessage("设备状态错误！");
                continue;
            }

            if(city == null || city.equals("")){
                allEquipment.setMessage("设备归属城市不能为空！");
                continue;
            }

            if(buyCity == null || buyCity.equals("")){
                allEquipment.setMessage("设备采购城市不能为空！");
                continue;
            }
            try {
                insertSingleEquipment(allEquipment);
                allEquipment.setMessage(null);//没有错误信息表示该条数据已经插入数据库。
            }catch (Exception e){
                String errorMeaasge = e.getMessage();
                if(e instanceof SimpleException){
                    errorMeaasge = ((SimpleException)e).getErrorMessage();
                }
                allEquipment.setMessage(errorMeaasge);
            }
        }
        return errorEquipments;
    }

    public File getErrorExcel() throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();//获取当前用户的用户名
        String fileName = maxValueService.findValueByKey(username);
        if(fileName == null || fileName.equals("")){
            throw new SimpleException(errorType,"当前用户没有未导入的设备信息！");
        }
        File file = null;
        try {
            file = new File(BASE_PATH+fileName);
        }catch (Exception e){
            throw new SimpleException(errorType,"系统发生错误：获取错误文件失败，请联系管理员该BUG！");
        }
        return file;
    }

    public boolean hasErrorExcelFile() throws Exception{
        String username = SecurityContextHolder.getContext().getAuthentication().getName();//获取当前用户的用户名
        String fileName = null;
        try {
            fileName = maxValueService.findValueByKey(username);
        }catch (Exception e){}

        if(fileName == null || fileName.equals("") || fileName.equals("1")){
            return false;
        }
        return true;
    }

    public boolean hasEqInsertSuccessExcelFile() throws Exception{
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();//获取当前用户的用户名
        String state = maxValueService.findValueByKey(userName+"-eqsuccessState");
        if(state.equals("true")){
            return true;
        }
        return false;
    }

    public File getEqInsertSuccessFile() throws Exception {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();//获取当前用户的用户名
        String fileName = maxValueService.findValueByKey(userName+"-eqsuccess");
        return new File(BASE_PATH+fileName);
    }
}
