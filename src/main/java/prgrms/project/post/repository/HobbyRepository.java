package prgrms.project.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prgrms.project.post.domain.user.Hobby;

@Repository
public interface HobbyRepository extends JpaRepository<Hobby, Long> {
}
