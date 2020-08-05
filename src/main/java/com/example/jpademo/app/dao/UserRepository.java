package com.example.jpademo.app.dao;

import com.example.jpademo.app.entity.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import java.util.List;

import java.util.stream.Stream;


//public interface UserRepository extends JpaRepository<User, Integer> {
//}

public interface UserRepository extends CrudRepository<User, Integer> {
    List<User> findUserByEmailAndIdAndName(String email, Long id, String name);

    List<User> findUserByEmailOrName(String email,String name);
    List<User> findUserByEmailIsStartingWithAndNameStartingWith(String email,String name);

    List<User> findFirst2ByName(String  name);
    List<User> findFirstByName(String  name);


    List<User> findByNameIsNotNull();


    List<User> findFirstByNameOrderByIdDesc(String name);


//    stream返回类型
    Stream<User> findAllByName(String name);

    @Query("select u from User u")
    Stream<User> findAllByCustomQueryAndStream();





}