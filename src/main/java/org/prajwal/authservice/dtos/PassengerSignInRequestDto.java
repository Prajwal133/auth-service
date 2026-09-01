package org.prajwal.authservice.dtos;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@Builder
public class PassengerSignInRequestDto {
    private String email;
    private  String password;
}
