package org.programmers.springbootboardjpa.web.dto.user;

import org.programmers.springbootboardjpa.domain.user.Age;
import org.programmers.springbootboardjpa.domain.user.Name;
import org.programmers.springbootboardjpa.domain.user.Password;
import org.programmers.springbootboardjpa.domain.user.User;

import java.time.LocalDate;
import java.util.List;

public record UserUpdateFormV1(Long userId, String nickname, String password, String firstname, String lastname,
                               LocalDate birthDate, List<String> interests)
        implements UserUpdateForm {

    @Override
    public User applyToUser(User user) {
        if (!userId.equals(user.getUserId())) {
            //TODO: 너무 방어적인 코드는 아닐지
            throw new IllegalArgumentException("수정하려는 대상 ID와 주어진 ID가 불일치합니다!");
        }
        return user.update(new User(userId,
                (isStringFieldAssigned(nickname)) ? nickname : user.getNickname(),
                (isStringFieldAssigned(password)) ? new Password(password) : user.getPassword(),
                (isStringFieldAssigned(firstname) && isStringFieldAssigned(lastname)) ? new Name(firstname, lastname) : user.getName(),
                (birthDate != null) ? new Age(birthDate) : user.getAge(),
                //TODO: Interest 처리
                user.getInterests()));
    }

    private boolean isStringFieldAssigned(String field) {
        return ((field != null) && !(toString().isBlank()));
    }
}