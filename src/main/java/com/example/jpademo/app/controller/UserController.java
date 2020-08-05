package com.example.jpademo.app.controller;


import com.example.jpademo.app.dao.UserPagingAndSortingRepository;
import com.example.jpademo.app.dao.UserRepository;
import com.example.jpademo.app.entity.User;
import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
@Slf4j
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserPagingAndSortingRepository userPagingAndSortingRepository;
    @GetMapping("/list")
    public Iterable<User> list(){

        log.info("user:list");
        return userRepository.findAll();
    }

    @GetMapping(path = "/add")
    public void addNewUser() {
        User n = new User();
        n.setName("jack");
        n.setEmail("1123.@qq.com");
        userRepository.save(n);
    }

    @GetMapping("/getPage")
    public Page<User> getPage(){



        Sort sort = Sort.by(Sort.Direction.ASC,"id");
        Pageable pageable = PageRequest.of(0, 10, sort);


//        多组字段排序
//        System.out.println("通过创建Sort.Order对象的集合创建sort对象");
//        List<Sort.Order> orders = new ArrayList<>();
//        orders.add(new Sort.Order(Sort.Direction.DESC,"id"));
//        orders.add(new Sort.Order(Sort.Direction.ASC,"firstName"));
//        Pageable pageable1 = PageRequest.of(0, 10, Sort.by(orders));

        Page<User> jack = userPagingAndSortingRepository.findByName("jack", pageable);
        Page<User> startingWith = userPagingAndSortingRepository.findByNameStartingWith("ja", pageable);
        System.out.println(startingWith);
        return jack;
    }


    @GetMapping("/getuser")
    public List<User> getUser(){
        List<User> jack = userRepository.findUserByEmailAndIdAndName("122", 1L, "jack");
        List<User> jack1 = userRepository.findUserByEmailIsStartingWithAndNameStartingWith("11", "jack");
        System.out.println(jack1);

        List<User> jack2 = userRepository.findFirst2ByName("jack");

        log.info("jack2 = " + jack2.toString());

        List<User> isNotNull = userRepository.findByNameIsNotNull();
        log.info("isNotNull = " + isNotNull);

        List<User> jack3 = userRepository.findFirstByNameOrderByIdDesc("jack");
        System.out.println("jack3 = " + jack3);
        return jack2;
    }

}
