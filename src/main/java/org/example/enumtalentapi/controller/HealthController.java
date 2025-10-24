package org.example.enumtalentapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class HealthController {
    @GetMapping("/.well-known/health")
    public Map<String,String> health() {
        return Map.of("status","ok");
    }
}
