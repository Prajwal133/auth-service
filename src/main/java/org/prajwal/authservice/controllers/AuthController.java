package org.prajwal.authservice.controllers;


import org.prajwal.authservice.dtos.PassengerSignInRequestDto;
import org.prajwal.authservice.dtos.PassengerSignInResponseDto;
import org.prajwal.authservice.dtos.PassengerSignUpRequestDto;
import org.prajwal.authservice.dtos.PassengerSignUpResponseDto;
import org.prajwal.authservice.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup/passenger")
    public ResponseEntity<PassengerSignUpResponseDto> signUp(@RequestBody PassengerSignUpRequestDto passengerSignUpRequestDto) {
        PassengerSignUpResponseDto passengerSignUpResponseDto = authService.signUp(passengerSignUpRequestDto);
        return new ResponseEntity<>(passengerSignUpResponseDto, HttpStatus.OK);
    }

    @PostMapping("/signin/passenger")
    public ResponseEntity<PassengerSignInResponseDto> signIn(@RequestBody PassengerSignInRequestDto  passengerSignInRequestDto) {
        PassengerSignInResponseDto passengerSignInResponseDto = authService.signIn(passengerSignInRequestDto);
        return new ResponseEntity<>(passengerSignInResponseDto, HttpStatus.OK);
    }

}
