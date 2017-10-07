package cn.ejie.service;

import cn.ejie.dao.SupplierMapper;
import cn.ejie.exception.SimpleException;
import cn.ejie.exception.SupplierException;
import cn.ejie.po.MaxValue;
import cn.ejie.pocustom.SupplierCustom;
import cn.ejie.utils.BeanPropertyValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
        String fileName = EquipmentService.UPLOAD_DIR + originFileName+"-"+String.valueOf(System.currentTimeMillis()) + extension;
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
    }

}
