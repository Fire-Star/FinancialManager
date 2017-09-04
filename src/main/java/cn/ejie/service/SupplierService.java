package cn.ejie.service;

import cn.ejie.dao.SupplierMapper;
import cn.ejie.exception.SupplierException;
import cn.ejie.pocustom.SupplierCustom;
import cn.ejie.utils.BeanPropertyValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/8/24.
 */
@Service
public class SupplierService {

    private String errorType = "supplierErrorType";

    @Autowired
    private SupplierMapper supplierMapper;

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


}
