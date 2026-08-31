package org.prajwal.authservice.dtos;

import lombok.*;
import org.prajwal.authservice.models.Passenger;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PassengerSignUpResponseDto {
    private Long Id;
    private String name;
    private String email;
    private String phoneNumber;
    private Date createdAt;

    //to convert passenger into PassengerSignUpResponseDto
    public static PassengerSignUpResponseDto from(Passenger passenger) {
        return PassengerSignUpResponseDto.builder()
                .Id(passenger.getId())
                .name(passenger.getName())
                .email(passenger.getEmail())
                .phoneNumber(passenger.getPhoneNumber())
                .createdAt(passenger.getCreatedAt())
                .build();


    }

}

