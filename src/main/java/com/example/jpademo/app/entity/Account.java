package com.example.jpademo.app.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "account") // 指定表的名称 不指定的化默认是类的名称小写
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"}) // 不序列化会报错
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;

    private String name ;

    private double money;

}
