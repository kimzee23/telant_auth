package org.example.enumtalentapi.service;

import org.example.enumtalentapi.dto.LoginRequest;
import org.example.enumtalentapi.dto.SignupRequest;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    String signup(SignupRequest request);
    String login(LoginRequest request);
    String verifyEmail(String tokenStr);
}
