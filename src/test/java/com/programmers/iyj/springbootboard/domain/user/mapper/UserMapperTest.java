package com.programmers.iyj.springbootboard.domain.user.mapper;

import com.programmers.iyj.springbootboard.domain.user.domain.Hobby;
import com.programmers.iyj.springbootboard.domain.user.domain.User;
import com.programmers.iyj.springbootboard.domain.user.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {
    private final UserMapper mapper
            = Mappers.getMapper(UserMapper.class);

    @Test
    public void entityToDto() {
        // Given
        User user = User.builder()
                .name("john")
                .age(25)
                .hobby(Hobby.NETFLIX)
                .build();

        // When
        UserDto userDto = mapper.entityToDto(user);

        // Then
        assertEquals(user.getName(), userDto.getName());
        assertEquals(user.getAge(), userDto.getAge());
        assertEquals(user.getHobby(), userDto.getHobby());
    }

    @Test
    public void dtoToEntity() {
        // Given
        UserDto userDto = UserDto.builder()
                .name("john")
                .age(25)
                .hobby(Hobby.NETFLIX)
                .build();

        // When
        User user = mapper.dtoToEntity(userDto);

        // Then
        assertEquals(userDto.getName(), user.getName());
        assertEquals(userDto.getAge(), user.getAge());
        assertEquals(userDto.getHobby(), user.getHobby());
    }
}