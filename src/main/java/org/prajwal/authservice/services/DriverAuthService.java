package org.prajwal.authservice.services;

import org.prajwal.authservice.dtos.DriverSignInResponseDto;
import org.prajwal.authservice.dtos.DriverSignUpRequestDto;
import org.prajwal.authservice.dtos.DriverSignUpResponseDto;
import org.prajwal.authservice.dtos.SignInRequestDto;
import org.prajwal.authservice.models.Driver;
import org.prajwal.authservice.repositories.DriverRepository;
import org.prajwal.authservice.security.DriverUserDetails;
import org.prajwal.authservice.security.JwtService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DriverAuthService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final DriverRepository driverRepository;
    private final AuthenticationManager driverAuthenticationManager;
    private final JwtService jwtService;

    public DriverAuthService(BCryptPasswordEncoder bCryptPasswordEncoder,
                             DriverRepository driverRepository,
                             @Qualifier("driverAuthenticationManager") AuthenticationManager driverAuthenticationManager,
                             JwtService jwtService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.driverRepository = driverRepository;
        this.driverAuthenticationManager = driverAuthenticationManager;
        this.jwtService = jwtService;
    }

    public DriverSignUpResponseDto driverSignUp(DriverSignUpRequestDto driverSignUpRequestDto) {
        Driver driver = Driver.builder()
                .name(driverSignUpRequestDto.getName())
                .email(driverSignUpRequestDto.getEmail())
                .password(bCryptPasswordEncoder.encode(driverSignUpRequestDto.getPassword()))
                .phoneNumber(driverSignUpRequestDto.getPhoneNumber())
                .licenseNumber(driverSignUpRequestDto.getLicenseNumber())
                .vehicleNumber(driverSignUpRequestDto.getVehicleNumber())
                .build();
        Driver savedDriver = driverRepository.save(driver);
        return DriverSignUpResponseDto.from(savedDriver);
    }


    public DriverSignInResponseDto signIn(SignInRequestDto signInRequestDto) {
        Authentication authentication = driverAuthenticationManager.authenticate(
               new  UsernamePasswordAuthenticationToken(
                       signInRequestDto.getEmail(),
                       signInRequestDto.getPassword()
               )
        );

        DriverUserDetails principal = (DriverUserDetails) authentication.getPrincipal();
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "ROLE_DRIVER");

        String jwtToken = jwtService.createToken(authentication.getName(),claims);
        return DriverSignInResponseDto.builder()
                .driverId(principal.getDriver().getId())
                .name(principal.getDriver().getName())
                .email(authentication.getName())
                .phoneNumber(principal.getDriver().getPhoneNumber())
                .token(jwtToken)
                .build();
    }
}
