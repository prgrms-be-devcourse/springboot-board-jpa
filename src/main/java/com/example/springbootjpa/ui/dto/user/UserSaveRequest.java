package com.example.springbootjpa.ui.dto.user;

import com.example.springbootjpa.domain.user.Hobby;

public record UserSaveRequest(
        String name,
        int age,
        Hobby hobby
) {
}
