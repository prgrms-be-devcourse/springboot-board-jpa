package com.example.board.Dto;

import com.example.board.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class UserDto
{
    private String name;
    private int age;
    private String hobby;
}
