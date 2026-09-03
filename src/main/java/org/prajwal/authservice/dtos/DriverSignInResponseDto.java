package org.prajwal.authservice.dtos;

import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DriverSignInResponseDto {
    private String token;
    private Long driverId;
    private String name;
    private String email;
    private String phoneNumber;
    // in future we can add this
    // private Boolean isVerified;

    public DriverSignInResponseDto(String token){
        this.token = token;
    }
}
