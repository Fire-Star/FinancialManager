package cn.ejie.dao;

import cn.ejie.pocustom.EquipmentBorrowCustom;

import java.util.List;

public interface EquipmentBorrowMapper {

    public List<EquipmentBorrowCustom> findBorrowByEqId(String eqId)throws Exception;
}
