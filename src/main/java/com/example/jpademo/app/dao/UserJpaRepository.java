package com.example.jpademo.app.dao;

import com.example.jpademo.app.entity.DTO.NamesOnly;
import com.example.jpademo.app.entity.DTO.UserNameAndId;
import com.example.jpademo.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Map;


public interface UserJpaRepository<T> extends JpaRepository<User, Integer> {

    List<NamesOnly> findByName(String name);

    // 定义一个泛型接口
    List<T> findByName(String lastname, Class<T> type);



    @Query("select s.name from User s where s.id=?1")
    String getIdandName(Long id);


    @Query("select s.id as id, s.name as name from User s ")
    List<UserNameAndId> getallusers();



//    指定原生的sql进行查询
//    注意点：nativeQuery 不支持直接 Sort 的参数查询。
    @Query(value = "SELECT * FROM user order by id desc ", nativeQuery = true)
    List<User> findByEmailAddress();

    // 通过参数进行排序写法二
    @Query(value = "select * from user where name=?1 order by ?2 desc",nativeQuery = true)
    List<User> findByFirstName(String firstName,String sort);




}