package org.example.enumtalentapi.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Document(collection = "verification_tokens")
@Getter
@Setter
public class VerificationToken {
    @Id
    private String id;

    private String token;

    private User user;

    private boolean used = false;
    private LocalDateTime expiresAt = LocalDateTime.now().plusHours(24);

    // getters/setters
}
