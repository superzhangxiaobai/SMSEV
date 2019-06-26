package com.xiaobai.smsev.sys.controller;

import com.xiaobai.smsev.sys.entity.UserInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
    @RequestMapping("/")
    public String dft() {
        return "sys/login";
    }
    @RequestMapping("/home")
    public String home() {
        return "sys/home";
    }

    /*当我们访问这个URL的时候，Spring Security会帮我们验证当前用户是否有权限访问该地址。
     *官方推荐的鉴权注解方式，控制权限到请求方法级别。可通过三种方式的注解：
     *注解方式1：@Secured， spring自带的注解方法：securedEnabled = true
     *注解方式2：@PreAuthorize，方法调用前注解：securedEnabled = true
     *注解方式2：@RolesAllowed，非spring框架: jsr250Enabled = true
     *注意1：角色要填全名！
     *注意2：一定要在自定义的WebSecurityConfigurerAdapter中添加注解。@EnableGlobalMethodSecurity(axx=bxx)！axx/bxx见上
     */
    @RequestMapping("/hello")
    //@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public String hello(){
        return "sys/hello";
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login() {
        System.out.println("login.html");
        return "sys/login";
    }
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ModelAndView login(UserInfo user) {
        System.out.println(user);
        ModelAndView mv=new ModelAndView();
        //获取subject
        Subject subject = SecurityUtils.getSubject();
        // 封装用户数据
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getLoginname(), user.getPassword());
        //执行登陆方法，用捕捉异常去判断是否登陆成功
        try {
            subject.login(usernamePasswordToken);
            mv.setViewName("sys/index");
            mv.addObject("code",200);
            mv.addObject("message", "success");
        }
        catch (UnknownAccountException e) {
            mv.addObject("code", 500);
            mv.setViewName("sys/login");
            mv.addObject("message", "用户不存在或密码不正确");
        } catch (IncorrectCredentialsException e) {
            //密码错误
            mv.setViewName("sys/login");
            mv.addObject("code", 500);
            mv.addObject("message", "用户不存在或密码不正确");
        }
        return mv;
    }

    @RequestMapping(value = "/logout")
    public String logout() {
        return "sys/login";
    }

    @RequestMapping(value = "/syserror")
    public String error() {
        return "sys/error";
    }

    @RequestMapping(value = "/admin")
    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String admin() {
        return "sys/admin";
    }
}
