package com.alpha.lainovo.utilities.customUserDetails;

import com.alpha.lainovo.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    private Integer userId;
    private String email;
    private String password;
    private boolean isVerify;
    private List<GrantedAuthority> authorities;

    public CustomUserDetails(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.isVerify = user.getIsVerify();
        this.authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
