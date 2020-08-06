package com.example.jpademo;

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

@SpringBootTest
class JpademoApplicationTests {

    @Test
    void contextLoads() {
        LocalDate date = LocalDate.now();
        LocalDate localDate = date.plusWeeks(-1);
        System.out.println(localDate);
        LocalDate weekFirstDay = date.plusDays(1 - date.getDayOfWeek().getValue());
        System.out.println(weekFirstDay);
    }

}
