package org.example.enumtalentapi.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.ArrayList;
import java.util.List;
@Document(collation = "users_profile")
@Getter
@Setter
public class TalentProfile {
    @Id
    private String id;

    private User user;

    private String transcript;
    private String statementOfPurpose;

    public int calculateCompleteness() {
        int total = 2;
        int filled = 0;
        if (transcript != null && !transcript.isEmpty()) filled++;
        if (statementOfPurpose != null && !statementOfPurpose.isEmpty()) filled++;
        return (filled * 100)/total;
    }

    public List<String> missingFields() {
        List<String> missing = new ArrayList<>();
        if (transcript == null || transcript.isEmpty()) missing.add("transcript");
        if (statementOfPurpose == null || statementOfPurpose.isEmpty()) missing.add("statementOfPurpose");
        return missing;
    }
}
