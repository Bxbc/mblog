package me.bxbc.aspect;

import jdk.nashorn.internal.ir.RuntimeNode;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * Author: BI XI
 * Date 2021/2/2
 */

@Aspect
@Component
public class logAspects {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* me.bxbc.web.*.*(..))")
    public void log() {}

    @Before("log()")
    public void doBefore(JoinPoint jt) {
        ServletRequestAttributes atr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest req = atr.getRequest();
        String url = req.getRequestURL().toString();
        String ip = req.getRemoteAddr();
        // 获取截取到类方法名字
        String classMethod = jt.getSignature().getDeclaringTypeName() + '.' +jt.getSignature().getName();
        Object[] args = jt.getArgs();
        RequestLogs lgs = new RequestLogs(url,ip,classMethod,args);
        logger.info("Request : {}", lgs);
    }

    @After("log()")
    public void doAfter() {

    }

    @AfterReturning(returning = "result", pointcut = "log()")
    public void getReturn(Object result) {
        logger.info("Result : {}", result);
    }

    private class RequestLogs {
        private String url, ip, classMethod;
        // 记录请求的对象数组
        private Object[] args;

        public RequestLogs(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "requestLogs{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
