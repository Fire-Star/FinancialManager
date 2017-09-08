package cn.ejie.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/22.
 */
public class AllCustomExceptionResolver implements HandlerExceptionResolver {

    private String defaultErrorPage;//默认的错误页面

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        ModelAndView mv = new ModelAndView();
        String view = null;

        String errorType = "defaultError";
        String errorMessage = "发生了意想不到的错误，请通知管理人员！";

        //处理用户的所有异常，包括登录注册等。
        if(ex instanceof SimpleException){
            SimpleException simpleException = (SimpleException)ex;
            errorType = simpleException.getErrorType();
            errorMessage = simpleException.getErrorMessage();

            view = simpleException.getView();
        } else {
            ex.printStackTrace();
        }
        request.setAttribute("errorType",errorType);
        request.setAttribute("errorMessage",errorMessage);

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
