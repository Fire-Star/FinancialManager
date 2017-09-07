package cn.ejie.service;

import cn.ejie.dao.DepartmentMapper;
import cn.ejie.dao.StaffMapper;
import cn.ejie.exception.SimpleException;
import cn.ejie.exception.StaffException;
import cn.ejie.pocustom.StaffCustom;
import cn.ejie.utils.BeanPropertyValidateUtils;
import cn.ejie.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffService {
    @Autowired
    private StaffMapper staffMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    public void staffAddSingle(StaffCustom staffCustom) throws Exception{
        BeanPropertyValidateUtils.validateIsEmptyProperty(staffCustom);//验证前台传输过来的关键字段属性是否为空。
        String tel = staffCustom.getTel();
        if(tel.length()!=11){
            throw new StaffException("staffTelError","电话号码必须为11位！");
        }
        String depId = departmentMapper.findDepartmentIDByDepName(staffCustom.getDep());
        if(depId==null){
            throw new StaffException("staffTelError",staffCustom.getDep()+" 部门不错在！");
        }

        Integer staffCount = staffMapper.findStaffCountByDepAndNameAndTel(staffCustom.getTel(),staffCustom.getName(),staffCustom.getDep());
        if(staffCount>0){
            throw new StaffException("staffTelError","该员工已经存在，你可以通过 员工所属部门、姓名、电话号码 判断该员工是否存在！");
        }
        staffCustom.setEntryTime(StringUtils.zhDateStrToENDateStr(staffCustom.getEntryTime()));
        try {
            staffMapper.insert(staffCustom);
        }catch (Exception e){
            throw new SimpleException("staffDateBaseError","数据库发生错误！");
        }

    }
}
