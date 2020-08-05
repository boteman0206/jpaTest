package com.example.jpademo.app.entity.DTO;

import lombok.Data;

@Data
public class NamesOnlyDto {
    private final String name;
    private final  Long id;
    //注意构造方法

    public NamesOnlyDto(String name, Long id) {
        this.name = name;
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public Long getId() {
        return this.id;
    }
}