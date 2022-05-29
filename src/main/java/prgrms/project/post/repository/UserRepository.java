package prgrms.project.post.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import prgrms.project.post.domain.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    @Query("select u from User u join fetch u.hobbies where u.id = :id")
    Optional<User> findById(Long id);

    @Query("select u from User u")
    Slice<User> findUsersAll(Pageable pageable);
}
