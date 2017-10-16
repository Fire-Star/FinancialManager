package cn.ejie.service;

import cn.ejie.dao.SupplierMapper;
import cn.ejie.exception.SimpleException;
import cn.ejie.exception.SupplierException;
import cn.ejie.po.MaxValue;
import cn.ejie.pocustom.SupplierCustom;
import cn.ejie.utils.BeanPropertyValidateUtils;
import cn.ejie.utils.ExcelUtils;
import cn.ejie.utils.SimpleBeanUtils;
import cn.ejie.utils.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/24.
 */
@Service
public class SupplierService {

    private String errorType = "supplierErrorType";

    @Autowired
    private SupplierMapper supplierMapper;

    @Autowired
    private MaxValueService maxValueService;

    private String [] titleNamePro = {"name","adtitude","address","contactName","tel","business"};
    private String [] titleErrorNamePro = {"name","adtitude","address","contactName","tel","business","errroMessage"};
    private String [] successTitleNameStr = {"序列号","名称","资质","地址","联系人","联系电话","主营业务"};
    private String [] errorTitleNameStr = {"序列号","名称","资质","地址","联系人","联系电话","主营业务","错误信息"};

    public List<SupplierCustom> findAllSupplier() throws Exception{
        List<SupplierCustom> supplierCustoms = null;
        try {
            supplierCustoms = supplierMapper.findAllSupplier();
        }catch (Exception e){
            e.printStackTrace();
            throw new SupplierException(errorType,"数据库发生错误！");
        }
        return supplierCustoms;
    }

    public List<SupplierCustom> findSupplierBySql(String sql) throws Exception{
        List<SupplierCustom> supplierCustoms = null;
        try {
            supplierCustoms = supplierMapper.findSupplierBySql(sql);
        }catch (Exception e){
            e.printStackTrace();
            throw new SupplierException(errorType,"数据库发生错误！");
        }
        return supplierCustoms;
    }

    public SupplierCustom findSupplierById(String id) throws Exception{
        SupplierCustom supplierCustom = new SupplierCustom();
        try {
            supplierCustom = supplierMapper.findSupplierById(id);
        }catch (Exception e){
            e.printStackTrace();
            throw new SupplierException(errorType,"数据库发生错误！");
        }
        return supplierCustom;
    }

    public void addSingleSupplier(SupplierCustom supplierCustom) throws Exception{
        //验证当前对象的属性是否满足 插入到数据库需求，比如字段是否为 null 或者 空字符串！
        BeanPropertyValidateUtils.validateIsEmptyProperty(supplierCustom);

        String supplierName = supplierCustom.getName();
        Integer count = supplierMapper.hasSupplierByName(supplierName);
        if(count>0){
            throw new SupplierException("supplierNameError","当前提供商已经存在！");
        }
        supplierMapper.addSingleSupplier(supplierCustom);

        if(!supplierCustom.isPiDao()){

            String successFileName = "供应商成功导入列表.xlsx";
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();//获取当前用户的用户名

            updataErrorState(userName,"1");
            updataSuccessState(userName,successFileName);

            List<SupplierCustom> supplierCustoms = new LinkedList<>();
            supplierCustoms.add(supplierCustom);

            List<List<String>> successData = ExcelUtils.objectProToStrList(supplierCustoms,titleNamePro);

            ExcelUtils.createExcel(EquipmentService.BASE_PATH,successFileName,successData,"供应商导入成功列表",successTitleNameStr);
        }
    }

    public String findSupIdBySupName(String name) throws Exception{
        String supId = "";
        try {
            supId = supplierMapper.findSupIdBySupName(name);
        }catch (Exception e){
            e.printStackTrace();
        }
        return supId;
    }

    public int countEqNumBySupName(String supName){
        int count = 0;
        try {
            count = supplierMapper.countEqNumBySupName(supName);
        }catch (Exception e){
            e.printStackTrace();
        }
        return count;
    }

    public Double sumTotalMoney(String supName){
        Double total = 0.0;
        try {
            total = supplierMapper.sumTotalMoney(supName);
        }catch (Exception e){
            e.printStackTrace();
        }
        return total;
    }

    public void updateSup(SupplierCustom supplierCustom) throws Exception{
        try {
            supplierMapper.updateSup(supplierCustom);
        }catch (Exception e){
            e.printStackTrace();
        }
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
        //获取文件大小，单位字节
        long site = file.getSize();
        if(site > EquipmentService.MAX_FILE_SISE) {
            //可以对文件大小进行检查
        }
        if(!".xlsx".equals(extension)){
            throw new SimpleException(errorType,"上传文件类型错误，必须为xlsx格式！");
        }
        //构造文件上传后的文件绝对路径，这里取系统时间戳＋文件名作为文件名
        //不推荐这么写，这里只是举例子，这么写会有并发问题
        //应该采用一定的算法生成独一无二的的文件名
        originFileName = originFileName.substring(0,originFileName.lastIndexOf("."));
        String fileSimpleName = originFileName+"-"+String.valueOf(System.currentTimeMillis()) + extension;
        String fileName = EquipmentService.UPLOAD_DIR + fileSimpleName;
        try {
            file.transferTo(new File(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String findValueFileName = null;
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            findValueFileName = maxValueService.findValueByKey(userName+"-SupplierTargetSource");
        } catch (Exception e) {}
        if(findValueFileName == null){
            MaxValue maxValue = new MaxValue();
            maxValue.setKey(userName+"-SupplierTargetSource");
            maxValue.setValue("1");
            maxValueService.insertMaxValue(maxValue);
        }else if(!findValueFileName.equals("")&&!findValueFileName.equals("1")){
            File targetFileSource = new File(EquipmentService.BASE_PATH+findValueFileName);
            targetFileSource.delete();
        }
        //不管是否为 null ，都会更新数据库的上传文件名称，但是不包含路径。
        MaxValue updateParam = new MaxValue();
        updateParam.setKey(userName+"-SupplierTargetSource");
        updateParam.setValue(fileSimpleName);
        maxValueService.updataMaxValue(updateParam);

        insertSuppliers(fileName);
    }

    private void insertSuppliers(String fileName) throws Exception {
        List<SupplierCustom> supplierCustoms = analisTargetFile(fileName);
        //你为什么写个简单的代码都会觉得麻烦？？？？
        //你真的很low！！！
        //no home ， only yourself , stand up！！！

        //在我的脑海里，突然明白了犯贱这个词，但是你的所作所为，为什么只有原谅这个词？？？
        boolean isInsert = false;
        List<SupplierCustom> successInsert = new LinkedList<>();
        List<SupplierCustom> errorInsert = new LinkedList<>();

        boolean hasSuccess = false;
        boolean hasError = false;

        for (SupplierCustom supplierCustom : supplierCustoms) {
            supplierCustom.setPiDao(true);
            try {
                addSingleSupplier(supplierCustom);
                successInsert.add(supplierCustom);
                hasSuccess = true;
            }catch (Exception e){
                hasError = true;
                errorInsert.add(supplierCustom);
                if(e instanceof SimpleException){
                    String errorMessage = ((SimpleException)e).getErrorMessage();
                    supplierCustom.setErrroMessage(errorMessage);
                }
            }
        }

        List<List<String>> successData = ExcelUtils.objectProToStrList(successInsert,titleNamePro);
        List<List<String>> errorData = ExcelUtils.objectProToStrList(errorInsert,titleErrorNamePro);
        String successFileName = "供应商成功导入列表.xlsx";
        String errorFileName = "供应商失败导入列表.xlsx";

        ExcelUtils.createExcel(EquipmentService.BASE_PATH,successFileName,successData,"供应商导入成功列表",successTitleNameStr);
        ExcelUtils.createExcel(EquipmentService.BASE_PATH,errorFileName,errorData,"供应商导入失败列表",errorTitleNameStr);

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();//获取当前用户的用户名

        if(!hasError){
            errorFileName = "1";
        }
        if(!hasSuccess){
            successFileName = "1";
        }
        updataErrorState(userName,errorFileName);
        updataSuccessState(userName,successFileName);

        if(hasError){
            throw new SimpleException(errorType,"上传供应商信息文件中有一些或很多未成功导入，请下载未导入供应商信息，修正后重新导入！");
        }
    }

    public boolean hasSuccessFile() throws Exception {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();//获取当前用户的用户名
        String result = maxValueService.findValueByKey(userName+"-SupplierSuccessFile");
        if(result.equals("1")){
            return false;
        }
        return true;
    }

    public boolean hasErrorFile() throws Exception {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();//获取当前用户的用户名
        String result = maxValueService.findValueByKey(userName+"-SupplierErrorFile");
        if(result.equals("1")){
            return false;
        }
        return true;
    }

    public String getState(String key) throws Exception {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        String result = maxValueService.findValueByKey(userName+key);
        if(result.equals("1")){
            throw new SimpleException(errorType,"没有发现你要下载的文件！");
        }
        return result;
    }

    public void updataSuccessState(String userName,String value) throws Exception {
        maxValueService.updateState(userName+"-SupplierSuccessFile",value);
    }

    public void updataErrorState(String userName,String value) throws Exception {
        maxValueService.updateState(userName+"-SupplierErrorFile",value);
    }

    private List<SupplierCustom> analisTargetFile(String fileName) throws Exception {
        List<SupplierCustom> allStaff = new LinkedList<>();
        XSSFWorkbook wb = null;
        try {
            wb = new XSSFWorkbook(fileName);
        } catch (IOException e) {
            throw new SimpleException(errorType,"你上传的文件格式有误，请重新上传！");
        }
        XSSFSheet sheet = wb.getSheetAt(0);
        if(sheet == null){
            throw new SimpleException(errorType,"excel没有Sheet！");
        }

        Row titleRow = sheet.getRow(1);
        if(titleRow==null){
            throw new SimpleException(errorType,"上传文件内容格式错误：供应商信息不满足要求，第二行必须为标题！");
        }
        ExcelUtils.valatileExcelTitle(titleRow,errorType,successTitleNameStr,"供应商");
        int startIndexRow = 2;
        int lastIndexRow = sheet.getLastRowNum()+1;//通常获取不准确会少一行，所以 +1
        for (int rowCount = startIndexRow; rowCount < lastIndexRow; rowCount++) {
            XSSFRow tempRow = sheet.getRow(rowCount);
            if(tempRow == null){
                continue;
            }
            int lastIndexCell = tempRow.getLastCellNum();
            SupplierCustom tempStaff = new SupplierCustom();//创建单个员工数据容器。
            for (int cellCount = 2; cellCount < lastIndexCell; cellCount++) {
                if(cellCount-2>=titleNamePro.length){
                    break;
                }
                XSSFCell tempCell = tempRow.getCell(cellCount);
                if(tempCell == null){
                    continue;
                }
                String tempValue = tempCell.toString();
                if(titleNamePro.length<=cellCount-2){
                    break;
                }

                if(cellCount == 6){
                    try {
                        tempValue = StringUtils.numberToStr(tempCell);
                    } catch (Exception e) {
                        throw new SimpleException(errorType,"电话号码格式错误！");
                    }
                }
                String fileNamePro = titleNamePro[cellCount-2];
                tempValue = tempValue.trim();
                SimpleBeanUtils.setTargetFieldValue(tempStaff,fileNamePro,tempValue);
            }
            allStaff.add(tempStaff);
        }
        wb.close();
        return allStaff;
    }
}
