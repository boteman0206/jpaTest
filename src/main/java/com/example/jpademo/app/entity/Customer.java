package com.example.jpademo.app.entity;

import lombok.Data;

import javax.persistence.Id;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 客户的实体类
 * 配置映射关系
 * 1.实体类和表的映射关系
 * 2.实体类中属性和表中字段的映射关系
 */
@Entity
@Table(name = "cst_customer")
@Data
public class Customer  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cust_id")
    private Long custId; //客户的主键

    @Column(name = "cust_name")
    private String custName;//客户名称

    @Column(name = "cust_source")
    private String custSource;//客户来源



    //配置客户和联系人之间的关系（一对多关系）
    /**
     * 使用注解的形式配置多表关系
     *      1.声明关系
     *          @OneToMany : 配置一对多关系
     *              targetEntity ：对方对象的字节码对象
     *      2.配置外键（中间表）
     *              @JoinColumn : 配置外键
     *                  name：对方外键字段名称
     *                  referencedColumnName：参照的主表的主键字段名称
     *
     *  * 在客户实体类上（一的一方）添加了外键了配置，所以对于客户而言，也具备了维护外键的作用
     */

    @OneToMany(targetEntity = LinkMan.class)
    @JoinColumn(name = "lkm_cust_id", referencedColumnName = "cust_id")
    private List<LinkMan> linkMan;
    // 省略 getter 和 setter 方法
}

/**
 *
 * 一： @OneToOne 一对一关联关系
 *
 * 用法 @OneToOne 需要配合 @JoinColumn 一起使用。注意：可以双向关联，也可以只配置一方，看实际需求
 *
 *案例：假设一个部门只有一个员工，Department 的内容如下：
 * @OneToOne
 * @JoinColumn(name="employee_id",referencedColumnName="id")
 * private Employee employeeAttribute = new Employee();
 *注意：employee_id指的是 Department 里面的字段，而 referencedColumnName="id" 指的是 Employee 表里面的字段。
 *
 *如果需要双向关联，Employee 的内容如下：
 *
 * @OneToOne(mappedBy="employeeAttribute")
 * private Department department;
 *
 * 当然了也可以不选用 mappedBy 和下面效果是一样的：
 * @OneToOne
 * @JoinColumn(name="id",referencedColumnName="employee_id")
 * private Department department;
 *
 *
 *
 * 二： @OneToMany 一对多 & @ManyToOne 多对一
 * @ManyToOne 与 OneToMany 的源码稍有区别仔细体会。
 *
 * （2）使用案例，也是必须要和 @JoinColumn 配合使用才有效。
 *
 *
 *
 *
 *
 */
