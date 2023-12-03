package com.example.board.global.security.detailsService;

import com.example.board.domain.member.entity.Member;
import com.example.board.domain.member.entity.MemberRole;
import com.example.board.domain.member.repository.MemberRepository;
import com.example.board.global.exception.CustomException;
import com.example.board.global.exception.ErrorCode;
import com.example.board.global.security.details.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findMemberWithRolesByEmail(email)
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        return createMember(member);
    }

    private UserDetails createMember(Member member) {
        List<MemberRole> roles = member.getRoles();
        List<SimpleGrantedAuthority> authorities = roles.stream().
            map(mr -> new SimpleGrantedAuthority(mr.getRole().getRoleType().toString())).toList();

        return new MemberDetails(member.getId().toString(), member.getEmail(), member.getPassword(), authorities);
    }
}
