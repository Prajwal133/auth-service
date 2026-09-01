package org.prajwal.authservice.controllers;


import org.prajwal.authservice.dtos.PassengerSignUpRequestDto;
import org.prajwal.authservice.dtos.PassengerSignUpResponseDto;
import org.prajwal.authservice.dtos.SignInRequestDto;
import org.prajwal.authservice.services.AuthService;
import org.prajwal.authservice.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(
            AuthService authService,
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/signup/passenger")
    public ResponseEntity<PassengerSignUpResponseDto> signUp(@RequestBody PassengerSignUpRequestDto passengerSignUpRequestDto) {
        PassengerSignUpResponseDto passengerSignUpResponseDto = authService.signUp(passengerSignUpRequestDto);
        return new ResponseEntity<>(passengerSignUpResponseDto, HttpStatus.OK);
    }

    @PostMapping("/signin/passenger")
    public ResponseEntity<?> signIn(@RequestBody SignInRequestDto signInRequestDto) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(signInRequestDto.getEmail(), signInRequestDto.getPassword()));

        if (authentication.isAuthenticated()) {

            Map<String, Object> claims = new HashMap<>();
            claims.put("email", signInRequestDto.getEmail());
            String jwtToken = jwtService.createToken(authentication.getName(), claims);
            return new ResponseEntity<>(jwtToken, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Auth Unsuccessful", HttpStatus.UNAUTHORIZED);
        }
    }

}
