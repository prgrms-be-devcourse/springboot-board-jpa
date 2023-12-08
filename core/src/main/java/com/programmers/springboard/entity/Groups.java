package com.programmers.springboard.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
public class Groups {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "groups")
    private List<GroupPermission> groupPermissions = new ArrayList<>();

    public void addGroupPermissions(GroupPermission groupPermission){
        if(!groupPermissions.contains(groupPermission)){
            groupPermissions.add(groupPermission);
        }
    }

    public List<GrantedAuthority> getAuthorities(){
        return groupPermissions
                .stream()
                .map(gp -> new SimpleGrantedAuthority(gp.getPermission().getName()))
                .collect(Collectors.toList());
    }
}
