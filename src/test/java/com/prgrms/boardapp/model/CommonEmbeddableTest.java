package com.prgrms.boardapp.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonEmbeddableTest {

    @Test
    @DisplayName("createdBy 필드는 MAX를 넘어갈 수 없다.")
    void testHobbyValidateLength() {
        String maxOver = "*".repeat(CommonEmbeddable.CREATED_BY_MAX_LENGTH + 1);

        assertThrows(IllegalArgumentException.class, () -> CommonEmbeddable.builder().createdBy(maxOver).build());
    }

}