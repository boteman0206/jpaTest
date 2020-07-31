package com.example.jpademo.app.controller;


import com.example.jpademo.app.dao.CustomerDao;
import com.example.jpademo.app.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerDao customerDao;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Customer> getAccounts() {
        System.out.println("git test");
        return customerDao.findAll();
    }

}
