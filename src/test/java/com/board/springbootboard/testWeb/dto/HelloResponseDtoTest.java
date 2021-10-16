package com.board.springbootboard.testWeb.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HelloResponseDtoTest {

    @Test
    public void lombokTest() {
        //Given
        String name="test";
        int amount=1000;

        //When
        HelloResponseDto dto=new HelloResponseDto(name,amount);

        //Then
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);

    }


}