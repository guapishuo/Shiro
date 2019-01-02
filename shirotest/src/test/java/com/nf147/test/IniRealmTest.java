package com.nf147.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class IniRealmTest {

    @Test
    public void RealmTest() {
        IniRealm realm = new IniRealm("classpath:user.ini");
        //创建主体
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(realm);

        //进行认证
        UsernamePasswordToken token = new UsernamePasswordToken("jack", "1123456");
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            System.out.println("登录是否成功：" + subject.isAuthenticated());
            subject.checkRole("admin");
            subject.checkPermission("user:select");
        }catch (IncorrectCredentialsException e){
            System.out.println("密码错误");
            System.out.println(e.getMessage());
        }
        catch (UnknownAccountException e){
            System.out.println("账号错误");
            System.out.println(e.getMessage());
        }




    }
}
