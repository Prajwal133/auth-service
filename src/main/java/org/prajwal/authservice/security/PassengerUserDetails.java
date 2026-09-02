package org.prajwal.authservice.security;

import lombok.Getter;
import lombok.NonNull;
import org.antlr.v4.runtime.misc.NotNull;
import org.jspecify.annotations.NullMarked;
import org.prajwal.authservice.models.Passenger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

//	wraps the passenger as something Spring Security understands
// Spring security does authentication on userDetails Object
@Getter
public class PassengerUserDetails implements UserDetails {

    private final Passenger passenger;   // hold the whole entity, don't extend it

    public PassengerUserDetails(Passenger passenger) {
        this.passenger = passenger;
    }

    @Override
    public @NonNull String getUsername() {
        return passenger.getEmail();
    }

    @Override
    public String getPassword() {
        return passenger.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_PASSENGER")); // or List.of()
    }

    // below set of methods are not of much concern
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


}
