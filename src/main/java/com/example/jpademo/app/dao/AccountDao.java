package com.example.jpademo.app.dao;

import com.example.jpademo.app.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountDao  extends JpaRepository<Account,Integer> {
}

