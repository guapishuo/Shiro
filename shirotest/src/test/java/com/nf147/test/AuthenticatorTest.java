package com.nf147.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

public class AuthenticatorTest {

    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before
    public void addUser() {
        simpleAccountRealm.addAccount("jack", "12345612", "admin");
        simpleAccountRealm.addAccount("tom", "123456", "user");
    }

    @Test
    public void authenticatorTest() {
        //创建主体
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(simpleAccountRealm);

        //进行认证
        UsernamePasswordToken token = new UsernamePasswordToken("tom", "1234567", "admin");
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);

        System.out.println("登录是否成功：" + subject.isAuthenticated());
        subject.checkRoles("admin");
    }
}
