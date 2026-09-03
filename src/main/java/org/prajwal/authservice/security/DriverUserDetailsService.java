package org.prajwal.authservice.security;

import org.prajwal.authservice.models.Driver;
import org.prajwal.authservice.repositories.DriverRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DriverUserDetailsService implements UserDetailsService {
    private final DriverRepository driverRepository;

    public DriverUserDetailsService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Driver> driver = driverRepository.findDriverByEmail(username);
        if(driver.isPresent()) {
            return new DriverUserDetails(driver.get());
        }else{
            throw new UsernameNotFoundException("User not found with the provided email");
        }
    }
}
