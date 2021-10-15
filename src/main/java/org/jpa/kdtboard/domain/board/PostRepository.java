package org.jpa.kdtboard.domain.board;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yunyun on 2021/10/11.
 */
public interface PostRepository<T extends Post> extends JpaRepository<T, Long> {

}
