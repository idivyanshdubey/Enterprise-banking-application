package com.bankingApp.j.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/logs")
@CrossOrigin(origins = "http://localhost:4200")  // Enable CORS for all origins
public class FrontendLogController {

    private static final Logger logger = LoggerFactory.getLogger(FrontendLogController.class);

    @PostMapping
    public void receiveLog(@RequestBody FrontendLog log) {
        switch (log.getLevel().toLowerCase()) {
            case "info"    -> logger.info("[FRONTEND] {}", log.getMessage());
            case "warning" -> logger.warn("[FRONTEND] {}", log.getMessage());
            case "error"   -> logger.error("[FRONTEND] {}", log.getMessage());
            default        -> logger.debug("[FRONTEND] {}", log.getMessage());
        }
    }

    public static class FrontendLog {
        private String level;
        private String message;
        private String timestamp;

        // Getters and setters
        public String getLevel() { return level; }
        public void setLevel(String level) { this.level = level; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public String getTimestamp() { return timestamp; }
        public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
    }
}