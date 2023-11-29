package com.programmers.springboard.security;

import com.programmers.springboard.entity.Member;
import com.programmers.springboard.exception.MemberNotFoundException;
import com.programmers.springboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(MemberNotFoundException::new);
        return User.builder()
                .username(member.getLoginId())
                .password(member.getPasswd())
                .authorities(member.getGroups().getAuthorities())
                .build();
    }
}
