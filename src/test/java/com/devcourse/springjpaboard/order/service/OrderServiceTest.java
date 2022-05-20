package com.devcourse.springjpaboard.order.service;

import com.devcourse.springjpaboard.domain.order.OrderStatus;
import com.devcourse.springjpaboard.order.dto.*;
import org.h2.command.dml.MergeUsing;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    String uuid = UUID.randomUUID().toString();

    @Test
    void save_test() {
        // Given
        OrderDto orderDto = OrderDto.builder()
                .uuid(uuid)
                .memo("문 앞 보관")
                .orderDatetime(LocalDateTime.now())
                .orderStatus(OrderStatus.OPENED)
                .memberDto(
                        MemberDto.builder()
                                .name("kang")
                                .nickName("hong")
                                .address("xxxxxxx")
                                .age(33)
                                .description("---")
                                .build()
                )
                .orderItemDtos(List.of(
                        OrderItemDto.builder()
                                .price(1000)
                                .quantity(100)
                                .itemDtos(List.of(
                                        ItemDto.builder()
                                                .type(ItemType.FOOD)
                                                .chef("baek")
                                                .price(1000)
                                                .build()
                                ))
                                .build()
                ))
                .build();
        //When
        String saveUuid = orderService.save(orderDto);
        //Then
        assertThat(uuid).isEqualTo(saveUuid);
    }
}