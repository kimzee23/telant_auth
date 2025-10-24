package org.example.enumtalentapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class ProfileResponse {
    private String email;
    private int completeness;
    private List<String> missingFields;

    // getters/setters
}
