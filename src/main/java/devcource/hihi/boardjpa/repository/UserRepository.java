package devcource.hihi.boardjpa.repository;

import devcource.hihi.boardjpa.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // 오프셋 기반 페이지네이션을 위한 메서드
    Page<User> findAll(Pageable pageable);

}
