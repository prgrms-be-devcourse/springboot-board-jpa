package com.spring.board.springboard.user.service;

import com.spring.board.springboard.user.domain.Member;
import com.spring.board.springboard.user.domain.dto.MemberRequestDto;
import com.spring.board.springboard.user.domain.dto.MemberLoginDto;
import com.spring.board.springboard.user.domain.dto.MemberResponseDto;
import com.spring.board.springboard.user.exception.AuthenticateException;
import com.spring.board.springboard.user.exception.NoMemberException;
import com.spring.board.springboard.user.repository.MemberRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class MemberService {

    private static final String userInfoCookieName = "user";
    private static final String NO_COOKIE = "사용자 정보를 알 수 없어 접근이 제한됩니다.";
    private static final Integer COOKIE_AGE = 60;

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    public Member findById(Integer memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> {
                    throw new NoMemberException(
                            MessageFormat.format("No Member data by id : {0}", memberId));
                });
    }

    @Transactional(readOnly = true)
    public MemberResponseDto findById(Integer memberId, HttpServletRequest request) {

        Cookie userInfo = getUserInfoCookie(request);

        final Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> {
                    throw new NoMemberException(
                            MessageFormat.format("No Member data by id : {0}", memberId));
                });

        validateMemberByCookie(userInfo, findMember);

        return new MemberResponseDto(findMember);
    }

    @Transactional
    public void register(MemberRequestDto memberRequestDto) {
        final Member newMember = memberRequestDto.toEntity();
        memberRepository.save(newMember);
    }

    @Transactional
    public MemberResponseDto login(MemberLoginDto memberLoginDto, HttpServletResponse response) {
        Member findMember = memberRepository.findByEmail(
                        memberLoginDto.email())
                .orElseThrow(() -> {
                    throw new NoMemberException(
                            MessageFormat.format("입력한 email: [{0}] 정보로 회원을 찾을 수 없습니다.", memberLoginDto.email()));
                });

        if (isNotEqual(findMember.getPassword(),
                memberLoginDto.password())) {
            throw new AuthenticateException("password가 올바르지 않습니다.");
        }

        Cookie cookie = setUserInfoCookie(findMember.getEmail());
        response.addCookie(cookie);

        return new MemberResponseDto(findMember);
    }

    private boolean isNotEqual(String origin, String input) {
        return !Objects.equals(origin, input);
    }

    private void validateMemberByCookie(Cookie cookie, Member findMember) {
        if (isNotEqual(cookie.getValue(), findMember.getEmail())) {
            throw new AuthenticateException("권한이 없습니다.");
        }
    }

    private Cookie getUserInfoCookie(HttpServletRequest request) {
        if(Objects.isNull(request.getCookies())){
            throw new AuthenticateException(NO_COOKIE);
        }

        List<Cookie> cookies = Arrays.stream(
                        request.getCookies())
                .toList();

        return cookies.stream()
                .filter(cookie ->
                        Objects.equals(cookie.getName(), userInfoCookieName))
                .findAny()
                .orElseThrow(() -> {
                    throw new AuthenticateException(NO_COOKIE);
                });
    }

    private Cookie setUserInfoCookie(String cookieValue){
        Cookie cookie = new Cookie(userInfoCookieName, cookieValue);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(COOKIE_AGE);
        return cookie;
    }
}
