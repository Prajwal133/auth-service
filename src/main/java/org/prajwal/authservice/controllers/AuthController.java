package org.prajwal.authservice.controllers;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.prajwal.authservice.dtos.PassengerSignUpRequestDto;
import org.prajwal.authservice.dtos.PassengerSignUpResponseDto;
import org.prajwal.authservice.dtos.PassengerSignInRequestDto;
import org.prajwal.authservice.dtos.PassengerSignInResponseDto;
import org.prajwal.authservice.services.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup/passenger")
    public ResponseEntity<PassengerSignUpResponseDto> signUp(@RequestBody PassengerSignUpRequestDto passengerSignUpRequestDto) {
        PassengerSignUpResponseDto passengerSignUpResponseDto = authService.signUp(passengerSignUpRequestDto);
        return ResponseEntity.ok(passengerSignUpResponseDto);
    }

    @PostMapping("/signin/passenger")
    public ResponseEntity<PassengerSignInResponseDto> signIn(@RequestBody PassengerSignInRequestDto passengerSignInRequestDto, HttpServletResponse httpServletResponse) {

        PassengerSignInResponseDto passengerSignInResponseDto = authService.signIn(passengerSignInRequestDto);
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

}
