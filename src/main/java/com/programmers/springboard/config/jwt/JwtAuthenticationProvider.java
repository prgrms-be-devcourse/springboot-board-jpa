package com.programmers.springboard.config.jwt;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.programmers.springboard.entity.Member;
import com.programmers.springboard.exception.LoginFailException;
import com.programmers.springboard.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
	private final Jwt jwt;
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public boolean supports(Class<?> authentication) {
		return JwtAuthenticationToken.class.isAssignableFrom(authentication);
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		JwtAuthenticationToken jwtAuthentication = (JwtAuthenticationToken)authentication;
		return processUserAuthentication(
			String.valueOf(jwtAuthentication.getPrincipal()),
			jwtAuthentication.getCredentials()
		);
	}

	private Authentication processUserAuthentication(String principal, String credentials) {
		try {
			Member member = login(principal, credentials);
			List<GrantedAuthority> authorities = member.getAuthorities();
			String token = getToken(member.getLoginId(), authorities);
			JwtAuthenticationToken authenticated =
				new JwtAuthenticationToken(new JwtAuthentication(token, member.getLoginId()), null, authorities);
			authenticated.setDetails(member);
			return authenticated;
		} catch (IllegalArgumentException e) {
			throw new BadCredentialsException(e.getMessage());
		} catch (DataAccessException e) {
			throw new AuthenticationServiceException(e.getMessage(), e);
		}
	}

	private Member login(
		String principal,
		String credentials
	) {
		Member member = memberRepository.findByLoginId(principal)
			.orElseThrow(LoginFailException::new);
		member.checkPassword(passwordEncoder, credentials);

		return member;
	}

	private String getToken(String username, List<GrantedAuthority> authorities) {
		String[] roles = authorities.stream()
			.map(GrantedAuthority::getAuthority)
			.toArray(String[]::new);
		return jwt.sign(Jwt.Claims.from(username, roles));
	}

}
