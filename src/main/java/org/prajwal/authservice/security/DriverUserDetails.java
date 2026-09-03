package org.prajwal.authservice.security;

import lombok.Getter;
import org.jspecify.annotations.Nullable;
import org.prajwal.authservice.models.Driver;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
@Getter
public class DriverUserDetails implements UserDetails {
    private final Driver driver;

    public DriverUserDetails(Driver driver) {
        this.driver = driver;
    }



    @Override
    public @Nullable String getPassword() {
        return driver.getPassword();
    }

    @Override
    public String getUsername() {
        return driver.getEmail();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_DRIVER"));
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
