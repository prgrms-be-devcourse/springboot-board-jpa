package com.prgms.jpaBoard.domain.user.presentation.dto;

import com.prgms.jpaBoard.domain.user.HobbyType;

public record UserSaveRequest(
        String name,
        int age,
        HobbyType hobbyType
) {
}
