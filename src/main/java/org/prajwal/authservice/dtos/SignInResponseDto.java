package org.prajwal.authservice.dtos;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignInResponseDto {
    private String accessToken;
    private Long userId;
    private String role; // this is what tells the client "you're a passenger" or "you're a driver" — the differentiation happens in the RESPONSE, not the request

}
