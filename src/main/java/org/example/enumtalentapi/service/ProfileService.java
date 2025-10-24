package org.example.enumtalentapi.service;

import org.example.enumtalentapi.dto.ProfileRequest;
import org.example.enumtalentapi.dto.ProfileResponse;
import org.springframework.stereotype.Service;

@Service
public interface ProfileService {
    ProfileResponse createOrUpdate(ProfileRequest request);
    ProfileResponse getProfile();

}
