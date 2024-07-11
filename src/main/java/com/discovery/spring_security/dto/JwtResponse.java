package com.discovery.spring_security.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class JwtResponse {
    private String jwtToken;

    private String username;
    private List<String> roles;
}
