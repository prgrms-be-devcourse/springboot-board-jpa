package com.example.springbootboard.domain;

import com.example.springbootboard.domain.vo.Name;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
public class NameValidationTest {

    @Test
    @DisplayName("Name의 유효성 검사 실패 경우를 확인한다.")
    public void testInvalidEmail() {
        assertThrows(IllegalArgumentException.class, () -> {
            var name = new Name("허승연승연");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            var name = new Name("허");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            var name = new Name("eddie");
        });
    }

    @Test
    @DisplayName("Name의 유효성 검사 성공 경우를 확인한다.")
    public void testValidName() {
        var name = new Name("허승연");
        assertEquals("허승연", name.getFullName());
    }

    @Test
    @DisplayName("Name의 동등성 검사를 확인한다.")
    public void testEqName() {
        var name1 = new Name("허승연");
        var name2 = new Name("허승연");
        assertEquals(name1, name2);
    }
}

