package org.prajwal.authservice.dtos;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PassengerSignInResponseDto {

    private String token;
    private Long passengerId;
    private String name;
    private String email;

    public PassengerSignInResponseDto(String token) {
        this.token = token;
    }

}
