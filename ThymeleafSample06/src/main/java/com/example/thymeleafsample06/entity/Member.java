package com.example.thymeleafsample06.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Member {
    private Integer id; //회원 ID
    private String name; //회원명
    
}
