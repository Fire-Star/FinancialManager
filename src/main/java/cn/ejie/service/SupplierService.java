package cn.ejie.service;

import cn.ejie.annotations.BeanPropertyErrorType;
import cn.ejie.dao.SupplierMapper;
import cn.ejie.exception.SimpleException;
import cn.ejie.exception.SupplierException;
import cn.ejie.pocustom.SupplierCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by Administrator on 2017/8/24.
 */
@Service
public class SupplierService {

    private String errorType = "supplierErrorType";

    @Autowired
    private SupplierMapper supplierMapper;

    @RequestMapping("/supplier/showAll")
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

    }
}
