package com.xiaobai.smsev.sys.config;


import com.xiaobai.smsev.sys.entity.UserInfo;
import com.xiaobai.smsev.sys.service.UserinfoService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 实现AuthorizingRealm接口用户用户认证
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserinfoService userService;

    //执行授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权");

        //获取当前登陆用户
        Subject subject = SecurityUtils.getSubject();
        UserInfo user = (UserInfo) subject.getPrincipal();
        //给资源授权
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(userService.getPerms(user.getId()));//添加角色
        simpleAuthorizationInfo.addStringPermissions(userService.getPerms(user.getId()));//添加权限
        return simpleAuthorizationInfo;

    }

    //执行认证逻辑
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("认证");
        //加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
        //shiro判断逻辑
        UsernamePasswordToken user = (UsernamePasswordToken) authenticationToken;
        UserInfo realUser = new UserInfo();
        realUser.setLoginname(user.getUsername());
        realUser.setPassword(String.copyValueOf(user.getPassword()));
        UserInfo newuser = userService.loadUserByUsername(user.getUsername());
        //ByteSource salt = ByteSource.Util.bytes(newuser.getSalt());
        if (newuser == null) {
            return null;
        }
        System.out.println("登录成功"+newuser.getUsername());
        //return new SimpleAuthenticationInfo(newuser, newuser.getPassword(),salt,user.getUsername());
        return new SimpleAuthenticationInfo(newuser, newuser.getPassword(),user.getUsername());
    }
}
