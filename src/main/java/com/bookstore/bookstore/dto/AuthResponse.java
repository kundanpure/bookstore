package com.bookstore.bookstore.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor // Added missing no-args constructor
public class AuthResponse {
    private String token;
}