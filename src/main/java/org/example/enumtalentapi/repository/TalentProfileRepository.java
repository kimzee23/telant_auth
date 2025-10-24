package org.example.enumtalentapi.repository;

import org.example.enumtalentapi.entity.TalentProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TalentProfileRepository extends MongoRepository<TalentProfile, String> {
    Optional<TalentProfile> findByUserId(String userId);
}
