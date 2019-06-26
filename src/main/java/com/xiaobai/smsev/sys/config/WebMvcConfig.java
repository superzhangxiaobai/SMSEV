package com.xiaobai.smsev.sys.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebMvcConfig implements WebMvcConfigurer {

    //直接页面跳转，不经过Controller，这样在没有任何处理业务的时候,快捷的页面转向定义会节省好多代码
    @Override
    public void addViewControllers(ViewControllerRegistry registry)
    {
        registry.addViewController("/login").setViewName("system/login");
        registry.addViewController("/").setViewName("system/login");
        registry.addViewController("/syserror").setViewName("system/error");
    }
}
