package com.example.board.global.security.util;

import com.example.board.domain.role.entity.RoleType;
import com.example.board.global.exception.CustomException;
import com.example.board.global.security.details.MemberDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.board.global.exception.ErrorCode.*;

public class SecurityUtil {

    private SecurityUtil() {
    }

    public static Optional<Long> getCurrentUserId(){
        Authentication authentication = getAuthenticationFromContext();
        if(authentication == null){
            return Optional.empty();
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof MemberDetails memberDetails) {
            return Optional.of(Long.valueOf(memberDetails.getId()));
        } else if (principal instanceof String) {
            throw new CustomException(AUTHENTICATION_ENTRY_POINT);
        }
        throw new CustomException(ILLEGAL_AUTHENTICATION);
    }

    public static boolean isAuthenticated(){
        Authentication authentication = getAuthenticationFromContext();
        return authentication.isAuthenticated() && authentication instanceof UsernamePasswordAuthenticationToken;
    }

    public static List<RoleType> extractMemberRolesFromContext() {
        Authentication authentication = getAuthenticationFromContext();
        MemberDetails principal = (MemberDetails) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = principal.getAuthorities();
        return authorities.stream().map(GrantedAuthority::getAuthority).map(RoleType::valueOf).collect(
            Collectors.toList());
    }

    private static Authentication getAuthenticationFromContext() {
        return SecurityContextHolder.getContext()
            .getAuthentication();
    }

    public static Long getCurrentUserIdCheck(){
        return getCurrentUserId()
            .orElseThrow(() -> new CustomException(ACCESS_DENIED));
    }
}
