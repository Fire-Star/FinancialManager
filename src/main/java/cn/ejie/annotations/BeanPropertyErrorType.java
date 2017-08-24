package cn.ejie.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2017/8/24.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BeanPropertyErrorType {
    /**
     * 该注解类是对POJO的各项属性在Service层中，
     * 如果哪儿一个属性不满足或者出错，那么我就通过该注解
     * 的value属性，向页面输出内容，这里面的value属性值保存的是错误类型。
     */
    String value();//出错类型
    String propertyName();//属性名称，当出错时，我们将会提示该 属性值 的属性名称不能为空！
}
