package com.example.jpademo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class JpademoApplicationTests {

    @Test
    void contextLoads() {
        LocalDate date = LocalDate.now();
        LocalDate localDate = date.plusWeeks(-1);
        System.out.println(localDate);
        LocalDate weekFirstDay = date.plusDays(1 - date.getDayOfWeek().getValue());
        System.out.println(weekFirstDay);
    }

}
