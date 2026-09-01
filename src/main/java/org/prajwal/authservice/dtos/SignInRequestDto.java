package org.prajwal.authservice.dtos;

import lombok.*;
import org.springframework.stereotype.Service;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
/*
sign-in should be unified, not split by role,
because sign-in only ever needs email + password — nothing role-specific.
A driver and a passenger authenticate with the exact same shape of data.
 */
public class SignInRequestDto {
    private String email;
    private String password;
}
