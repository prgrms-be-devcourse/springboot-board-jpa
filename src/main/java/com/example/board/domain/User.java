package com.example.board.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="user")
//@SequenceGenerator(name="user_seq",sequenceName = "seq",initialValue = 1,allocationSize = 1)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name",nullable = false)
    private String name;

    private  int age;

    private  String hobby;

    @OneToMany(mappedBy = "user")
    private List<Post> post=new ArrayList<>();

    public void setAge(int age){

        if(age<0||age>200){
            throw new IllegalArgumentException("올바르지 않은 값입니다");

        }


    }





}
