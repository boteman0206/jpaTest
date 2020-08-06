package com.example.jpademo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;

@SpringBootApplication
public class JpademoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpademoApplication.class, args);
    }


    //让Spring管理JPAQueryFactory 全局导入
    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager entityManager){
        return new JPAQueryFactory(entityManager);
    }

}
