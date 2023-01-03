package com.example.springbootboard.entity;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public enum Hobby {
    MUSIC, GAME, CODING, SOCCER;

    private static final List<Hobby> HOBBY_LIST = Arrays.asList(MUSIC, GAME, CODING, SOCCER);
    public static Hobby getRandomHobby(){
        return HOBBY_LIST.get(new Random().nextInt(HOBBY_LIST.size()));
    }
}
