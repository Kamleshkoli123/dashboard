package com.DocMate.controller;

import com.DocMate.service.DashboardService;
import com.DocMate.service.JwtService;
import com.DocMate.model.DashboardItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);
    
    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/get")
    public ResponseEntity<?> getDashboardItems(@RequestHeader("Authorization") String token, @RequestParam("path") String path) {
        logger.info("Received request for dashboard items with path: {} and token: {}", path, token);

        if (!jwtService.validateToken(token)) {
            logger.warn("Invalid JWT token received.");
            return ResponseEntity.status(401).body("Unauthorized");
        }

        try {
            List<DashboardItem> items = dashboardService.fetchItemsByPath(path);
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            logger.error("Error fetching dashboard items", e);
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }
    
    @GetMapping("/test")
    public ResponseEntity<?> test() {
        logger.info("Received request for test");

        return ResponseEntity.status(200).body("test success");
    }
}
