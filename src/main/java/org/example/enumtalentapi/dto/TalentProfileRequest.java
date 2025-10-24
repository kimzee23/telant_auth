package org.example.enumtalentapi.dto;

import lombok.Data;
import java.util.List;

@Data
public class TalentProfileRequest {
    private String firstName;
    private String lastName;
    private String phone;
    private String location;
    private String profilePicture;

    private String bio;
    private String headline;
    private List<String> skills;
    private String experienceLevel;
    private String currentPosition;
    private String company;

    private String highestDegree;
    private String institution;
    private String fieldOfStudy;
    private Integer graduationYear;

    private String transcript;
    private String statementOfPurpose;
    private String resumeUrl;
    private String portfolioUrl;

    private List<String> preferredRoles;
    private String workMode;
    private String salaryExpectation;
    private String locationPreference;
}