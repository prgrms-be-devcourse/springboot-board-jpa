package org.prgms.board.domain.post.service;

import org.prgms.board.domain.post.domain.Post;
import org.prgms.board.domain.post.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

	/** 게시글 페이징 조회 **/
	Page<PostDto.Response> getPostPage(Pageable pageable);

	/** 게시글 단건 조회 **/
	PostDto.Response getOnePost(Long postId);

	/** 게시글 작성 **/
	void writePost(PostDto.Write writeDto);

	/** 게시글 수정 **/
	void updatePost(PostDto.Update updateDto);
}
