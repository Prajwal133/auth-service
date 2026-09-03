package org.prajwal.authservice.dtos;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverSignUpRequestDto {
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String licenseNumber;
    private String vehicleNumber;
}
