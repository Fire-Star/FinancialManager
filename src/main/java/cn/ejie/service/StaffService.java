package cn.ejie.service;

import cn.ejie.dao.DepartmentMapper;
import cn.ejie.dao.StaffMapper;
import cn.ejie.exception.SimpleException;
import cn.ejie.exception.StaffException;
import cn.ejie.po.MaxValue;
import cn.ejie.pocustom.StaffCustom;
import cn.ejie.utils.BeanPropertyValidateUtils;
import cn.ejie.utils.ExcelUtils;
import cn.ejie.utils.SimpleBeanUtils;
import cn.ejie.utils.StringUtils;
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
import java.util.*;

@Service
public class StaffService {
    @Autowired
    private StaffMapper staffMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private CityService cityService;

    @Autowired
    private MaxValueService maxValueService;

    @Autowired
    private DepartmentService departmentService;

    private String errorType="staffError";

    /**
     * 添加员工
     * @param staffCustom
     * @throws Exception
     */
    public void addSingleStaff(StaffCustom staffCustom) throws Exception{
        BeanPropertyValidateUtils.validateIsEmptyProperty(staffCustom);//验证前台传输过来的关键字段属性是否为空。
        String city = staffCustom.getCity();
        String dep = staffCustom.getDep();

        String tel = staffCustom.getTel();
        boolean isPiInsert = staffCustom.isPiInsert();
        if(!isPiInsert){
            if(tel.length()!=11){
                throw new StaffException(errorType,"电话号码必须为11位！");
            }
        }

        String cityID = cityService.findCityIDByCity(staffCustom.getCity());
        staffCustom.setCity(cityID);

        Map<String,String> params = new HashMap<>();
        params.put("city",staffCustom.getCity());
        params.put("dep",staffCustom.getDep());
        String depId = departmentMapper.findDepartmentIDByDepNameAndCity(params);

        if(depId==null){
            throw new StaffException(errorType,"该城市下部门 "+staffCustom.getDep()+" 不存在，请联系管理员维护系统字段！");
        }
        staffCustom.setDep(depId);
        System.out.println(staffCustom);
        Integer staffCount = staffMapper.findStaffCountByDepAndNameAndTel(staffCustom);
        if(staffCount>0){
            throw new StaffException(errorType,"该员工已经存在，你可以通过 员工所属部门、姓名、电话号码 判断该员工是否存在！");
        }

        if(!isPiInsert){
            staffCustom.setEntryTime(StringUtils.zhDateStrToENDateStr(staffCustom.getEntryTime()));
        }

        try {
            staffMapper.insert(staffCustom);
        }catch (Exception e){
            throw new SimpleException(errorType,"数据库发生错误！");
        }
        if(!isPiInsert){
            String [] titleSuccessNameStr = {"序列号","姓名","城市","部门","岗位","联系电话","入职时间"};
            String fileSuccessName = "员工导入成功列表.xlsx";
            List<StaffCustom> insertData = new LinkedList<>();

            staffCustom.setCity(city);
            staffCustom.setDep(dep);

            insertData.add(staffCustom);

            createStaffExcel(insertData,fileSuccessName,"导入成功员工列表",titleSuccessNameStr,false);//创建导入成功excel列表

            String fileErrorName = "1";
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();//获取当前用户的用户名

            changeState(userName+"-staffSuccessExcel",fileSuccessName);
            changeState(userName+"-staffErrorExcel",fileErrorName);
        }
    }

    public List<StaffCustom> findAll() throws Exception{
        List<StaffCustom> staffList = new ArrayList<StaffCustom>();
        try {
            staffList = staffMapper.findAll();
        }catch (Exception e){
            e.printStackTrace();
            throw new SimpleException(errorType,"数据库发生错误！");
        }
        return staffList;
    }

    public List<StaffCustom> findBySql(String sql) throws Exception{
        List<StaffCustom> staffList = new ArrayList<StaffCustom>();
        try {
            staffList = staffMapper.findBySql(sql);
        }catch (Exception e){
            e.printStackTrace();
            throw new SimpleException(errorType,"数据库发生错误！");
        }
        return staffList;
    }

    public StaffCustom findStaffById(String id) throws Exception{
        StaffCustom staffCustom = new StaffCustom();
        try {
            staffCustom = staffMapper.findStaffById(id);
        }catch (Exception e){
            e.printStackTrace();
            throw new SimpleException(errorType,"数据库发生错误！");
        }
        return staffCustom;
    }

    public Integer countStaffByCity(String city) throws Exception{
        if(city==null || city.equals("")){
            throw new SimpleException(errorType,"查询公司员工时，城市不能为空！");
        }
        Integer count = staffMapper.countStaffByCity(city);
        if(count==null){
            throw new SimpleException(errorType,"查询员工时，系统发生故障！");
        }
        return count;
    }

    public Integer countStaffByDepart(String department) throws Exception{
        if(department == null || department.equals("")){
            throw new SimpleException(errorType,"查询公司员工时，部门不能为空！");
        }
        Integer count = staffMapper.countStaffByDepart(department);
        if(count==null){
            throw new SimpleException(errorType,"查询员工时，系统发生故障！");
        }
        return count;
    }

    public void uploadFile(MultipartFile file) throws Exception {
        //获取文件类型
        String contentType = file.getContentType();

        System.out.println("contentType="+contentType);

        if(!contentType.equals("")) {
            //可以对文件类型进行检查
        }
        //获取input域的name属性
        String name = file.getName();
        System.out.println("name="+name);
        //获取文件名，带扩展名
        String originFileName = file.getOriginalFilename();
        System.out.println("originFileName"+originFileName);
        //获取文件扩展名
        String extension = originFileName.substring(originFileName.lastIndexOf("."));
        System.out.println(extension);
        //获取文件大小，单位字节
        long site = file.getSize();
        System.out.println("size="+site);
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
            findValueFileName = maxValueService.findValueByKey(userName+"-StaffTargetSource");
        } catch (Exception e) {}
        if(findValueFileName == null){
            MaxValue maxValue = new MaxValue();
            maxValue.setKey(userName+"-StaffTargetSource");
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

        insertStaff(fileName);
    }
    String [] titleSuccessNameStr = {"序列号","姓名","城市","部门","岗位","联系电话","入职时间"};
    private void insertStaff(String fileAbsPath) throws Exception {
        String fileName = fileAbsPath.substring(fileAbsPath.lastIndexOf("\\")+1);
        System.out.println(fileName);
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        String findValueFileName = null;
        try {
            findValueFileName = maxValueService.findValueByKey(userName+"-staff");
        } catch (Exception e) {}
        if(findValueFileName == null){
            MaxValue maxValue = new MaxValue();
            maxValue.setKey(userName+"-staff");
            maxValue.setValue("1");
            maxValueService.insertMaxValue(maxValue);
        }
        //不管是否为 null ，都会更新数据库的上传文件名称，但是不包含路径。
        MaxValue updateParam = new MaxValue();
        updateParam.setKey(userName+"-staff");
        updateParam.setValue(fileName);
        maxValueService.updataMaxValue(updateParam);

        //删除之前的文件，保留现在的文件！
        if(!findValueFileName.equals("1")){
            File proFile = new File(EquipmentService.BASE_PATH+findValueFileName);
            proFile.delete();
        }
        List<StaffCustom> staffCustomList = analisTargetFile(fileName);

        List<StaffCustom> insertSuccessList = new LinkedList<>();
        List<StaffCustom> insertErrorList = new LinkedList<>();
        boolean hasError = false;
        boolean hasSuccess = false;
        for (StaffCustom staffCustom : staffCustomList) {
            String city = staffCustom.getCity();
            String dep = staffCustom.getDep();

            try {
                insertSingleStaff(staffCustom);
                insertSuccessList.add(staffCustom);
                hasSuccess = true;
            }catch (Exception e){
                hasError = true;
                insertErrorList.add(staffCustom);
                if(e instanceof SimpleException){
                    String errorMessage = ((SimpleException)e).getErrorMessage();
                    staffCustom.setErrorMessage(errorMessage);
                }
            }
            staffCustom.setCity(city);
            staffCustom.setDep(dep);
        }

        String [] titleErrorNameStr = {"序列号","姓名","城市","部门","岗位","联系电话","入职时间","错误信息"};
        String fileSuccessName = "员工导入成功列表.xlsx";
        String fileErrorName = "员工导入失败列表.xlsx";
        createStaffExcel(insertSuccessList,fileSuccessName,"导入成功员工列表",titleSuccessNameStr,false);//创建导入成功excel列表
        createStaffExcel(insertErrorList,fileErrorName,"导入失败员工列表",titleErrorNameStr,true);//创建导入失败excel列表

        if(!hasError){
            fileErrorName = "1";
        }
        if(!hasSuccess){
            fileSuccessName = "1";
        }
        changeState(userName+"-staffSuccessExcel",fileSuccessName);
        changeState(userName+"-staffErrorExcel",fileErrorName);

        if(hasError){
            throw new SimpleException(errorType,"还有一些或者很多员工没有导入，请下载未导入员工信息，更正后重新导入！");
        }
    }

    private void changeState(String key, String fileSuccessName) throws Exception {
        maxValueService.updateState(key,fileSuccessName);
    }

    public String getState(String key) throws Exception {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        String result = maxValueService.findValueByKey(userName+key);
        if(result.equals("1")){
            throw new SimpleException(errorType,"没有发现你要下载的文件！");
        }
        return result;
    }

    public boolean findState(String key) throws Exception {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        String successState = maxValueService.findValueByKey(userName+key);
        if(successState.equals("1")){
            return false;
        }
        return true;
    }

    public boolean hasSuccessFile() throws Exception {
        return findState("-staffSuccessExcel");
    }
    public boolean hasErrorFile() throws Exception{
        return findState("-staffErrorExcel");
    }

    private void createStaffExcel(List<StaffCustom> insertSuccessList,String fileName,String sheetName,String []titleName,boolean hasErrorMessage) throws Exception {
        List<List<String>> data = new LinkedList<>();
        for (StaffCustom staffCustom : insertSuccessList) {
            String name = staffCustom.getName();
            String city = staffCustom.getCity();
            String dep = staffCustom.getDep();
            String position = staffCustom.getPosition();
            String tel = staffCustom.getTel();
            String entryTime = staffCustom.getEntryTime();

            List<String> tempItemData = new LinkedList<>();
            tempItemData.add(name);
            tempItemData.add(city);
            tempItemData.add(dep);
            tempItemData.add(position);
            tempItemData.add(tel);
            tempItemData.add(entryTime);
            if(hasErrorMessage){
                tempItemData.add(staffCustom.getErrorMessage());
            }
            data.add(tempItemData);
        }
        ExcelUtils.createExcel(EquipmentService.BASE_PATH,fileName,data,sheetName,titleName);
    }

    private void insertSingleStaff(StaffCustom staffCustom) throws Exception {
        staffCustom.setPiInsert(true);
        addSingleStaff(staffCustom);
    }

    private String [] titleNamePro = {"name","city","dep","position","tel","entryTime"};

    private List<StaffCustom> analisTargetFile(String fileName) throws Exception {
        List<StaffCustom> allStaff = new LinkedList<>();
        XSSFWorkbook wb = null;
        try {
            wb = new XSSFWorkbook(EquipmentService.BASE_PATH+fileName);
        } catch (IOException e) {
            throw new SimpleException(errorType,"你上传的文件格式有误，请重新上传！");
        }
        XSSFSheet sheet = wb.getSheetAt(0);
        if(sheet == null){
            throw new SimpleException(errorType,"excel没有Sheet！");
        }

        Row titleRow = sheet.getRow(1);
        if(titleRow==null){
            throw new SimpleException(errorType,"员工信息不满足要求，第二行必须为标题！");
        }
        ExcelUtils.valatileExcelTitle(titleRow,errorType,titleSuccessNameStr,"员工");

        int startIndexRow = 2;
        int lastIndexRow = sheet.getLastRowNum()+1;//通常获取不准确会少一行，所以 +1
        String tempPosition = "";
        for (int rowCount = startIndexRow; rowCount < lastIndexRow; rowCount++) {
            XSSFRow tempRow = sheet.getRow(rowCount);
            if(tempRow == null){
                continue;
            }
            int lastIndexCell = tempRow.getLastCellNum();
            StaffCustom tempStaff = new StaffCustom();//创建单个员工数据容器。
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

                if(titleNamePro[cellCount-2].equals("dep") && tempValue != null){
                    if(!tempValue.equals("")){
                        tempPosition = tempValue;
                    }else{
                        tempValue = tempPosition;
                    }
                }
                if(cellCount == 6){
                    try {
                        tempValue = StringUtils.numberToStr(tempCell);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new SimpleException(errorType,"电话号码格式错误！");
                    }
                }

                if(cellCount == 7){
                    Date tempDate = StringUtils.getExcelTime(tempCell);
                    if(tempDate != null){
                        tempValue = StringUtils.getNormalTime(tempDate);
                    }
                }
                String fileNamePro = titleNamePro[cellCount-2];
                SimpleBeanUtils.setTargetFieldValue(tempStaff,fileNamePro,tempValue);
            }
            allStaff.add(tempStaff);
        }
        wb.close();
        return allStaff;
    }

    public void updateStaff(StaffCustom staffCustom) throws Exception{
        try {
            staffMapper.updateByPrimaryKey(staffCustom);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
