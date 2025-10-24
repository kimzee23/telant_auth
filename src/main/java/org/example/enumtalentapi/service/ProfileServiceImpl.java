package org.example.enumtalentapi.service;

import lombok.AllArgsConstructor;
import org.example.enumtalentapi.dto.ProfileRequest;
import org.example.enumtalentapi.dto.ProfileResponse;
import org.example.enumtalentapi.entity.TalentProfile;
import org.example.enumtalentapi.entity.User;
import org.example.enumtalentapi.repository.TalentProfileRepository;
import org.example.enumtalentapi.repository.UserRepository;

@AllArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final TalentProfileRepository profileRepo;
    private final UserRepository userRepo;


    public ProfileResponse createOrUpdate(ProfileRequest request){
        // For simplicity, assume authenticated user ID = "1"
        User user = userRepo.findById("1").orElseThrow();
        TalentProfile profile = profileRepo.findByUserId(user.getId());
        if(profile==null) profile = new TalentProfile();

        profile.setUser(user);
        profile.setTranscript(request.getTranscript());
        profile.setStatementOfPurpose(request.getStatementOfPurpose());
        profileRepo.save(profile);

        ProfileResponse response = new ProfileResponse();
        response.setEmail(user.getEmail());
        response.setCompleteness(profile.calculateCompleteness());
        response.setMissingFields(profile.missingFields());
        return response;
    }

    public ProfileResponse getProfile(){
        User user = userRepo.findById("1").orElseThrow();
        TalentProfile profile = profileRepo.findByUserId(user.getId());
        ProfileResponse response = new ProfileResponse();
        response.setEmail(user.getEmail());
        if(profile==null) {
            response.setCompleteness(0);
            response.setMissingFields(java.util.List.of("transcript","statementOfPurpose"));
        } else {
            response.setCompleteness(profile.calculateCompleteness());
            response.setMissingFields(profile.missingFields());
        }
        return response;
    }
}
