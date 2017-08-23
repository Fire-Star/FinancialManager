package cn.ejie.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2017/8/22.
 */
public class AllCustomExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView mv = new ModelAndView();
        //处理用户的所有异常，包括登录注册等。
        if(ex instanceof SimpleException){
            SimpleException simpleException = (SimpleException)ex;

            request.setAttribute("errorType",simpleException.getErrorType());
            request.setAttribute("errorMessage",simpleException.getErrorMessage());

            mv.setViewName(simpleException.getView());
            return  mv;
        }
        return null;
    }
}
