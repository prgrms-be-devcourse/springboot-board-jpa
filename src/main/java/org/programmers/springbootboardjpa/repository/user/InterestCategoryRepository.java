package org.programmers.springbootboardjpa.repository.user;

import org.programmers.springbootboardjpa.domain.user.InterestCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterestCategoryRepository extends JpaRepository<InterestCategory, Long> {
}