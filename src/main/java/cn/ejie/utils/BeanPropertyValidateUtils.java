package cn.ejie.utils;

import cn.ejie.pocustom.SupplierCustom;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2017/8/24.
 */
public class BeanPropertyValidateUtils {
    /**
     * 这里面实现的是 对一个 Bean 属性的属性值进行校验，如果 null 或者空字符串，那么久抛出对应 BeanPropertyErrorType 的异常！
     */
    public static void validateIsEmptyProperty(Object target ,Field ...excepts){
        Class clazz = target.getClass();
        Field fields[] = clazz.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getName());
        }
    }
}
