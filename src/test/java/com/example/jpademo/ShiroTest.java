package com.example.jpademo;


import com.example.jpademo.app.shiroTest.Login;
import junit.framework.Assert;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;

@SpringBootTest
public class ShiroTest {

    @Test
    void test() {
        System.out.println("shiro test");
    }


    /**
     * 使用shiro配置文件下面的ini文件进行测试
     */
    @Test
    public void testHelloworld() {
        //1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        Factory<SecurityManager> factory =
                new IniSecurityManagerFactory("classpath:shiro.ini");
        //2、得到SecurityManager实例 并绑定给SecurityUtils
        org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123"); // 正确的名称进行登陆
//        UsernamePasswordToken token = new UsernamePasswordToken("zhang1", "123"); // 错误的名称登陆

        try {
            //4、登录，即身份验证
            subject.login(token);
        } catch (AuthenticationException e) {
            //5、身份验证失败
            System.out.println(e.getStackTrace());
        }

        System.out.println("是否登陆  = " + subject.isAuthenticated());
        Assert.assertEquals(true, subject.isAuthenticated()); //断言用户已经登录

        //6、退出
        subject.logout();
    }


    /**
     * shiro-realm.ini 自定义配置管理器测试
     */
    @Test
    public void testShiroRealm() {
        //1、获取SecurityManager工厂，此处使用realm.ini配置文件初始化SecurityManager
        Factory<SecurityManager> factory =
                new IniSecurityManagerFactory("classpath:shiro-realm.ini");
        //2、得到SecurityManager实例 并绑定给SecurityUtils
        org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        System.out.println("subject : " +subject);
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123"); // 正确的名称进行登陆

        try {
            //4、登录，即身份验证
            subject.login(token);
        } catch (AuthenticationException e) {
            //5、身份验证失败
            System.out.println(e.getStackTrace());
        }

        System.out.println("是否登陆  = " + subject.isAuthenticated());
        Assert.assertEquals(true, subject.isAuthenticated()); //断言用户已经登录

        //6、退出
        subject.logout();
    }


    /**
     * 角色的配置文件测试
     */
    @Test
    public void testHasRole() {
        Login login = new Login();
        Subject subject = login.login("classpath:shiro-role.ini", "zhang", "123");

        //判断拥有角色：role1
        Assert.assertTrue(subject.hasRole("role1"));
        System.out.println(subject.hasRole("role1"));
        //判断拥有角色：role1 and role2
        Assert.assertTrue(subject.hasAllRoles(Arrays.asList("role1", "role2")));
        //判断拥有角色：role1 and role2 and !role3
        boolean[] result = subject.hasRoles(Arrays.asList("role1", "role2", "role3"));
        for (boolean b : result) {
            System.out.println(b);
        }
    }


    /**
     * 角色接口的配置
     * Shiro提供了isPermitted和isPermittedAll用于判断用户是否拥有某个权限或所有权限，
     *  没有提供如isPermittedAny用于判断拥有某一个权限的接口。
     *
     *  subject.checkPermission("user:create");
     *  权限如果没有会报错
     */
    @Test
    public void testIsPermitted() {
        Login login = new Login();

        login.login("classpath:shiro-permission.ini", "zhang", "123");

        Subject subject = SecurityUtils.getSubject();

        //判断拥有权限：user:create
        Assert.assertTrue(subject.isPermitted("user:create"));
        System.out.println("user::create " + subject.isPermitted("user:create"));
        //判断拥有权限：user:update and user:delete
        Assert.assertTrue(subject.isPermittedAll("user:update", "user:delete"));
        System.out.println("user:update:delete " + subject.isPermittedAll("user:update", "user:delete"));
        //判断没有权限：user:view
        Assert.assertFalse(subject.isPermitted("user:view"));
        System.out.println("user:view " + subject.isPermitted("user:view"));
    }






}
