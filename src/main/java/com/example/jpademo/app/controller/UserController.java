package com.example.jpademo.app.controller;


import com.example.jpademo.app.dao.UserJpaRepository;
import com.example.jpademo.app.dao.UserPagingAndSortingRepository;
import com.example.jpademo.app.dao.UserRepository;
import com.example.jpademo.app.entity.DTO.NamesOnly;
import com.example.jpademo.app.entity.DTO.NamesOnlyDto;
import com.example.jpademo.app.entity.DTO.UserNameAndId;
import com.example.jpademo.app.entity.QUser;
import com.example.jpademo.app.entity.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping(value = "/user")
@Slf4j
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserPagingAndSortingRepository userPagingAndSortingRepository;

    @Autowired
    UserJpaRepository userJpaRepository;


    @GetMapping("/list")
    public Iterable<User> list(){

        log.info("user:list");
        return userRepository.findAll();
    }

    @GetMapping(path = "/add")
    public void addNewUser() {
        User n = new User();
        n.setName("jack");
        n.setEmail("1123.@qq.com");
        userRepository.save(n);
    }

    @GetMapping("/getPage")
    public Page<User> getPage(){



        Sort sort = Sort.by(Sort.Direction.ASC,"id");
        Pageable pageable = PageRequest.of(0, 10, sort);


//        多组字段排序
//        System.out.println("通过创建Sort.Order对象的集合创建sort对象");
//        List<Sort.Order> orders = new ArrayList<>();
//        orders.add(new Sort.Order(Sort.Direction.DESC,"id"));
//        orders.add(new Sort.Order(Sort.Direction.ASC,"firstName"));
//        Pageable pageable1 = PageRequest.of(0, 10, Sort.by(orders));

        Page<User> jack = userPagingAndSortingRepository.findByName("jack", pageable);
        Page<User> startingWith = userPagingAndSortingRepository.findByNameStartingWith("ja", pageable);
        System.out.println(startingWith);
        return jack;
    }


    @GetMapping("/getuser")
    public List<User> getUser(){
        List<User> jack = userRepository.findUserByEmailAndIdAndName("122", 1L, "jack");
        List<User> jack1 = userRepository.findUserByEmailIsStartingWithAndNameStartingWith("11", "jack");
        System.out.println(jack1);

        List<User> jack2 = userRepository.findFirst2ByName("jack");

        log.info("jack2 = " + jack2.toString());

        List<User> isNotNull = userRepository.findByNameIsNotNull();
        log.info("isNotNull = " + isNotNull);

        List<User> jack3 = userRepository.findFirstByNameOrderByIdDesc("jack");
        System.out.println("jack3 = " + jack3);


        return jack2;
    }


    @GetMapping("/stream")
    @Transactional(readOnly = true)
    public List<User> getStream(){


        // stream 测试  需要加上@Transactional(readOnly = true)注解 否则报错
        Stream<User> allByCustomQueryAndStream = userRepository.findAllByCustomQueryAndStream();
        allByCustomQueryAndStream.forEach(System.out::println);


        Stream<User> stream = null;
        try {
            stream = userRepository.findAllByName("jack");
            stream.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream!=null){
                stream.close();  // 流的关闭
            }
        }

        return null;
    }


    // 自定意返回的字段
    @GetMapping("/define")
    public List<NamesOnly>  getDefine() {
        List<NamesOnly> jack = userJpaRepository.findByName("jack");

        String idandName = userJpaRepository.getIdandName(1L);
        System.out.println(idandName);

        List<UserNameAndId> getallusers = userJpaRepository.getallusers();
        for (UserNameAndId getalluser : getallusers) {
            System.out.println(getalluser.getId() + "===" + getalluser.getName());
        }



//        另外的方式

        //我想包含全字段，就直接用原始entity（Person.class）接收即可
        List<User> jack1 = userJpaRepository.findByName("jack", User.class);
        System.out.println("所有的user = " + jack1);
        //如果我想仅仅返回名称，我只需要指定Dto即可。
        List<NamesOnlyDto> jack2 = userJpaRepository.findByName("jack", NamesOnlyDto.class);
        System.out.println("matters = " + jack2);



        return jack;
    }


    @GetMapping("/sql")
    public void getOriginSql(){
        List<User> byEmailAddress = userJpaRepository.findByEmailAddress();
        System.out.println(byEmailAddress);

        // 测试不起作用，不知道为什么
        List<User> byFirstName = userJpaRepository.findByFirstName("jack", "id");
        System.out.println(byFirstName);


        List byLastnameOrFirstname = userJpaRepository.findByLastnameOrFirstname("jack", "jack");
        System.out.println(byLastnameOrFirstname);
    }




    @GetMapping("/getQuery")
    public void getQuery(){

        // TODO: 20-8-5 可以动态的执行拼接查询条件 注意点
        /**
         * 1： 因为是基本类型，需要忽略掉。因为基本数据类型会被加在where条件中进行过滤
         * 2： 查询 Null 值   //改变“Null值处理方式”：包括。 .withIncludeNullValues()
         * 3：  //忽略其他属性
         *      .withIgnorePaths("id", "name", "sex", "age", "focus", "addTime", "remark", "customerType");
         *
         */


        //创建查询条件数据对象
        User user = new User();
        user.setName("Ja");
        user.setEmail("1");

        //创建匹配器，即如何使用查询条件
        ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.startsWith()) //姓名采用“开始匹配”的方式查询
                .withMatcher("email", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("id");  //忽略属性：是否关注。因为是基本类型，需要忽略掉

        //创建实例
        Example<User> ex = Example.of(user, matcher);
        List all = userJpaRepository.findAll(ex);

        for (Object o : all) {
            System.out.println("====" + o);
        }




    }


    /**
     * 简单的动态参数查询
     * @return
     */

    @GetMapping("activate")
    public Page<User> getActivate(){
        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        int page = 0;
        int pageSize = 5;
        Pageable pageable = PageRequest.of(page,pageSize,sort);

        //通常使用 Specification 的匿名内部类
        Specification<User> specification = new Specification<User>() {

            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path id = root.get("id");
                Predicate predicateId = criteriaBuilder.ge(id,1);
                return predicateId ;
            }
        };

        Page<User> userPage = userJpaRepository.findAll(specification,  pageable);
        return userPage;
    }



    /**
     * 复杂的条件动态拼接查询
     */
    @GetMapping("difficulty")
    public Page getDifficulty(){
        String name = "jack";
        String email = "1";
        Long id = 1L;


        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        int page = 0;
        int pageSize = 5;
        Pageable pageable = PageRequest.of(page,pageSize,sort);


        // 待分页的查询
        Page all = userJpaRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<Predicate>();
            if (StringUtils.isNotBlank(name)) {
                //liked的查询条件
                predicates.add(cb.like(root.get("name"), "%" + name + "%"));
            }
            if (StringUtils.isNotBlank(email)) {
                //equal查询条件
//                predicates.add(cb.like(root.get("email"), "%" + email + "%"));
                // 负责的or关联条件
                predicates.add(cb.or(cb.like(root.get("email"), "%" + email + "%"), cb.like(root.get("name"), "%"+name+ "%")));
            }
            if (id != null) {
                //greaterThan大于等于查询条件
                predicates.add(cb.greaterThanOrEqualTo(root.get("id"), id));
            }
//            if (userParam.getBeginCreateTime()!=null&&userParam.getEndCreateTime()!=null){
//                根据时间区间去查询   predicates.add(cb.between(root.get("createTime"),userParam.getBeginCreateTime(),userParam.getEndCreateTime()));
//            }
//            if (StringUtils.isNotBlank(userParam.getAddressCity())) {
//                //联表查询，利用root的join方法，根据关联关系表里面的字段进行查询。
//                predicates.add(cb.equal(root.join("addressEntityList").get("addressCity"), userParam.getAddressCity()));
//            }
            return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        }, pageable);

        return all;



//        方式二 不带分页
//        List all = userJpaRepository.findAll((Specification<User>) (itemRoot, query, criteriaBuilder) -> {
//            //这里用 List 存放多种查询条件，实现动态查询
//            List<Predicate> predicatesList = new ArrayList<>();
//            //name 模糊查询，like 语句
//            if (name != null) {
//                predicatesList.add(
//                        criteriaBuilder.and(
//                                criteriaBuilder.like(
//                                        itemRoot.get("name"), "%" + name + "%")));
//            }
//            // itemPrice like 语句
//            if (email != null) {
//                predicatesList.add(
//                        criteriaBuilder.and(
//                                criteriaBuilder.like(
//                                        itemRoot.get("email"), "%" + email + "%")));
//            }
////            //itemStock 大于等于 >= 语句
//            if (id != null) {
//                predicatesList.add(
//                        criteriaBuilder.and(
//                                criteriaBuilder.ge(
//                                        itemRoot.get("id"), id)));
//            }
//            //where() 拼接查询条件
//            query.where(predicatesList.toArray(new Predicate[predicatesList.size()]));
//            //返回通过 CriteriaQuery 拼装的 Predicate
//            return query.getRestriction();
//        });
//
//        System.out.println("all = " + all);
//        return all;
    }


    @GetMapping("querydsl")
    public Page<User> getquerydsl(){
//        querydsl 测试使用

        QUser user = QUser.user;

        // todo 这个和上面的Predicate有冲突  需要注意

        // 构建查询条件
        com.querydsl.core.types.Predicate jackor = user.name.startsWith("jack").or(user.email.contains("1"));
        com.querydsl.core.types.Predicate jackand = user.name.startsWith("jack").and(user.email.contains("1"));

        //分页排序
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC,"id"));
        PageRequest pageRequest = PageRequest.of(0,10,sort);
        //查找结果
        Page<User> tCityPage = userJpaRepository.findAll((com.querydsl.core.types.Predicate) jackor, pageRequest);

        return tCityPage;

    }

}
