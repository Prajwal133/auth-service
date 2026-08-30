package org.prajwal.authservice.dtos;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PassengerSignUpResponseDto {
    private String Id;
    private String name;
    private String email;
    private String phoneNumber;
    private Date createdAt;

}

