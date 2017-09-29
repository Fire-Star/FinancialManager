package cn.ejie.service;

import cn.ejie.dao.EquipmentMapper;
import cn.ejie.dao.EquipmentStateMapper;
import cn.ejie.exception.EquipmentException;
import cn.ejie.exception.SimpleException;
import cn.ejie.pocustom.EquipmentCustom;
import cn.ejie.utils.BeanPropertyValidateUtils;
import cn.ejie.utils.SimpleBeanUtils;
import cn.ejie.utils.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/21.
 */
@Service
public class EquipmentService {
    private static final String inserState = "闲置";
    private static final String UPLOAD_DIR = "K:\\文件上传\\";
    private static final long MAX_FILE_SISE = 61440; //为 60 MB

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

    private String errorType = "errorType";

    public void insertSingleEquipment(EquipmentCustom equipmentCustom) throws Exception{
        System.out.println(equipmentCustom);
        System.out.println(equipmentCustom.getBuyCount());
        BeanPropertyValidateUtils.validateIsEmptyProperty(equipmentCustom);//验证Bean属性是否为空！！！
        String countStr = equipmentCustom.getBuyCount();

        int len = countStr.length();
        for(int i=0;i<len;i++){
            if(!Character.isDigit(countStr.charAt(i))){
                throw new SimpleException(errorType,"购买数量只能够为整数！");
            }
        }
        Integer iCount = Integer.parseInt(countStr);

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

        while (counti-->0){
            Integer count = equipmentMapper.countEquipmentByCity(belongCityID)+1;
            String eqOtherIdAfter = StringUtils.fillPreString(count.toString(),'0',4);//计算出设备ID

            equipmentCustom.setEqOtherId(cityOtherID+eqTypeOtherId+eqOtherIdAfter);
            equipmentMapper.insertSingleEquipment(equipmentCustom);
            System.out.println("插入1");
        }
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
        if(site > MAX_FILE_SISE) {
            //可以对文件大小进行检查
        }
        //构造文件上传后的文件绝对路径，这里取系统时间戳＋文件名作为文件名
        //不推荐这么写，这里只是举例子，这么写会有并发问题
        //应该采用一定的算法生成独一无二的的文件名
        String fileName = UPLOAD_DIR + originFileName+"-"+String.valueOf(System.currentTimeMillis()) + extension;
        try {
            file.transferTo(new File(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("fileName--------->"+fileName);
        insertXslEquipment(fileName);
    }

    private String[] eqCellName = {"eqType","eqName","brandName","supplier","buyCity","purchasDepart","city","belongDepart","eqStateId","purchasPrice","purchasTime","buyCount"};
    /**
     * 对于excel的要求，第一行必须是列头
     * @param fileName
     */
    private void insertXslEquipment(String fileName) throws Exception{
        boolean bRow = true,bCell = true;
        try {
            InputStream inputStream = new FileInputStream(fileName);//根据文件绝对路径
            XSSFWorkbook wb = new XSSFWorkbook(inputStream);
            int sheetCount = wb.getNumberOfSheets();//获取xls中的Sheet页数
            for (int sheet = 0; sheet < sheetCount; sheet++) {
                XSSFSheet tempSheet = wb.getSheetAt(sheet);//tempSheet不可能为null
                int rowCount = tempSheet.getLastRowNum();//获取当前Sheet页中包括的行数
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
                        String fieldValue = getCellData(tempCell);
                        int index = cell-2;//获取下标
                        String fieldName = eqCellName[index];//获取列名
                        SimpleBeanUtils.setTargetFieldValue(tempEquipment,fieldName,fieldValue);//设置属性值
                    }
                    System.out.println(tempEquipment);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new SimpleException(errorType,e.getMessage());
        }
    }

    public String getCellData(XSSFCell targetCell){
        int type = targetCell.getCellType();
        String value = "";//如果没有获取到数据就为 空字符串
        switch (type){
            case XSSFCell.CELL_TYPE_NUMERIC: // 0 当该单元格数据为数字的时候
                value = String.valueOf(targetCell.getNumericCellValue());
                break;
            case XSSFCell.CELL_TYPE_STRING: // 1 当该单元格数据为字符串的时候
                value = targetCell.getStringCellValue();
                break;
            case XSSFCell.CELL_TYPE_FORMULA: // 2 当该单元格数据为公式的时候
                value = targetCell.getCellFormula();
                break;
            case XSSFCell.CELL_TYPE_BLANK: // 3 当该单元格数据为空的时候
                break;
            case XSSFCell.CELL_TYPE_BOOLEAN: // 4 当该单元格数据为布尔值的时候
                value = String.valueOf(targetCell.getBooleanCellValue());
                break;
            case XSSFCell.CELL_TYPE_ERROR: // 5 当该单元格数据 ERROR 的时候,(故障)
                value = "ERROR";
                break;
        }

        return value;
    }
}
