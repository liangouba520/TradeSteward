
package com.yunhe.core.aop.weblog;

import com.yunhe.core.aop.webexception.ExceptionEnum;
import com.yunhe.core.aop.webexception.GlobalException;
import com.yunhe.core.common.annotion.WebLog;
import com.yunhe.core.util.DateUtil;
import com.yunhe.systemsetup.entity.Employ;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

/**
 * <p>
 * AOP日志
 * </p>
 *
 * @author 孔邹祥
 * @date 2019年1月10日
 */

//@Aspect
//@Component
public class WebLogAspect {

    private final static Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    @Resource
    SystemLogMapper systemLogMapper;

    @Pointcut(value = "execution(public * com.yunhe.core.common.login.controller.LoginController.login(..))")
    public void webLogin() {
    }

    @Pointcut(value = "execution(public * com.yunhe.*.controller.*.*(..))")
    public void webLog() {
    }

    /**
     * <p>
     * 方法拦截之前开始打印日志
     * </p>
     *
     * @param joinPoint
     */
    @Before(value = "webLog()")
    public void beBefore(JoinPoint joinPoint) {
        SystemLog log = new SystemLog();

        logger.info("<--**********************日志开始**************************-->");
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;

//        Request请求
        HttpServletRequest request = attributes.getRequest();

//        Session请求
        HttpSession session = request.getSession();

        Employ employ = (Employ) session.getAttribute("employ");

//        获取员工的登录账号
        log.setAccount(employ.getEmUsername());

//         员工姓名
        log.setUsername(employ.getEmRealname());

//        获取对方法的操作
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        //获取操作
        WebLog webLog = method.getAnnotation(WebLog.class);
        if (webLog != null) {
            String value = webLog.value();
            //保存获取的操作
            log.setOperation(value);
        }

        //请求的参数
        Object[] args = joinPoint.getArgs();
        /*//将参数所在的数组转换成json
        String params = J;
        sysLog.setParams(params);*/

//        IP地址
        log.setIp(request.getRemoteAddr());

//        获取操作时间
        log.setCreateDate(DateUtil.curr());


//        URL
        logger.info("URL={}", request.getRequestURL());
//        Method
        logger.info("Method={}", request.getMethod());
//        IP
        logger.info("IP={}", request.getRemoteAddr());
//        类方法
        logger.info("Class_Method={}", joinPoint.getSignature().getDeclaringTypeName() + "." +
                joinPoint.getSignature().getName());
//        参数
        logger.info("args={}", joinPoint.getArgs());
//        HTTP请求方法
        logger.info("HTTP_Method", request.getMethod());
        logger.info("<--**********************日志结束**************************-->");
        if (systemLogMapper.insert(log)>1){
            throw new GlobalException(ExceptionEnum.LOG_ERROR);
        }
        System.out.println(log);
    }


    /**
     * <P>
     * 打印方法返回的结果值
     * </p>
     *
     * @param object
     */
    @AfterReturning(returning = "object", pointcut = "webLog()")
    public void doAfterReturning(Object object) {
        logger.info("response={}", object.toString());
    }
}

