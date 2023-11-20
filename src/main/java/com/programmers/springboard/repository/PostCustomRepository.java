package com.programmers.springboard.repository;

import java.util.List;

import com.programmers.springboard.entity.Post;

public interface PostCustomRepository {

	public List<Post> getPosts(Integer page);
}
