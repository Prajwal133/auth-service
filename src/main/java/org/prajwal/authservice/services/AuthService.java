package org.prajwal.authservice.services;

import org.prajwal.authservice.dtos.PassengerSignUpRequestDto;
import org.prajwal.authservice.dtos.PassengerSignUpResponseDto;
import org.prajwal.authservice.dtos.PassengerSignInRequestDto;
import org.prajwal.authservice.dtos.PassengerSignInResponseDto;
import org.prajwal.authservice.models.Passenger;
import org.prajwal.authservice.repositories.PassengerRepository;
import org.prajwal.authservice.security.JwtService;
import org.prajwal.authservice.security.PassengerUserDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    private final PassengerRepository passengerRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(PassengerRepository passengerRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService) {
        this.passengerRepository = passengerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public PassengerSignUpResponseDto signUp(PassengerSignUpRequestDto passengerSignUpRequestDto) {
        Passenger passenger = new Passenger().builder()
                .name(passengerSignUpRequestDto.getName())
                .email(passengerSignUpRequestDto.getEmail())
                .password(bCryptPasswordEncoder.encode(passengerSignUpRequestDto.getPassword()))
                .phoneNumber(passengerSignUpRequestDto.getPhoneNumber())
                .build();

        Passenger savedPassenger = passengerRepository.save(passenger);


        return PassengerSignUpResponseDto.from(savedPassenger);
    }

    /*
    orchestrates: builds auth request → calls Spring Security → generates JWT
     */
    public PassengerSignInResponseDto signIn(PassengerSignInRequestDto passengerSignInRequestDto) {
        /*
        You build an unproven claim (UsernamePasswordAuthenticationToken as input) → hand it to authenticationManager,
        which internally uses UserDetailsService + PasswordEncoder to check it
        → get back a proven Authentication object, which wraps a UserDetails (your PassengerUserDetails) as its principal
        → you pull the real entity out of that principal, plus roles, and use it to build your own JWT.

         */

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        passengerSignInRequestDto.getEmail(),
                        passengerSignInRequestDto.getPassword()
                )
        );

        PassengerUserDetails principal = (PassengerUserDetails) authentication.getPrincipal();

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "ROLE_PASSENGER");

        String jwtToken = jwtService.createToken(authentication.getName(), claims);


        return PassengerSignInResponseDto.builder()
                .token(jwtToken)
                .passengerId(principal.getPassenger().getId())
                .name(principal.getPassenger().getName())
                .email(authentication.getName())
                .build();
    }
}