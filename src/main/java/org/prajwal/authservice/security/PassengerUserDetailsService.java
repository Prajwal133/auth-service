package org.prajwal.authservice.security;

import lombok.NonNull;
import org.prajwal.authservice.models.Passenger;
import org.prajwal.authservice.repositories.PassengerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
this class is responsible for loading the User in the form of UserDetails object for auth
basically fetches the passenger from DB
------------------------------------------
Spring Security calls this automatically during login
— you never call it yourself in your controller.
 */
@Service
public class PassengerUserDetailsService implements UserDetailsService {

    private final PassengerRepository passengerRepository;

    public PassengerUserDetailsService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }
    @Override
    /*
    "@NonNull indicates that the method should not return null.
    In this case, if the user exists it returns UserDetails, otherwise it throws UsernameNotFoundException."

    "@NonNull is not required here because our  method already either returns a UserDetails or throws an exception.
    I use it to explicitly state that the method must never return null."
     */
    public @NonNull UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Passenger> passenger = passengerRepository.findPassengerByEmail(email);// bcs email is the uniqque identifier
        if(passenger.isPresent()) {
            return new PassengerUserDetails(passenger.get());
        }else{
            throw new UsernameNotFoundException("User not found with the provided email");
        }
    }
}
