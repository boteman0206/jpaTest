package com.example.jpademo.app.dao;

import com.example.jpademo.app.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserPagingAndSortingRepository extends PagingAndSortingRepository<User, Integer> {

    Page<User> findByName(String name, Pageable pageable);
    Page<User> findByNameStartingWith(String name, Pageable pageable);
}
