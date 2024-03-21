package com.wtacademyplatform.backend.controllers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wtacademyplatform.backend.dto.LoginRequestDto;
import com.wtacademyplatform.backend.model.AuthenticationResponse;
import com.wtacademyplatform.backend.services.AuthenticationService;

@RestController
class AuthenticationController {
    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody LoginRequestDto loginRequestDTO
    ) {
        try {
            AuthenticationResponse response = authService.authenticate(loginRequestDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Handle the exception here
            if (e instanceof BadCredentialsException) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthenticationResponse(null,"Combinatie van email en wachtwoord is verkeerd"));
            } else {
                // For other exceptions, return a generic error message
                String errorMessage = "An error occurred during authentication: " + e.getMessage();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AuthenticationResponse(null, errorMessage));
            }
        }
    }
}
