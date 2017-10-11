package cn.ejie.annotations;
import java.lang.annotation.*;

@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLogAOP {
    String module() default "";
    String methods() default "";
}
