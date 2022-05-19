package org.programmers.springbootboardjpa.domain.user;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.List;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInterests {

    @OneToMany(mappedBy = "user")
    private List<UserInterest> userInterests;
}