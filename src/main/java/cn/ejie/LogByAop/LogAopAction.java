package cn.ejie.LogByAop;

import cn.ejie.annotations.SystemLogAOP;
import cn.ejie.pocustom.*;
import cn.ejie.service.SystemLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

@Component
@Aspect
public class LogAopAction {

    @Autowired
    private SystemLogService systemLogService;

    @Pointcut("execution(* cn.ejie.web.controller.EquipmentController.editEqDetail(..))"
            + "||execution(* cn.ejie.web.controller.StaffController.updateStaffDetail(..))"
            + "||execution(* cn.ejie.web.controller.SupplierController.editSupDetail(..))"
            + "||execution(* cn.ejie.web.controller.UserController.editUser(..))"
            + "||execution(* cn.ejie.web.controller.UserController.insertUser(..))"
            + "||execution(* cn.ejie.web.controller.UserController.delUser(..))"
            + "||execution(* cn.ejie.web.controller.EquipmentBorrowController.addEqBorLog(..))"
            + "||execution(* cn.ejie.web.controller.FixedLogController.addEqFixLog(..))"
            + "||execution(* cn.ejie.web.controller.DepartmentController.addDepartment(..))"
            + "||execution(* cn.ejie.web.controller.DepartmentController.delDepartByDepartmentID(..))"
            + "||execution(* cn.ejie.web.controller.CityController.addCity(..))"
            + "||execution(* cn.ejie.web.controller.CityController.delCity(..))"
            + "||execution(* cn.ejie.web.controller.EquipmentTypeController.addEquipmentType(..))"
            + "||execution(* cn.ejie.web.controller.EquipmentTypeController.delEquipmentType(..))"
            + "||execution(* cn.ejie.web.controller.EquipmentNameController.insertSingleEquipmentName(..))"
            + "||execution(* cn.ejie.web.controller.EquipmentNameController.delEquipmentName(..))"
            + "||execution(* cn.ejie.web.controller.EquipmentController.insertSingleEquipment(..))"
            + "||execution(* cn.ejie.web.controller.EquipmentController.uploadFileAndInsert(..))"
            + "||execution(* cn.ejie.web.controller.StaffController.staffAdd(..))"
            + "||execution(* cn.ejie.web.controller.StaffController.uploadFileAndInsert(..))"
            + "||execution(* cn.ejie.web.controller.SupplierController.addSingleSupplier(..))"
            + "||execution(* cn.ejie.web.controller.SupplierController.uploadFileAndInsert(..))")
    /*@Pointcut("execution(* cn.ejie.service.*.login(..))"
            + "||execution(* cn.ejie.service.*.save*(..))"
            + "||execution(* cn.ejie.service.*.add*(..))"
            + "||execution(* cn.ejie.service.*.insert*(..))"
            + "||execution(* cn.ejie.service.*.del*(..))"
            + "||execution(* cn.ejie.service.*.remove*(..))"
            + "||execution(* cn.ejie.service.*.edit*(..))"
            + "||execution(* cn.ejie.service.*.update*(..))")*/
    //@Pointcut("execution(* cn.ejie.web.controller.UserController.login(..))")
    private void cotrollerAspect(){}
    @Around("cotrollerAspect()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable{
        Object object = null;
        String userName = "";
        String module = "";
        String method = "";
        String userIP = "";
        String date = "";
        String parament = "";
        String commit = "";
        HttpServletRequest request = null;
        // 获得被拦截的方法
        Method preMethod = null;

        System.out.println("系统日志开始");
        SystemLogCustom systemLogCustom = new SystemLogCustom();

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();  //通过spring security获得登录的用户名
        userName = userDetails.getUsername();
        date = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());

        // 拦截的方法参数
        Object[] args = pjp.getArgs();
        // 拦截的实体类，就是当前正在执行的controller
        Object target = pjp.getTarget();
        // 拦截的方法名称。当前正在执行的方法
        String methodName = pjp.getSignature().getName();
        MethodSignature msig = null;
        // 拦截的放参数类型
        Signature sig = pjp.getSignature();
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        msig = (MethodSignature) sig;
        Class[] parameterTypes = msig.getMethod().getParameterTypes();
        try {
            preMethod = target.getClass().getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (SecurityException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        if(null!=preMethod){
            if(preMethod.isAnnotationPresent(SystemLogAOP.class)){
                SystemLogAOP systemLogAOP = preMethod.getAnnotation(SystemLogAOP.class);
                method = systemLogAOP.methods();
                module = systemLogAOP.module();
                try {
                    object = pjp.proceed();//执行该方法
                    commit = "执行成功!";
                }catch (Exception e){
                    commit = "执行失败!";
                }
            }else{
                object = pjp.proceed();//执行该方法
            }
        }else {
            object = pjp.proceed();//执行该方法
        }
        //通过分析aop监听参数分析出request等信息
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof HttpServletRequest) {
                request = (HttpServletRequest) args[i];
            }else if(args[i] instanceof EquipmentCustom){
                EquipmentCustom equipmentCustom = (EquipmentCustom) args[i];
                parament = equipmentCustom.toString();
                System.out.println(1111);
            }/*else if(args[i] instanceof StaffCustom){
                StaffCustom staffCustom = (StaffCustom) args[i];
                parament = staffCustom.toString();
                System.out.println(2222);
            }else if(args[i] instanceof SupplierCustom){
                SupplierCustom supplierCustom = (SupplierCustom) args[i];
                parament = supplierCustom.toString();
                System.out.println(3333);
            }else if(args[i] instanceof EquipmentBorrowCustom){
                EquipmentBorrowCustom equipmentBorrowCustom = (EquipmentBorrowCustom) args[i];
                parament = equipmentBorrowCustom.toString();
                System.out.println(4444);
            }else if(args[i] instanceof FixedLogCustom){
                FixedLogCustom fixedLogCustom = (FixedLogCustom) args[i];
                parament = fixedLogCustom.toString();
                System.out.println(5555);
            }*/
        }
        if (request != null) {
            userIP = request.getHeader( " x-forwarded-for " );
            if (userIP  ==   null   ||  userIP.length()  ==   0   ||   " unknown " .equalsIgnoreCase(userIP))  {
                userIP  =  request.getHeader( " Proxy-Client-IP " );
            }
            if (userIP  ==   null   ||  userIP.length()  ==   0   ||   " unknown " .equalsIgnoreCase(userIP))  {
                userIP  =  request.getHeader( " WL-Proxy-Client-IP " );
            }
            if (userIP  ==   null   ||  userIP.length()  ==   0   ||   " unknown " .equalsIgnoreCase(userIP))  {
                userIP  =  request.getRemoteAddr();
            }
            Enumeration enu=request.getParameterNames();
            while(enu.hasMoreElements()){
                String paraName=(String)enu.nextElement();
                parament = parament + paraName + ":" + request.getParameter(paraName) + ";";
            }

        }
        systemLogCustom.setUserID(userName);
        systemLogCustom.setModule(module);
        systemLogCustom.setMethod(method);
        systemLogCustom.setUserIP(userIP);
        systemLogCustom.setParament(parament);
        systemLogCustom.setDate(date);
        systemLogCustom.setCommit(commit);
        systemLogService.insertSystemLog(systemLogCustom);
        System.out.println("userName:"+userName+" module:"+module+" method:"+method+" userIP:"+userIP+" date:"+date+
                " parament:"+ parament +" commit:"+commit);
        System.out.println("系统日志结束");
        return object;
    }
    /*@After("cotrollerAspect()")
    public void after(JoinPoint joinPoint){
        System.out.println("系统日志");
    }*/
}
