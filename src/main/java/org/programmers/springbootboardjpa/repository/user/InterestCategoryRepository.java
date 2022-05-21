package org.programmers.springbootboardjpa.repository.user;

import org.programmers.springbootboardjpa.domain.user.InterestCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterestCategoryRepository extends JpaRepository<InterestCategory, Long> {
    //TODO: 자주 조회되는 Category 값은 자체적으로 map 만들어서 캐싱하면 어떨까>?
}