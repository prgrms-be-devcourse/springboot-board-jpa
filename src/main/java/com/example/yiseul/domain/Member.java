package com.example.yiseul.domain;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;


public class Member {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private int age;


    private String hobby;

//    private


}
