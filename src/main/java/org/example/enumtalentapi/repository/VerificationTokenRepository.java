package org.example.enumtalentapi.repository;

import org.example.enumtalentapi.entity.User;
import org.example.enumtalentapi.entity.VerificationToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends MongoRepository<VerificationToken,String> {
    VerificationToken findByToken(String token);

    void deleteByUser(User user);
}
