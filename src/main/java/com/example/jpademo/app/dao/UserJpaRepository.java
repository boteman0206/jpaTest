package com.example.jpademo.app.dao;

import com.example.jpademo.app.entity.DTO.NamesOnly;
import com.example.jpademo.app.entity.DTO.UserNameAndId;
import com.example.jpademo.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;


public interface UserJpaRepository<T> extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    List<NamesOnly> findByName(String name);

    // 定义一个泛型接口
    List<T> findByName(String lastname, Class<T> type);



    @Query("select s.name from User s where s.id=?1")
    String getIdandName(Long id);


    @Query("select s.id as id, s.name as name from User s ")
    List<UserNameAndId> getallusers();



//    指定原生的sql进行查询 如果要使用Sort就不要写原生的sql
//    注意点：nativeQuery 不支持直接 Sort 的参数查询。
    @Query(value = "SELECT * FROM user order by id desc ", nativeQuery = true)
    List<User> findByEmailAddress();

    // 通过参数进行排序写法二
    // TODO: 20-8-5 参数化sort不起作用 待验证
    @Query(value = "select * from user where name=?1 order by ?2 desc",nativeQuery = true)
    List<User> findByFirstName(String firstName,String sort);


//      使用param参数化进行查询
    @Query("select u from User u where u.name = :name or u.email = :email ")
    List<User> findByLastnameOrFirstname(@Param("name") String name,
                                   @Param("email") String email);



}