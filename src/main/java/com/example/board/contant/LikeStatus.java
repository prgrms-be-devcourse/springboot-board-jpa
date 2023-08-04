package com.example.board.contant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@Getter
@RequiredArgsConstructor
public enum LikeStatus {

    LIKE(0, "좋아요"),
    DISLIKE(1, "싫어요");

    private final Integer value;

    private final String typeName;

    //프론트에서 받아온 Integer 타입을 enum 타입으로 변환하는 로직
    public static LikeStatus fromValue(Integer value) {
        for (LikeStatus likeStatus : LikeStatus.values()) {
            if (Objects.equals(likeStatus.value, value)) {
                return likeStatus;
            }
        }
        throw new IllegalArgumentException("유효하지 않은 값입니다.");
    }
}
