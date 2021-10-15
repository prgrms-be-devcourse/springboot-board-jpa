package org.jpa.kdtboard.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by yunyun on 2021/10/15.
 */

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class EncryptTest {


    @Test
    @DisplayName("동일한 두 값을 암호화할 경우, 두 값은 일치한다.")
    void SHA256TestForValid() throws NoSuchAlgorithmException {
        //Given
        var test1 = new String("1234");
        var test2 = new String("1234");

        //When
        var result = Encrypt.SHA256(test1).equals(Encrypt.SHA256(test2));

        //Then
        assertThat(result, is(TRUE));
    }

    @Test
    @DisplayName("동일하지 않은 두 값을 암호화할 경우, 두 값은 불일치한다.")
    void SHA256TestForInvalid() throws NoSuchAlgorithmException {
        //Given
        var test1 = new String("1234");
        var test2 = new String("4321");

        //When
        var result = Encrypt.SHA256(test1).equals(Encrypt.SHA256(test2));

        //Then
        assertThat(result, is(FALSE));
    }
}