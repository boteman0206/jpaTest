package com.example.jpademo.app.dao;


import com.example.jpademo.app.entity.Customer;
import com.example.jpademo.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public interface CustomerDao extends JpaRepository<Customer,Integer> {
    // 异步查询
    @Async
    Future<User> findByCustName(String name);
    @Async
    CompletableFuture<User> findOneByCustName(String name);
    @Async
    ListenableFuture<User> findOneByCustId(Long id);
    @Async
    List<Customer> findAll();
}

