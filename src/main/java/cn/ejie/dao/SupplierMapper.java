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
     * 根据sql查询供应商信息
     * @param sql
     * @return
     * @throws Exception
     */
    public List<SupplierCustom> findSupplierBySql(String sql) throws Exception;

    /**
     * 添加单个提供商
     * @param supplierCustom
     * @throws Exception
     */
    public void addSingleSupplier(SupplierCustom supplierCustom) throws Exception;

    /**
     *
     * @param name
     * @return
     * @throws Exception
     */
    public Integer hasSupplierByName(String name) throws Exception;

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    public SupplierCustom findSupplierById(String id) throws Exception;

    /**
     *
     * @param name
     * @return
     * @throws Exception
     */
    public String findSupIdBySupName(String name) throws Exception;

    /**
     *
     * @param supName
     * @return
     * @throws Exception
     */
    public int countEqNumBySupName(String supName) throws Exception;

    /**
     *
     * @param supName
     * @return
     * @throws Exception
     */
    public Double sumTotalMoney(String supName) throws Exception;

    /**
     *
     * @param supplierCustom
     * @throws Exception
     */
    public void updateSup(SupplierCustom supplierCustom) throws Exception;
}
