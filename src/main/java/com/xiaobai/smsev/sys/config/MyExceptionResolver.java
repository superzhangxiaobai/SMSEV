package com.xiaobai.smsev.sys.config;


import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义异常，用于捕捉shiro 注解配置方式无法捕捉到异常
 */
public class MyExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest,
                                         HttpServletResponse httpServletResponse, Object o, Exception e) {
        ModelAndView mv = new ModelAndView("redirect:/login");
        if(e instanceof UnauthenticatedException) {
            System.out.println("<--------- 自定义捕捉 未登录异常 ------------->");
            return mv;
        }
        if(e instanceof UnauthorizedException) {
            System.out.println("<--------- 自定义捕捉 权限不足 ------------->");
            return mv;
        }
        e.printStackTrace();
        mv.addObject("exception",e.toString().replaceAll("\n", "<br/>"));
        return mv;
    }
}
