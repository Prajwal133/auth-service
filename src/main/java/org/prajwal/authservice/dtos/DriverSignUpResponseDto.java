package org.prajwal.authservice.dtos;

import lombok.*;
import org.prajwal.authservice.models.Driver;

import java.util.Date;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverSignUpResponseDto {
    private  Long id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String licenseNumber;
    private String vehicleNumber;
    private Date createdAt;

    public static  DriverSignUpResponseDto from (Driver driver) {
        return DriverSignUpResponseDto.builder()
                .id(driver.getId())
                .name(driver.getName())
                .email(driver.getEmail())
                .phoneNumber(driver.getPhoneNumber())
                .licenseNumber(driver.getLicenseNumber())
                .vehicleNumber(driver.getVehicleNumber())
                .createdAt(driver.getCreatedAt())
                .build();
    }
}
