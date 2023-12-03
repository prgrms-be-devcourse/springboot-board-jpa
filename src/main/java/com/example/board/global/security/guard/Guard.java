package com.example.board.global.security.guard;

import com.example.board.domain.role.entity.RoleType;
import com.example.board.global.security.util.SecurityUtil;

public abstract class Guard {

    public final boolean check(Long id){
       return SecurityUtil.isAuthenticated() && hasAuthority(id);
    }

    private boolean hasAuthority(Long id){
        return SecurityUtil.extractMemberRolesFromContext().contains(RoleType.ADMIN) || isResourceOwner(id);
    } 

    abstract protected boolean isResourceOwner(Long id);
}
