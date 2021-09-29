package com.elvis.springapp.dukachallenge.security;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum ApplicationSecurityRoles {
    TASKOWNER(Sets.newHashSet(ApplicationSecurityPermissions.TASK_READ, ApplicationSecurityPermissions.TASK_WRITE));

    private Set<ApplicationSecurityPermissions> permissions;

    public Set<SimpleGrantedAuthority> grantedAuthorities(){
        Set<SimpleGrantedAuthority> authorities = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission())).collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return authorities;
    }

}
