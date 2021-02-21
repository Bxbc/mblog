package me.bxbc.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Author: BI XI
 * Date 2021/2/2
 */

@ControllerAdvice
public class exceptionHandle {
    private final  Logger logger = LoggerFactory.getLogger(this.getClass());

    // 只要属于Exception这个类的异常都能处理
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler (HttpServletRequest request, Exception e) throws Exception {
        logger.error("Request URL: {}, Exception: {}", request.getRequestURI(), e);
        if(AnnotationUtils.findAnnotation(e.getClass(),ResponseStatus.class) != null) {
            throw e;
        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("url", request.getRequestURI());
        mv.addObject("exception", e);
        mv.setViewName("error/error");
        return mv;
    }
}
