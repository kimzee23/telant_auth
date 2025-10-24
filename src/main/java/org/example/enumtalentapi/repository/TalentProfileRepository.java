package org.example.enumtalentapi.repository;

import org.example.enumtalentapi.entity.TalentProfile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TalentProfileRepository extends MongoRepository<TalentProfile,String> {
    TalentProfile findByUserId(String userId);
}
