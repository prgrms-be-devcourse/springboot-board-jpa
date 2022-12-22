package com.example.springbootboardjpa.service;

import com.example.springbootboardjpa.repoistory.PostJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Transactional
class DefaultPostServiceTest {

    @Autowired
    private DefaultPostService DefaultPostService;

    @Test
    public void setUp(){

    }

}