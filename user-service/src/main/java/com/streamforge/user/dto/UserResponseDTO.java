package com.streamforge.user.dto;

import lombok.*;

import java.util.UUID;

/**
 * DTO for User responses (password excluded for security).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {

    private UUID id;
    private String username;
    private String email;
}
