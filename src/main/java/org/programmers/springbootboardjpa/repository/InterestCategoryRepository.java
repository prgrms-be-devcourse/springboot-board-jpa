package org.programmers.springbootboardjpa.repository;

import org.programmers.springbootboardjpa.domain.InterestCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterestCategoryRepository extends JpaRepository<Long, InterestCategory> {
}