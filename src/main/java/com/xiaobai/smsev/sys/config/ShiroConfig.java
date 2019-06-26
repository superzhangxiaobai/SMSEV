package com.xiaobai.smsev.sys.config;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.HashMap;
import java.util.Map;

/**
 * shiro配置类
 */
@Configuration
public class ShiroConfig {

    //将自己的验证方式加入容器
    @Bean(name = "userRealm")
    public UserRealm userRealm() {
        UserRealm userRealm= new UserRealm();
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher());//自定义加密算法
        return userRealm;
    }
    //权限管理，配置主要是Realm的管理认证
    @Bean(name = "securityManager ")
    public SecurityManager securityManager(){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(userRealm());
        return defaultWebSecurityManager;
    }

    //Filter工厂，设置对应的过滤条件和跳转条件
    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //添加shiro内置过滤器
        /*
         * anon:表示可以匿名使用。
           authc:表示需要认证(登录)才能使用，没有参数
           roles：参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，当有多个参数时，例如admins/user/**=roles["admin,guest"],每个参数通过才算通过，相当于hasAllRoles()方法。
           perms：参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，例如/admins/user/**=perms["user:add:*,user:modify:*"]，当有多个参数时必须每个参数都通过才通过，想当于isPermitedAll()方法。
           rest：根据请求的方法，相当于/admins/user/**=perms[user:method] ,其中method为post，get，delete等。
           port：当请求的url的端口不是8081是跳转到schemal://serverName:8081?queryString,其中schmal是协议http或https等，serverName是你访问的host,8081是url配置里port的端口，queryString是你访问的url里的？后面的参数。
           authcBasic：没有参数表示httpBasic认证
           ssl:表示安全的url请求，协议为https
           user:当登入操作时不做检查
           */

        Map<String, String> map = new HashMap<String, String>();
        map.put("/**","authc");
        map.put("/static/**","anon");
        //登出
        map.put("/logout","logout");
        //对所有用户认证

        //被拦截返回登陆页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        //登录成功至首页
        shiroFilterFactoryBean.setSuccessUrl("/index");
        //认证不通过跳转页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }
    //加入注解的使用，不加入这个注解不生效
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        System.out.println("开启shiro注解");
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


    //加密算法
    private CredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        /**
         * hash算法:这里使用MD5算法;
         */
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        /**
         * 散列的次数，比如散列两次，相当于 md5(md5(""));
         */
        hashedCredentialsMatcher.setHashIterations(1);
        return hashedCredentialsMatcher;
    }

    /**
     *
     * @描述：开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator --- ---开启aop注解 和AuthorizationAttributeSourceAdvisor --- --- 开启shiro注解功能)即可实现此功能
     * </br>Enable Shiro Annotations for Spring-configured beans. Only run after the lifecycleBeanProcessor(保证实现了Shiro内部lifecycle函数的bean执行) has run
     * </br>不使用注解的话，可以注释掉这两个配置
     * @创建人：gugu
     * @创建时间：2018年12月17日 下午14:07
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        System.out.println("开启aop注解");
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }


    /**
     * 自定义用于捕捉 shiro权限 异常
     * java bean 配置无法捕捉错误
     * @return
     */
    @Bean
    public HandlerExceptionResolver solver() {
        HandlerExceptionResolver resolver = new MyExceptionResolver();
        return resolver;
    }

}
