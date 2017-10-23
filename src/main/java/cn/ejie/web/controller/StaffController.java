package cn.ejie.web.controller;

import cn.ejie.annotations.SystemLogAOP;
import cn.ejie.exception.SimpleException;
import cn.ejie.pocustom.StaffCustom;
import cn.ejie.pocustom.UserCustom;
import cn.ejie.service.*;

import cn.ejie.utils.DownloadUtils;
import cn.ejie.utils.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONArray;

import cn.ejie.utils.SimpleBeanUtils;

import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.javassist.bytecode.stackmap.BasicBlock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class StaffController {

    @Autowired
    private ObjectMapper objectMapper;
    private String errorType="staffError";

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private CityService cityService;

    @Autowired
    private EquipmentBorrowService equipmentBorrowService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @RequestMapping("/staff/add")
    @SystemLogAOP(module = "员工添加",methods = "员工信息添加")
    public void staffAdd(HttpServletRequest request, HttpServletResponse response) throws Exception{
        StaffCustom staffCustom = SimpleBeanUtils.setMapPropertyToBean(StaffCustom.class,request.getParameterMap());
        staffService.addSingleStaff(staffCustom);
        SimpleException.sendSuccessMessage(response,objectMapper);
    }

    @RequestMapping("/staff/addPage")
    public String staffAddPage(){
        return "/WEB-INF/pages/staff/staffAdd.jsp";
    }

    @RequestMapping("/user/staff/query")
    public String search(){
        return "/WEB-INF/pages/staff/staffquery.html";
    }

    @RequestMapping("/user/staff/search")
    public void queryStaff(HttpServletRequest request, HttpServletResponse response) throws Exception{
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();  //通过spring security获得登录的用户名
        UserCustom userCustom = new UserCustom();
        try {
            userCustom = userService.findUserByName(userDetails.getUsername());
        }catch (Exception e){
            e.printStackTrace();
        }
        String role = userRoleService.findRoleByUserName(userCustom.getUsername());
        String city = "";
        try {
            city = userService.findCityIdByUserName(userCustom.getUsername());
        }catch (Exception e){
            e.printStackTrace();
            city = "";
        }
        String sql = "";
        String name = "";
        String dep = "";
        String position = "";
        String tel = "";
        String entrytime = "";
        String belongCity = "";
        String depName = "";
        String sqltemp = "SELECT id as staffId,name,department as dep,position,tel,entry_time as entryTime,custom_message as customMessages,city FROM staff";
        if(request.getParameter("name")!= null){
            name = request.getParameter("name");
        }
        if(request.getParameter("department")!= null){
            dep = request.getParameter("department");
        }
        if(request.getParameter("city")!=null){
            belongCity = request.getParameter("city");
        }
        if(!"".equals(dep)&&!"".equals(belongCity)){
            try {
                depName = departmentService.findDepartIDByCityStrAndDepartStr(belongCity,dep);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(request.getParameter("position")!= null){
            position = request.getParameter("position");
        }
        if(request.getParameter("tel")!= null){
            tel = request.getParameter("tel");
        }
        if(request.getParameter("entrytime")!= null&&!"".equals(request.getParameter("entrytime"))){
            entrytime = StringUtils.zhDateStrToENDateStr(request.getParameter("entrytime"));
        }
        if(!name.equals("")||!depName.equals("")||!position.equals("")||!tel.equals("")||!entrytime.equals("")
                ||!"ROLE_ADMIN".equals(role)){
            if("ROLE_ADMIN".equals(role)||"".equals(city)) {
                sqltemp = sqltemp + " WHERE";
            }else{
                sqltemp = sqltemp + " WHERE city='"+  city +"'";
            }
            if(!name.equals("")){
                sqltemp = sqltemp + " and name like '%"+name+"%'";
            }
            if(!depName.equals("")){
                sqltemp = sqltemp + " and department='"+depName+"'";
            }
            if(!position.equals("")){
                sqltemp = sqltemp + " and position like'%"+position+"%'";
            }
            if(!tel.equals("")){
                sqltemp = sqltemp + " and tel like'%"+tel+"%'";
            }
            if(!entrytime.equals("")){
                sqltemp = sqltemp + " and entry_time='"+entrytime+"'";
            }
            if(sqltemp.contains("WHERE and")){
                sql = sqltemp.replaceAll("WHERE and","WHERE");
            }else{
                sql = sqltemp;
            }
        }else{
            sql = sqltemp;
        }

        List<StaffCustom> staffCustomList = new ArrayList<StaffCustom>();
        try {
            staffCustomList = staffService.findBySql(sql);
        }catch (Exception e){
            e.printStackTrace();
            throw new SimpleException(errorType,"数据库发生错误！");
        }
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < staffCustomList.size(); i++) {
            Map<String,String> map = new HashMap<String,String>();
            map.put("index",i+"");
            map.put("id",staffCustomList.get(i).getStaffId());
            map.put("name",staffCustomList.get(i).getName());

            String cityName = "";
            try {
                cityName = cityService.findCityNameByCityID(staffCustomList.get(i).getCity().toString());
            }catch (Exception e){
                e.printStackTrace();
            }
            map.put("city",cityName);
            String depart = "";
            try {
                depart = departmentService.findDepartNameByDepId(staffCustomList.get(i).getDep().toString());
            }catch (Exception e){
                e.printStackTrace();
            }
            map.put("department",depart);
            map.put("position",staffCustomList.get(i).getPosition());
            map.put("tel",staffCustomList.get(i).getTel());
            map.put("entrytime",staffCustomList.get(i).getEntryTime());
            String borrowNum = "";
            try {
                borrowNum = String.valueOf(equipmentBorrowService.countAllByUser(staffCustomList.get(i).getStaffId()));
            }catch (Exception e){
                e.printStackTrace();
            }
            map.put("equipnum",borrowNum);
            list.add(map);
        }
        JSONArray jsonArray = new JSONArray();
        jsonArray = JSONArray.fromObject(list);
        SimpleException.sendMessage(response,jsonArray.toString(),objectMapper);
    }
    @RequestMapping("/user/staff/detail")
    public String searchStaffDetail(){
        return "/WEB-INF/pages/staff/staffdetail.jsp";
    }
    @RequestMapping("/user/staff/findStaffByStaffID")
    public void findStaffByStaffID (HttpServletRequest request,HttpServletResponse response) throws Exception{
        String staffId = "";
        StaffCustom staffCustom = new StaffCustom();
        if(request.getParameter("staffId")!=null){
            staffId = request.getParameter("staffId");
        }
        try {
            staffCustom = staffService.findStaffById(staffId);
        }catch (Exception e){
            e.printStackTrace();
            throw new SimpleException(errorType,"数据库发生错误！");
        }

        String department = "";
        try {
            department = departmentService.findDepartNameByDepId(staffCustom.getDep());
        }catch (Exception e){
            e.printStackTrace();
        }
        staffCustom.setDep(department);
        String city = "";
        try {
            city = cityService.findCityNameByCityID(staffCustom.getCity());
        }catch (Exception e){
            e.printStackTrace();
        }
        staffCustom.setCity(city);
        String customMessage = "";
        String messageObject = "";
        if(!"".equals(staffCustom.getCustomMessages())&&null!=staffCustom.getCustomMessages()){
            customMessage = staffCustom.getCustomMessages();
            String[] message = customMessage.split(";");
            String bufferMessage = "";
            for (int i = 0; i < message.length; i++) {
                String[] detail = message[i].split("=");
                bufferMessage = bufferMessage + detail[0] + ":'" + detail[1] + "',";
            }
            messageObject = "{" + bufferMessage.substring(0,bufferMessage.length()-1) + "}";
            JSONObject jsonMessage = new JSONObject();
            jsonMessage = JSONObject.fromObject(messageObject);
            staffCustom.setCustomMessages(jsonMessage.toString());
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject = JSONObject.fromObject(staffCustom);
        SimpleException.sendMessage(response,jsonObject.toString(),objectMapper);
    }
    @RequestMapping("/staff/findStaffByCity")
    public @ResponseBody List<StaffCustom> findDepartmentByCity(HttpServletRequest request) throws Exception{
        String city = "";
        String department = "";
        String departmentId = "";
        List<StaffCustom> list = new ArrayList<StaffCustom>();
        city = request.getParameter("city");
        department = request.getParameter("department");
        try {
            departmentId = departmentService.findDepartIDByCityStrAndDepartStr(city,department);
        }catch (Exception e){
            e.printStackTrace();
            throw new SimpleException(errorType,"查询部门ID时数据库错误!");
        }
        if("".equals(departmentId)||departmentId == null){
            return list;
        }else{
            String sql = "SELECT id as staffId,name,department as dep,position,tel,entry_time as entryTime," +
                    "custom_message as customMessages,city FROM staff WHERE department='" + departmentId + "'";
            try {
                list = staffService.findBySql(sql);
            }catch (Exception e){
                e.printStackTrace();
                throw new SimpleException(errorType,"查询员工时数据库错误!");
            }
        }
        return list;
    }

    @RequestMapping("/staff/upload")
    @SystemLogAOP(module = "员工添加",methods = "批量添加员工信息")
    public void uploadFileAndInsert(@RequestParam("eqXsl") MultipartFile file, HttpServletResponse response) throws Exception{
        if(!file.isEmpty()) {
            System.out.println("file:"+file.getName());
            staffService.uploadFile(file);
            SimpleException.sendSuccessMessage(response,objectMapper);
        }
    }

    @RequestMapping("/staff/hasSuccessFile")
    public void hasSuccessFile(HttpServletResponse response) throws Exception {
        boolean hasSuccessFile = staffService.hasSuccessFile();
        SimpleException.sendMessage(response,objectMapper,"state",hasSuccessFile+"");
    }

    @RequestMapping("/staff/downloadSuccessFile")
    public ResponseEntity<byte[]> sendSuccessExcel() throws Exception{

        String fileName = staffService.getState("-staffSuccessExcel");
        File file = new File(EquipmentService.BASE_PATH+fileName);

        return DownloadUtils.getResponseEntity(fileName,file);
    }

    @RequestMapping("/staff/hasErrorFile")
    public void hasErrorFile(HttpServletResponse response) throws Exception {
        boolean hasErrorFile = staffService.hasErrorFile();
        SimpleException.sendMessage(response,objectMapper,"state",hasErrorFile+"");
    }

    @RequestMapping("/staff/downloadErrorFile")
    public ResponseEntity<byte[]> sendErrorExcel() throws Exception{

        String fileName = staffService.getState("-staffErrorExcel");
        File file = new File(EquipmentService.BASE_PATH+fileName);

        return DownloadUtils.getResponseEntity(fileName,file);
    }

    @RequestMapping("/user/staffDetail/editStaffDetail")
    @SystemLogAOP(module = "员工查询",methods = "更新员工详细信息")
    public void updateStaffDetail(HttpServletRequest request,HttpServletResponse response){
        String staffID = "";
        String city = "";
        String depart = "";
        String position = "";
        String tel = "";
        String date = "";
        String customMessage = "";
        if( null != request.getParameter("staffID")){
            staffID = request.getParameter("staffID");
        }
        if(null != request.getParameter("city")){
            city = request.getParameter("city");
        }
        if(null != request.getParameter("depart")){
            depart = request.getParameter("depart");
        }
        if(null != request.getParameter("position")){
            position = request.getParameter("position");
        }
        if(null != request.getParameter("tel")){
            tel = request.getParameter("tel");
        }
        if(request.getParameter("date").contains("年")){
            date = StringUtils.zhDateStrToENDateStr(request.getParameter("date"));
        }else{
            date = request.getParameter("date");
        }
        if(!"".equals(city)){
            try{
                depart = departmentService.findDepartIDByCityStrAndDepartStr(city,depart);
                city = cityService.findCityIDByCity(city);
            }catch (Exception e){
                e.printStackTrace();
                String message = "数据库发生错误，修改信息失败！";
                SimpleException.sendMessage(response,message,objectMapper);//报告错误信息到前台！
                return;
            }
        }
        if(!"".equals(request.getParameter("customMessage"))&&null!=request.getParameter("customMessage")){
            customMessage = request.getParameter("customMessage");
        }
        StaffCustom staffCustom = new StaffCustom();
        try {
            staffCustom = staffService.findStaffById(staffID);
            staffCustom.setCity(city);
            staffCustom.setDep(depart);
            staffCustom.setPosition(position);
            staffCustom.setTel(tel);
            staffCustom.setEntryTime(date);
            staffCustom.setCustomMessages(customMessage);
            staffService.updateStaff(staffCustom);
        }catch (Exception e){
            e.printStackTrace();
            String message = "数据库发生错误，修改信息失败！";
            SimpleException.sendMessage(response,message,objectMapper);//报告错误信息到前台！
            return;
        }
        SimpleException.sendSuccessMessage(response,objectMapper);
    }
}
