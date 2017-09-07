package cn.ejie.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2017/8/22.
 */
public class AllCustomExceptionResolver implements HandlerExceptionResolver {

    private String defaultErrorPage;//默认的错误页面

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        ModelAndView mv = new ModelAndView();
        String view = null;
        //处理用户的所有异常，包括登录注册等。
        if(ex instanceof SimpleException){
            SimpleException simpleException = (SimpleException)ex;

            request.setAttribute("errorType",simpleException.getErrorType());
            request.setAttribute("errorMessage",simpleException.getErrorMessage());
            // TODO: 2017/9/7 这里还要写异常页面发送处理

            return  null;
        }

        if(view == null){
            view = defaultErrorPage;
        }
        mv.setViewName(view);

        return mv;
    }

    public String getDefaultErrorPage() {
        return defaultErrorPage;
    }

    public void setDefaultErrorPage(String defaultErrorPage) {
        this.defaultErrorPage = defaultErrorPage;
    }
}
