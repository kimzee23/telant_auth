package org.example.enumtalentapi.controller;

import lombok.RequiredArgsConstructor;
import org.example.enumtalentapi.dto.ApiResponse;
import org.example.enumtalentapi.dto.TalentProfileRequest;
import org.example.enumtalentapi.exception.CustomException;
import org.example.enumtalentapi.service.TalentProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile/talent")
@RequiredArgsConstructor
public class TalentProfileController {

    private final TalentProfileService profileService;

    @PostMapping("/{userId}")
    public ResponseEntity<ApiResponse> createOrUpdateProfile(
            @PathVariable String userId,
            @RequestBody TalentProfileRequest request
    ) {
        try {
            String message = profileService.createOrUpdateProfile(userId, request);
            return ResponseEntity.ok(new ApiResponse("success", message));
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(new ApiResponse("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse("error", "Unexpected error: " + e.getMessage()));
        }
    }

}
