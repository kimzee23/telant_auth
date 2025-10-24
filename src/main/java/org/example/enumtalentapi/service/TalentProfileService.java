package org.example.enumtalentapi.service;

import lombok.RequiredArgsConstructor;
import org.example.enumtalentapi.dto.TalentProfileRequest;
import org.example.enumtalentapi.entity.TalentProfile;
import org.example.enumtalentapi.entity.User;
import org.example.enumtalentapi.exception.CustomException;
import org.example.enumtalentapi.repository.TalentProfileRepository;
import org.example.enumtalentapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TalentProfileService {

    private final TalentProfileRepository profileRepository;
    private final UserRepository userRepository;

    public String createOrUpdateProfile(String userId, TalentProfileRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("USER_NOT_FOUND"));

        if (!user.isVerified()) {
            throw new CustomException("EMAIL_NOT_VERIFIED");
        }

        TalentProfile profile = profileRepository.findByUserId(userId)
                .orElseGet(() -> {
                    TalentProfile newProfile = new TalentProfile();
                    newProfile.setUserId(userId);
                    return newProfile;
                });

        if (request.getTranscript() != null && request.getTranscript().length() > 1000) {
            throw new CustomException("Transcript too long, please shorten it.");
        }
        if (request.getStatementOfPurpose() != null && request.getStatementOfPurpose().length() > 2000) {
            throw new CustomException("Statement of Purpose too long, please shorten it.");
        }
        if (request.getBio() != null && request.getBio().length() > 500) {
            throw new CustomException("Bio too long, please shorten it.");
        }


        updateProfileFields(profile, request);

        calculateCompleteness(profile);

        profileRepository.save(profile);

        return "Talent profile updated successfully (" + profile.getCompleteness() + "% complete)";
    }

    private void updateProfileFields(TalentProfile profile, TalentProfileRequest request) {
        profile.setFirstName(request.getFirstName());
        profile.setLastName(request.getLastName());
        profile.setPhone(request.getPhone());
        profile.setLocation(request.getLocation());
        profile.setProfilePicture(request.getProfilePicture());

        profile.setBio(request.getBio());
        profile.setHeadline(request.getHeadline());
        profile.setSkills(request.getSkills());
        profile.setExperienceLevel(request.getExperienceLevel());
        profile.setCurrentPosition(request.getCurrentPosition());
        profile.setCompany(request.getCompany());


        profile.setHighestDegree(request.getHighestDegree());
        profile.setInstitution(request.getInstitution());
        profile.setFieldOfStudy(request.getFieldOfStudy());
        profile.setGraduationYear(request.getGraduationYear());

        profile.setTranscript(request.getTranscript());
        profile.setStatementOfPurpose(request.getStatementOfPurpose());
        profile.setResumeUrl(request.getResumeUrl());
        profile.setPortfolioUrl(request.getPortfolioUrl());

        profile.setPreferredRoles(request.getPreferredRoles());
        profile.setWorkMode(request.getWorkMode());
        profile.setSalaryExpectation(request.getSalaryExpectation());
        profile.setLocationPreference(request.getLocationPreference());
    }

    private void calculateCompleteness(TalentProfile profile) {
        List<String> missing = new ArrayList<>();
        int totalFields = 10;
        int completedFields = 0;

        if (isNotEmpty(profile.getFirstName())) completedFields++;
        else missing.add("firstName");

        if (isNotEmpty(profile.getLastName())) completedFields++;
        else missing.add("lastName");

        if (isNotEmpty(profile.getBio())) completedFields++;
        else missing.add("bio");

        if (isNotEmpty(profile.getHeadline())) completedFields++;
        else missing.add("headline");

        if (profile.getSkills() != null && !profile.getSkills().isEmpty()) completedFields++;
        else missing.add("skills");

        if (isNotEmpty(profile.getExperienceLevel())) completedFields++;
        else missing.add("experienceLevel");

        if (isNotEmpty(profile.getLocation())) completedFields++;
        else missing.add("location");

        if (isNotEmpty(profile.getTranscript())) completedFields++;
        else missing.add("transcript");

        if (isNotEmpty(profile.getStatementOfPurpose())) completedFields++;
        else missing.add("statementOfPurpose");

        if (isNotEmpty(profile.getResumeUrl())) completedFields++;
        else missing.add("resumeUrl");

        int completeness = (int) ((completedFields / (double) totalFields) * 100);
        profile.setCompleteness(completeness);
        profile.setMissingFields(missing);
    }

    private boolean isNotEmpty(String field) {
        return field != null && !field.trim().isEmpty();
    }
}