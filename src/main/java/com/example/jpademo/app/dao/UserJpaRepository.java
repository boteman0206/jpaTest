package com.example.jpademo.app.dao;

import com.example.jpademo.app.entity.DTO.NamesOnly;
import com.example.jpademo.app.entity.DTO.UserNameAndId;
import com.example.jpademo.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Map;


public interface UserJpaRepository extends JpaRepository<User, Integer> {

    List<NamesOnly> findByName(String name);


    @Query("select s.name from User s where s.id=?1")
    String getIdandName(Long id);


    @Query("select s.id as id, s.name as name from User s ")
    List<UserNameAndId> getallusers();

}