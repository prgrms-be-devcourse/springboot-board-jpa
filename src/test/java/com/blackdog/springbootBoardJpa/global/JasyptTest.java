package com.blackdog.springbootBoardJpa.global;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JasyptTest {

    @Test
    void jasypt() {
        String url = "jdbc:mysql://localhost:3306/jpa_board_mission";
        String username = "root";
        String password = "root1234!";

        String encryptUrl = jasyptEncrypt(url);
        String encryptUsername = jasyptEncrypt(username);
        String encryptPassword = jasyptEncrypt(password);

        System.out.println("encrypt url : " + encryptUrl);
        System.out.println("encrypt username: " + encryptUsername);
        System.out.println("encrypt password: " + encryptPassword);

        assertThat(url).isEqualTo(jasyptDecrypt(encryptUrl));
    }

    private String jasyptEncrypt(String input) {
        String key = "key1234!";
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        encryptor.setPassword(key);
        return encryptor.encrypt(input);
    }

    private String jasyptDecrypt(String input) {
        String key = "key1234!";
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        encryptor.setPassword(key);
        return encryptor.decrypt(input);
    }

}
