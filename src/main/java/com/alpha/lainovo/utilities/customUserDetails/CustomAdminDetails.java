package com.alpha.lainovo.utilities.customUserDetails;

import com.alpha.lainovo.model.Admin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@AllArgsConstructor
public class CustomAdminDetails implements UserDetails {
    private Integer adminId;
    private String accountName;
    private String fullName;
    private String password;
    private String phoneNumber;
    private boolean isBlocked;
    private List<GrantedAuthority> authorities;

    public CustomAdminDetails(Admin admin) {
        this.adminId = admin.getAdminId();
        this.accountName = admin.getAccountName();
        this.fullName = admin.getFullName();
        this.password = admin.getPassword();
        this.phoneNumber = admin.getPhone();
        this.isBlocked = admin.isBlocked();
//        this.authorities = Collections.singletonList(new SimpleGrantedAuthority(admin.getRole().getRoleName()));
        this.authorities = admin.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return accountName;
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
