package com.lexueoude.ascept;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;


@Aspect
@Component
public class LogAscept {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.lexueoude.web.*.*(..))")
    public void log(){

    }
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String classMethod =
                joinPoint.getSignature().getDeclaringTypeName() + "_" + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);
        logger.info("This is exec before ..........");
        logger.info("在请求处理之前输出日志: request: {}", requestLog);
    }
    @After("log()")
    public void doAfter(){
        logger.info("This is exec after ..........");
    }

    @AfterReturning(returning = "result", pointcut = "log()")
    public void doAfterReturning(Object result){
        logger.info("This requests result is {}", result);
    }

    private class RequestLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
