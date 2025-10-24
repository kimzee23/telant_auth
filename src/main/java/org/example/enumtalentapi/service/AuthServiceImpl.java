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

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepo;
    private final VerificationTokenRepository tokenRepo;
    private final PasswordEncoder encoder;

    @Override
    public String signup(SignupRequest request) {
        Optional<User> existingUser = userRepo.findByEmail(request.getEmail());

        // Check if email already exists
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            if (user.isVerified()) {
                throw new CustomException("EMAIL_IN_USE_GUY");
            } else {
                tokenRepo.deleteByUser(user);
                VerificationToken newToken = createVerificationToken(user);
                return "Signup successful. Verify using token=" + newToken.getToken();
            }
        }


        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setPassword(encoder.encode(request.getPassword()));
        newUser.setVerified(false);
        userRepo.save(newUser);

        VerificationToken token = createVerificationToken(newUser);
        return "Signup successful. Verify using token=" + token.getToken();
    }

    private VerificationToken createVerificationToken(User user) {
        VerificationToken token = new VerificationToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        token.setUsed(false);
        token.setExpiresAt(LocalDateTime.now().plusHours(24));
        return tokenRepo.save(token);
    }

    @Override
    public String login(LoginRequest request) {
        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException("INVALID_CREDENTIALS"));

        if (!user.isVerified())
            throw new CustomException("EMAIL_NOT_VERIFIED");

        boolean matches = encoder.matches(request.getPassword(), user.getPassword());
        if (!matches)
            throw new CustomException("INVALID_CREDENTIALS");
        VerificationToken newToken = createVerificationToken(user);
        return "LOGIN_SUCCESS " +
                " token=" + newToken.getToken();
    }

    @Override
    public String verifyEmail(String tokenStr) {
        VerificationToken token = tokenRepo.findByToken(tokenStr);
        if (token == null)
            throw new CustomException("TOKEN_INVALID");

        if (token.isUsed())
            throw new CustomException("TOKEN_ALREADY_USED");

        if (token.getExpiresAt().isBefore(LocalDateTime.now()))
            throw new CustomException("TOKEN_EXPIRED");

        User user = token.getUser();
        user.setVerified(true);
        token.setUsed(true);

        tokenRepo.save(token);
        userRepo.save(user);

        return "EMAIL_VERIFIED";
    }
}
