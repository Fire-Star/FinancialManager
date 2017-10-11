package cn.ejie.service;

import cn.ejie.dao.SystemLogMapper;
import cn.ejie.pocustom.SystemLogCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemLogService {

    @Autowired
    SystemLogMapper systemLogMapper;

    public void insertSystemLog(SystemLogCustom systemLogCustom) throws Exception{
        try {
            systemLogMapper.insertSystemlog(systemLogCustom);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
