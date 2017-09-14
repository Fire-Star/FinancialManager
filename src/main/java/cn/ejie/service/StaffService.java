package cn.ejie.service;

import cn.ejie.dao.CityMapper;
import cn.ejie.dao.DepartmentMapper;
import cn.ejie.dao.StaffMapper;
import cn.ejie.exception.SimpleException;
import cn.ejie.exception.StaffException;
import cn.ejie.pocustom.StaffCustom;
import cn.ejie.utils.BeanPropertyValidateUtils;
import cn.ejie.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StaffService {
    @Autowired
    private StaffMapper staffMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private CityService cityService;

    private String errorType="staffError";

    /**
     * 添加员工
     * @param staffCustom
     * @throws Exception
     */
    public void addSingleStaff(StaffCustom staffCustom) throws Exception{
        BeanPropertyValidateUtils.validateIsEmptyProperty(staffCustom);//验证前台传输过来的关键字段属性是否为空。
        String tel = staffCustom.getTel();
        if(tel.length()!=11){
            throw new StaffException(errorType,"电话号码必须为11位！");
        }
        System.out.println(staffCustom);

        String cityID = cityService.findCityIDByCity(staffCustom.getCity());
        staffCustom.setCity(cityID);

        Map<String,String> params = new HashMap<>();
        params.put("city",staffCustom.getCity());
        params.put("dep",staffCustom.getDep());
        String depId = departmentMapper.findDepartmentIDByDepNameAndCity(params);

        if(depId==null){
            throw new StaffException(errorType,staffCustom.getDep()+" 部门不存在！");
        }
        staffCustom.setDep(depId);
        System.out.println(staffCustom);
        Integer staffCount = staffMapper.findStaffCountByDepAndNameAndTel(staffCustom);
        if(staffCount>0){
            throw new StaffException(errorType,"该员工已经存在，你可以通过 员工所属部门、姓名、电话号码 判断该员工是否存在！");
        }

        String cityId = departmentMapper.findCityIdByDepartmentId(depId);
        staffCustom.setCity(cityId);

        staffCustom.setEntryTime(StringUtils.zhDateStrToENDateStr(staffCustom.getEntryTime()));
        try {
            System.out.println(staffCustom);
            staffMapper.insert(staffCustom);
        }catch (Exception e){
            throw new SimpleException(errorType,"数据库发生错误！");
        }

    }

    public List<StaffCustom> findAll() throws Exception{
        List<StaffCustom> staffList = new ArrayList<StaffCustom>();
        try {
            staffList = staffMapper.findAll();
        }catch (Exception e){
            e.printStackTrace();
            throw new SimpleException(errorType,"数据库发生错误！");
        }
        return staffList;
    }

    public List<StaffCustom> findBySql(String sql) throws Exception{
        List<StaffCustom> staffList = new ArrayList<StaffCustom>();
        try {
            staffList = staffMapper.findBySql(sql);
        }catch (Exception e){
            e.printStackTrace();
            throw new SimpleException(errorType,"数据库发生错误！");
        }
        return staffList;
    }

    public StaffCustom findStaffById(String id) throws Exception{
        StaffCustom staffCustom = new StaffCustom();
        try {
            staffCustom = staffMapper.findStaffById(id);
        }catch (Exception e){
            e.printStackTrace();
            throw new SimpleException(errorType,"数据库发生错误！");
        }
        return staffCustom;
    }
}
