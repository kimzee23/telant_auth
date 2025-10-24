package org.example.enumtalentapi.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    private String id;
    private String email;
    private String password;
    private boolean verified;
    private String token;
    private LocalDateTime createdAt = LocalDateTime.now();
}
