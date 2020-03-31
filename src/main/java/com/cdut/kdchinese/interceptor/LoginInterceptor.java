package com.cdut.kdchinese.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * User: iRappelt
 * Date: 2020/03/21 10:40
 * Description:
 * Version: V1.0
 */
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 用户请求调用前执行
     * @param o
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //得到请求uri
//        System.out.println("//////////////////////////////进入preHandle////////////////");
        String uri = httpServletRequest.getRequestURI();
//        System.out.println(uri);
        //得到session对象，判断user属性是否存在
        HttpSession session = httpServletRequest.getSession();
        Object user = session.getAttribute("user");
        //放行条件
        if(uri.endsWith("chklogin")||uri.endsWith("login.html")||uri.endsWith("chkregister")||uri.endsWith("register.html")){
            //放行
            return true;
        } else if(user==null){
            //重定向到登录页面
            httpServletResponse.sendRedirect("/kdchinese/login.html");
            return false;
        } else {
            //放行
            return true;
        }

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
