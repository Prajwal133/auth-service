package org.prajwal.authservice.services;

import org.prajwal.authservice.helpers.AuthPassengerDetails;
import org.prajwal.authservice.models.Passenger;
import org.prajwal.authservice.respositories.PassengerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
this class is responsible for loading the User in the form of UserDetails object for auth
 */
@Service
public class UsrDetailsServiceImpl implements UserDetailsService {
    private final PassengerRepository passengerRepository;

    public UsrDetailsServiceImpl(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Passenger> passenger = passengerRepository.findPassengerByEmail(email);// bcs email is the uniqque identifier
        if(passenger.isPresent()) {
            return new AuthPassengerDetails(passenger.get());
        }else{
            throw new UsernameNotFoundException("User not found with the provided email");
        }
    }
}
