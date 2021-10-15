package org.jpa.kdtboard.domain.board;

/**
 * Created by yunyun on 2021/10/12.
 */
public enum PostScope {
    PRIVATE,  // 친구와 선생님 모두
    PUBLIC,  // 선생님만
    NONE // 질문 게시물이 아닐 경우 사용.
}
