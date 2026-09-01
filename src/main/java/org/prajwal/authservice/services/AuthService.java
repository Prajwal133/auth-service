package org.prajwal.authservice.services;

import org.prajwal.authservice.dtos.PassengerSignUpRequestDto;
import org.prajwal.authservice.dtos.PassengerSignUpResponseDto;
import org.prajwal.authservice.dtos.SignInRequestDto;
import org.prajwal.authservice.dtos.SignInResponseDto;
import org.prajwal.authservice.models.Passenger;
import org.prajwal.authservice.respositories.PassengerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final PassengerRepository passengerRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService(PassengerRepository passengerRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.passengerRepository = passengerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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

    public SignInResponseDto signIn(SignInRequestDto signInRequestDto) {
        return  SignInResponseDto.builder().build();
    }
}