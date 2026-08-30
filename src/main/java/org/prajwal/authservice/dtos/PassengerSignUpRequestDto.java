package org.prajwal.authservice.dtos;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassengerSignUpRequestDto {
    private String Name;
    private String email;
    private  String password;
    private  String phoneNumber;
}
