package com.example.board.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Hobby {
    SPORTS("운동"),
    ARTS("예술");

    private final String value;

    @Override
    public String toString(){
        return this.value;
    }
}
