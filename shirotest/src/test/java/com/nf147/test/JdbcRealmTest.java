package com.nf147.test;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class JdbcRealmTest {
    ComboPooledDataSource dataSource = new ComboPooledDataSource();

    {
        dataSource.setJdbcUrl("jdbc:mariadb://localhost:3306/shiro");
        dataSource.setUser("vip");
        dataSource.setPassword("vip");
    }

    @Test
    public void jdbcRealmTest() {
        // 1. 设置域
        JdbcRealm realm = new JdbcRealm();
        realm.setDataSource(dataSource);
        realm.setAuthenticationQuery("select password from myuser where username = ?");
//        realm.setUserRolesQuery();
        realm.setPermissionsLookupEnabled(true);

        // 2. 初始化 SecurityManager
        DefaultSecurityManager securityManager = new DefaultSecurityManager(realm);
        SecurityUtils.setSecurityManager(securityManager);

        // 3. 使用 Shiro 进行认证
        Subject subject = SecurityUtils.getSubject(); // 得到当前用户对象
        subject.login(new UsernamePasswordToken("jack", "123456789")); // 使用提供的用户名密码，校验此用户到底合不合法

        System.out.println("登录是否成功：" + subject.isAuthenticated());

        // 4. 登陆成功后，就可以使用 Shiro 进行权限的校验了
        SecurityUtils.getSubject().checkRole("admin");
        SecurityUtils.getSubject().checkPermission("user:select");

        if (SecurityUtils.getSubject().hasRole("admin")) {
            System.out.println("我很厉害，我是管理员！");
        } else {
            System.out.println("我只是一只小小鸟...");
        }

        if (SecurityUtils.getSubject().isPermitted("xxx")) {
            System.out.println("我可以干什么");
        } else {
            System.out.println("报警，记日志！这是非法操作啊！");
        }
    }
}
