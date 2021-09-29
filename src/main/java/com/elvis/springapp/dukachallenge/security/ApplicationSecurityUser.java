package com.elvis.springapp.dukachallenge.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;


@AllArgsConstructor
@Builder
public class ApplicationSecurityUser implements UserDetails {

    private final Set<? extends GrantedAuthority> grantedAuthorities;
    @Getter
    private final UUID id;
    private final String username;
    @Getter
    private final String firstName;
    @Getter
    private final String lastName;
    @Getter
    private final String email;
    @JsonIgnore
    private final String password;
    private final Boolean isAccountNonExpired;
    private final Boolean isAccountNonLocked;
    private final Boolean isCredentialsNonExpired;
    private final Boolean isEnabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
}
