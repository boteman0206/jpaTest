package com.example.jpademo.app.dao;

import com.example.jpademo.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;


//public interface UserRepository extends JpaRepository<User, Integer> {
//}

public interface UserRepository extends CrudRepository<User, Integer> {

}