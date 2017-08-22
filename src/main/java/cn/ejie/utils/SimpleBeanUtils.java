package cn.ejie.utils;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/22.
 */
public class SimpleBeanUtils {
    public static <T> T setMapPropertyToBean(Class<T> target, Map mapData){
        T targetObject = null;
        try {
            targetObject = (T) target.newInstance();
            BeanUtils.populate(targetObject,mapData);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return targetObject;
    }
}
