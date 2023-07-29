package com.jpaboard.domain.user.application;

import com.jpaboard.domain.user.dto.request.UserCreationRequest;
import com.jpaboard.domain.user.dto.response.UserResponse;
import com.jpaboard.domain.user.dto.request.UserUpdateRequest;
import jakarta.transaction.Transactional;

public interface UserService {

    Long createUser(UserCreationRequest request);

    UserResponse findCustomerById(Long id);

    void updateCustomer(Long id, UserUpdateRequest request);

    void deleteCustomer(Long id);
}
