package com.prgrms.board.post.service;

import com.prgrms.board.domain.post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Transactional
    public void save() {

    }

    @Transactional
    public void findAll() {

    }

    @Transactional
    public void findOne() {

    }

    @Transactional
    public void update() {

    }
}
