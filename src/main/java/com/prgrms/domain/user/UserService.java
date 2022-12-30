package com.prgrms.domain.user;

import static com.prgrms.dto.UserDto.Request;

import com.prgrms.dto.UserDto.Request.Login;
import com.prgrms.dto.UserDto.Response;
import com.prgrms.exception.ErrorCode;
import com.prgrms.exception.customException.EmailDuplicateException;
import com.prgrms.exception.customException.UserNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.nio.file.AccessDeniedException;
import java.util.Arrays;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
public class UserService {

    private final UserRepository userRepository;

    private final String COOKIE_NAME_USER_ID = "userId";
    private final String EMPTY = "";

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Response insertUser(@Valid Request userDto) {

        checkEmailDuplicate(userDto.email());

        User save = userRepository.save(userDto.toUser());
        return new Response(save);
    }

    private void checkEmailDuplicate(String email) {

        if (userRepository.existsByEmail(email)) {
            throw new EmailDuplicateException("이메일이 중복되었습니다", ErrorCode.INVALID_PARAMETER);
        }
    }

    @Transactional(readOnly = true)
    public Response findUserById(Long id) {

        return userRepository.findById(id)
            .map(Response::new)
            .orElseThrow(() -> new UserNotFoundException("id: " + id, ErrorCode.USER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Response login(@Valid Login loginDto, HttpServletResponse servletResponse) {

        Response response = userRepository.findUser(loginDto.email(), loginDto.password())
            .map(Response::new)
            .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        setCookieUserId(response.getUserId(), servletResponse);

        return response;
    }

    public void logout(HttpServletRequest request, HttpServletResponse response)
        throws AccessDeniedException {

        Cookie cookieUserId = getCookieUserId(request);
        cookieUserId.setValue(EMPTY);
        response.addCookie(cookieUserId);
    }

    public Cookie getCookieUserId(HttpServletRequest request) throws AccessDeniedException {

        return Arrays.stream(request.getCookies())
            .filter(i -> COOKIE_NAME_USER_ID.equals(i.getName()))
            .filter(i -> !EMPTY.equals(i.getValue()))
            .findAny()
            .orElseThrow(() -> new AccessDeniedException("접근 권한이 없습니다"));
    }

    private void setCookieUserId(Long userId, HttpServletResponse response) {

        Cookie userCookie = new Cookie(COOKIE_NAME_USER_ID, String.valueOf(userId));
        response.addCookie(userCookie);
    }

}
