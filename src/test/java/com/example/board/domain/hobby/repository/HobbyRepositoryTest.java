package com.example.board.domain.hobby.repository;

import com.example.board.domain.hobby.entity.Hobby;
import com.example.board.domain.hobby.entity.HobbyType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.example.board.domain.hobby.entity.HobbyType.GAME;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class HobbyRepositoryTest {

    @Autowired
    private HobbyRepository hobbyRepository;

    @DisplayName("취미 타입을 통한 취미 검색에 성공한다.")
    @Test
    void find_hobby_by_hobby_type_success() {
        // given
        final HobbyType hobbyType = GAME;
        Hobby hobby = Hobby.builder()
                .hobbyType(hobbyType)
                .build();
        hobbyRepository.saveAndFlush(hobby);

        // when
        Hobby findHobby = hobbyRepository.findByHobbyType(hobbyType);

        // then
        assertThat(findHobby).hasFieldOrPropertyWithValue("hobbyType", hobbyType);
    }
}