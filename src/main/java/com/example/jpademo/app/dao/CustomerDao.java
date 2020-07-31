package com.example.jpademo.app.dao;


import com.example.jpademo.app.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDao extends JpaRepository<Customer,Integer> {
}

