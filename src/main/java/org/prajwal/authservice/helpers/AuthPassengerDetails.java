package org.prajwal.authservice.helpers;

import lombok.AllArgsConstructor;
import org.prajwal.authservice.models.Passenger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

// Spring security does authentication on userDetails Objet
public class AuthPassengerDetails extends Passenger implements UserDetails {

    private String userName;
    private String password;

    public AuthPassengerDetails(Passenger passenger){
        this.userName = passenger.getEmail();
        this.password = passenger.getPassword();
    }

    @Override
    public String getUsername() {
        return this.userName;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
        return UserDetails.super.isAccountNonExpired();
    }


}
