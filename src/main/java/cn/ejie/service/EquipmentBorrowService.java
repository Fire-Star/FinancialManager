package cn.ejie.service;

import cn.ejie.dao.EquipmentBorrowMapper;
import cn.ejie.exception.SimpleException;
import cn.ejie.pocustom.EquipmentBorrowCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EquipmentBorrowService {

    @Autowired
    private EquipmentBorrowMapper equipmentBorrowMapper;

    public List<EquipmentBorrowCustom> findBorrowByEqId(String eqId) throws Exception{
        List<EquipmentBorrowCustom> equipmentBorrowCustomList = new ArrayList<EquipmentBorrowCustom>();
        try {
            equipmentBorrowCustomList = equipmentBorrowMapper.findBorrowByEqId(eqId);
        }catch (Exception e){
            e.printStackTrace();
            new SimpleException("borrowLogErrorType","数据库查询借出记录时出现错误");
        }
        return equipmentBorrowCustomList;
    }

    public int countAllByUser(String userId) throws Exception{
        int borrowNum = 0;
        try {
            borrowNum = equipmentBorrowMapper.countAllByUser(userId);
        }catch (Exception e){
            e.printStackTrace();
            new SimpleException("borrowLogErrorType","数据库查询借出记录时出现错误");
        }
        return borrowNum;
    }
}
