package org.example.enumtalentapi.service;

import lombok.AllArgsConstructor;
import org.example.enumtalentapi.dto.LoginRequest;
import org.example.enumtalentapi.dto.SignupRequest;
import org.example.enumtalentapi.entity.User;
import org.example.enumtalentapi.entity.VerificationToken;
import org.example.enumtalentapi.exception.CustomException;
import org.example.enumtalentapi.repository.UserRepository;
import org.example.enumtalentapi.repository.VerificationTokenRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.UUID;

@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepo;
    private final VerificationTokenRepository tokenRepo;
    private final PasswordEncoder encoder;

    public String signup(SignupRequest request){
        userRepo.findByEmail(request.getEmail()).ifPresent(u -> {
            if(u.isVerified()) throw new CustomException("EMAIL_IN_USE");
            else resendToken(u);
        });

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setVerified(false);
        userRepo.save(user);

        VerificationToken token = new VerificationToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        tokenRepo.save(token);

        return "SIGNUP_SUCCESS, VERIFY EMAIL";
    }

    public String login(LoginRequest request){
        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(()->new CustomException("INVALID_CREDENTIALS"));
        if(!user.isVerified()) throw new CustomException("EMAIL_NOT_VERIFIED");

        boolean matches = encoder.matches(request.getPassword(),user.getPassword());
        if(!matches) throw new CustomException("INVALID_CREDENTIALS");

        return "LOGIN_SUCCESS";
    }

    public String verifyEmail(String tokenStr){
        VerificationToken token = tokenRepo.findByToken(tokenStr);
        if(token==null) throw new CustomException("TOKEN_INVALID");
        if(token.isUsed()) throw new CustomException("TOKEN_ALREADY_USED");
        if(token.getExpiresAt().isBefore(java.time.LocalDateTime.now()))
            throw new CustomException("TOKEN_EXPIRED");

        User user = token.getUser();
        user.setVerified(true);
        token.setUsed(true);
        tokenRepo.save(token);
        userRepo.save(user);

        return "EMAIL_VERIFIED";
    }

    private void resendToken(User user){
        VerificationToken token = new VerificationToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        tokenRepo.save(token);
        throw new CustomException("VERIFICATION_RESENT");
    }
}
