package cn.ejie.dao;

import cn.ejie.pocustom.SupplierCustom;

import java.util.List;

/**
 * Created by Administrator on 2017/8/24.
 */
public interface SupplierMapper {

    /**
     * 查询出所有的提供商
     * @return
     * @throws Exception
     */
    public List<SupplierCustom> findAllSupplier() throws Exception;

    /**
     * 添加单个提供商
     * @param supplierCustom
     * @throws Exception
     */
    public void addSingleSupplier(SupplierCustom supplierCustom) throws Exception;

    public Integer hasSupplierByName(String name) throws Exception;
}
