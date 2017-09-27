package cn.ejie.dao;

import cn.ejie.pocustom.EquipmentBorrowCustom;

import java.util.List;

public interface EquipmentBorrowMapper {

    /**
     *
     * @param eqId
     * @return
     * @throws Exception
     */
    public List<EquipmentBorrowCustom> findBorrowByEqId(String eqId)throws Exception;

    /**
     *
     * @return
     * @throws Exception
     */
    public int countAllByUser(String userId) throws Exception;

    /**
     *
     * @param userId
     * @return
     * @throws Exception
     */
    public List<EquipmentBorrowCustom> findAllByUserId(String userId) throws Exception;

    /**
     *
     * @param equipmentBorrowCustom
     * @throws Exception
     */
    public void insertEqBorrowLog(EquipmentBorrowCustom equipmentBorrowCustom) throws Exception;
}
