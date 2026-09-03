package org.prajwal.authservice.controllers;


import jakarta.servlet.http.HttpServletResponse;
import org.prajwal.authservice.dtos.*;
import org.prajwal.authservice.services.DriverAuthService;
import org.prajwal.authservice.services.PassengerAuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final PassengerAuthService passengerAuthService;
    private final DriverAuthService driverAuthService;

    public AuthController(PassengerAuthService passengerAuthService, DriverAuthService driverAuthService) {
        this.passengerAuthService = passengerAuthService;
        this.driverAuthService = driverAuthService;
    }

    @PostMapping("/signup/passenger")
    public ResponseEntity<PassengerSignUpResponseDto> passengerSignUp(@RequestBody PassengerSignUpRequestDto passengerSignUpRequestDto) {
        PassengerSignUpResponseDto passengerSignUpResponseDto = passengerAuthService.passengerSignUp(passengerSignUpRequestDto);
        return ResponseEntity.ok(passengerSignUpResponseDto);
    }

    @PostMapping("/signup/driver")
    public ResponseEntity<DriverSignUpResponseDto> driverSignUp(@RequestBody DriverSignUpRequestDto driverSignUpRequestDto) {
        DriverSignUpResponseDto driverSignUpResponseDto = driverAuthService.driverSignUp(driverSignUpRequestDto);
        return ResponseEntity.ok(driverSignUpResponseDto);
    }

    @PostMapping("/signin/passenger")
    public ResponseEntity<PassengerSignInResponseDto> signIn(@RequestBody SignInRequestDto SignInRequestDto, HttpServletResponse httpServletResponse) {

        PassengerSignInResponseDto passengerSignInResponseDto = passengerAuthService.signIn(SignInRequestDto);
        String token = passengerSignInResponseDto.getToken();
        ResponseCookie cookie = ResponseCookie.from("jwt-token", token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .build();

        /*
        Cookie is provided by the Servlet API,
        while ResponseCookie is Spring's HTTP abstraction.
        Both ultimately represent a Set-Cookie response header,
        but ResponseCookie provides a more fluent API and convenient support for attributes like SameSite."

        Cookie
            → Servlet API
            → simpler/basic cookie object

        ResponseCookie
            → Spring HTTP API
            → more expressive control over HTTP cookie attributes
         */

        /*
        --------ALTERNATE WAY ---------
        Cookie cookie = new Cookie("jwtToken", jwtToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");

        httpServletResponse.addCookie(cookie);
         */


        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(passengerSignInResponseDto);
    }

    @PostMapping("/signin/driver")
    public ResponseEntity<DriverSignInResponseDto> signIn(@RequestBody SignInRequestDto signInRequestDto
    ) {
        DriverSignInResponseDto driverSignInResponseDto = driverAuthService.signIn(signInRequestDto);
        String token = driverSignInResponseDto.getToken();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, token)
                .body(driverSignInResponseDto);

    }
}
