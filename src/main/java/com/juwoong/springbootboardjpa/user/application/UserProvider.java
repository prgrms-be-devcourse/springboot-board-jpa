package com.juwoong.springbootboardjpa.user.application;

import com.juwoong.springbootboardjpa.user.domain.User;

public interface UserProvider {

    User provideUser(Long id);

}
