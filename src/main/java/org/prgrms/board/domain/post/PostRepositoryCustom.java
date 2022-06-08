package org.prgrms.board.domain.post;

import java.util.List;

public interface PostRepositoryCustom {
	List<Post> findAll(long offset, int limit);
}
