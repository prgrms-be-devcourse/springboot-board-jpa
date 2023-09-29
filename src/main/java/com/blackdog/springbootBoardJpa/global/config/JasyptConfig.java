package com.blackdog.springbootBoardJpa.global.config;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
@ConfigurationProperties("jasypt.encryptor")
@EnableEncryptableProperties
public class JasyptConfig {

    private String algorithm;
    private int poolSize;
    private String stringOutputType;
    private int keyObtentionIterations;

    @Bean("jasyptStringEncryptor")
    public StringEncryptor jasyptStringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig configuration = new SimpleStringPBEConfig();
        configuration.setAlgorithm(algorithm);
        configuration.setPoolSize(poolSize);
        configuration.setStringOutputType(stringOutputType);
        configuration.setKeyObtentionIterations(keyObtentionIterations);
        configuration.setPassword(getJasyptEncryptorPassword());
        encryptor.setConfig(configuration);
        return encryptor;
    }

    private String getJasyptEncryptorPassword() {
        try {
            ClassPathResource resource = new ClassPathResource("src/main/resources/jasypt-encryptor-password.txt");
            return String.join("", Files.readAllLines(Paths.get(resource.getPath())));
        } catch (IOException e) {
            throw new RuntimeException("jasypt password file not found");
        }
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    public String getStringOutputType() {
        return stringOutputType;
    }

    public void setStringOutputType(String stringOutputType) {
        this.stringOutputType = stringOutputType;
    }

    public int getKeyObtentionIterations() {
        return keyObtentionIterations;
    }

    public void setKeyObtentionIterations(int keyObtentionIterations) {
        this.keyObtentionIterations = keyObtentionIterations;
    }
}

