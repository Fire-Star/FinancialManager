package cn.ejie.service;

import cn.ejie.dao.FixedLogMapper;
import cn.ejie.exception.FixedLogException;
import cn.ejie.exception.SimpleException;
import cn.ejie.pocustom.FixedLogCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FixedLogService {

    @Autowired
    private FixedLogMapper fixedLogMapper;

    public List<FixedLogCustom> findAll(){
        List<FixedLogCustom> logCustomList = new ArrayList<FixedLogCustom>();
        try {
            logCustomList = fixedLogMapper.findAll();
        }catch (Exception e){
            e.printStackTrace();
            new FixedLogException("fixedLogErrorType","数据库发生错误！");
        }
        return logCustomList;
    }

    public List<FixedLogCustom> findBuSql(String sql){
        List<FixedLogCustom> fixedLogCustomList = new ArrayList<FixedLogCustom>();
        try {
            fixedLogCustomList = fixedLogMapper.findBySql(sql);
        }catch (Exception e){
            e.printStackTrace();
            new FixedLogException("fixedLogErrorType","数据库发生错误！");
        }
        return fixedLogCustomList;
    }

    public int countByEqId(String eqId) throws Exception{
        int num = 0;
        try {
            num = fixedLogMapper.countByEqId(eqId);
        }catch (Exception e){
            e.printStackTrace();
            new FixedLogException("fixedLogErrorType","数据库发生错误！");
        }
        return num;
    }

    public List<FixedLogCustom> findAllByEqId(String eqId){
        List<FixedLogCustom> fixedLogCustomList = new ArrayList<FixedLogCustom>();
        try {
            fixedLogCustomList = fixedLogMapper.findAllByEqId(eqId);
        }catch (Exception e){
            e.printStackTrace();
            new FixedLogException("fixedLogErrorType","数据库发生错误！");
        }
        return fixedLogCustomList;
    }

    public void insertFixlog(FixedLogCustom fixedLogCustom){
        try {
            fixedLogMapper.insertFixlog(fixedLogCustom);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
