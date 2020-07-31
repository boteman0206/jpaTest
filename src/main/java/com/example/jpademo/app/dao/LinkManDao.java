package com.example.jpademo.app.dao;


import com.example.jpademo.app.entity.LinkMan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkManDao extends JpaRepository<LinkMan,Integer> {
}

