package cn.org.xinke.annotation;

import cn.org.xinke.entity.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Aspect
@Component
public class LoginAspect {

    @Pointcut("execution(public * cn.org.xinke.controller.*.*(..)) && @annotation(cn.org.xinke.annotation.Login)")
    public void pointcut(){}

    @Around("pointcut()")
    public Object Interceptor(ProceedingJoinPoint pjp) throws Throwable {
        // 获取请求/响应对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        HttpSession session = request.getSession();
//        session.setAttribute("targetPage",request.getRequestURI());
        // 登录认证
        User loginUser = (User) request.getSession().getAttribute( "LOGIN_USER" );
        if (loginUser == null) {
            response.sendRedirect( "/login.html" );
        }
        try {
//            session.
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw throwable;
        }
    }
}
