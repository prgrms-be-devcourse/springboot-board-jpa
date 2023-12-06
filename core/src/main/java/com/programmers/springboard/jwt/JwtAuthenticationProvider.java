package com.programmers.springboard.jwt;

import com.programmers.springboard.entity.Member;
import com.programmers.springboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final Jwt jwt;
    private final MemberService memberService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;

        return processUserAuthentication(
            String.valueOf(jwtAuthenticationToken.getPrincipal()),
            String.valueOf(jwtAuthenticationToken.getCredentials())
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }

    public Authentication processUserAuthentication(String principal, String credential) {
        try{
            Member member = memberService.login(principal, credential);
            List<GrantedAuthority> authorities = member.getGroups().getAuthorities();

            String token = getToken(member.getLoginId(), authorities);
            JwtAuthenticationToken authenticated =
                    new JwtAuthenticationToken(authorities,
                            new JwtAuthentication(token, member.getLoginId()),
                            null);
            authenticated.setDetails(member);
            return authenticated;
        } catch (IllegalStateException e) {
            throw new BadCredentialsException(e.getMessage());
        } catch (Exception e) {
            throw new AuthenticationServiceException(e.getMessage(), e);
        }
    }

    private String getToken(String loginId, List<GrantedAuthority> authorities) {
        String[] roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toArray(String[]::new);
        return jwt.sign(Jwt.Claims.from(loginId, roles));
    }
}
