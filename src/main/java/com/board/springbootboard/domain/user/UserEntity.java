package com.board.springbootboard.domain.user;


import com.board.springbootboard.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@DynamicUpdate // 변경 필드만 대응
public class UserEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="name",nullable = false,length = 30)
    private String name;

    // nickName은 unique로 설정 -> nickName으로 조회하기 위해
    @Column(name="nick_name",nullable = false,length = 30,unique = true)
    private String nickName;

    @Column(name="age",nullable = false)
    private int age;

    // Builder
    @Builder
    public UserEntity(String name, String nickName, int age) {
        this.name = name;
        this.nickName = nickName;
        this.age = age;
    }

    // 수정
    public void update(String name,String nickName) {
        this.name=name;
        this.nickName=nickName;
    }



    //Todo - 연관관계 매핑


}
