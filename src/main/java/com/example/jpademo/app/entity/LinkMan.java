package com.example.jpademo.app.entity;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "cst_linkman")
@Data
public class LinkMan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lkm_id")
    private Long lkmId; //联系人编号(主键)

    @Column(name = "lkm_name")
    private String lkmName;//联系人姓名

    @Column(name = "lkm_gender")
    private String lkmGender;//联系人性别


    /**
     * 配置联系人到客户的多对一关系
     *     使用注解的形式配置多对一关系
     *      1.配置表关系
     *          @ManyToOne : 配置多对一关系
     *              targetEntity：对方的实体类字节码
     *      2.配置外键（中间表）
     *
     * * 配置外键的过程，配置到了多的一方，就会在多的一方维护外键
     *
     */
//    @ManyToOne(targetEntity = Customer.class,fetch = FetchType.LAZY)
//    @JoinColumn(name = "lkm_cust_id", referencedColumnName = "cust_id")
//    private List<Customer> customer = new ArrayList<Customer>();;
    //省略 getter 和 setter 方法
}