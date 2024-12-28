package com.DocMate.controller;

import com.DocMate.service.DashboardService;
import com.DocMate.service.JwtService;
import com.DocMate.util.PayloadValidationUtil;
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

    @GetMapping("/getService")
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

    @PostMapping("/addService")
    public ResponseEntity<?> addService(@RequestHeader("Authorization") String token, @RequestBody DashboardItem service) {
        logger.info("Received request to add a new service: {}", service);

        try {
            // Validate JWT Token
            if (!jwtService.validateAdminToken(token)) {
                logger.warn("Unauthorized access attempt with token: {}", token);
                return ResponseEntity.status(401).body("Unauthorized: Admin access required.");
            }

            // Validate the service payload
            List<String> validationErrors = PayloadValidationUtil.validateService(service);
            if (!validationErrors.isEmpty()) {
                logger.error("Validation errors found: {}", validationErrors);
                return ResponseEntity.badRequest().body(validationErrors);
            }

            // Add service to the dashboard
            dashboardService.addService(service);
            logger.info("Service added successfully: {}", service.getServiceId());

            return ResponseEntity.ok("Service added successfully.");

        } catch (IllegalArgumentException e) {
            logger.error("Invalid input data for adding service: {}", service, e);
            return ResponseEntity.badRequest().body("Invalid input data: " + e.getMessage());
        } catch (RuntimeException e) {
            logger.error("ServiceId already exists: {}", service.getServiceId(), e);
            return ResponseEntity.badRequest().body("ServiceId already exists: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error occurred while adding service: {}", service, e);
            return ResponseEntity.status(500).body("Internal server error: " + e.getMessage());
        }
    }


    @PutMapping("/updateService/{serviceId}")
    public ResponseEntity<?> updateService(@RequestHeader("Authorization") String token, @RequestBody DashboardItem service) {
        String serviceId = service.getServiceId();
    	logger.info("Received request to update service with ID: {}", serviceId);

        // Validate JWT Token
	    if (!jwtService.validateAdminToken(token)) {
	        logger.warn("Unauthorized access attempt with token: {}", token);
	        return ResponseEntity.status(401).body("Unauthorized: Admin access required.");
	    }
        
        List<String> validationErrors = PayloadValidationUtil.validateService(service);
        if (!validationErrors.isEmpty()) {
            logger.error("Validation errors found: {}", validationErrors);
            return ResponseEntity.badRequest().body(validationErrors);
        }

        try {
            dashboardService.updateService(serviceId, service);
            logger.info("Service updated successfully: {}", serviceId);
            return ResponseEntity.ok("Service updated successfully");
        } catch (Exception e) {
            logger.error("Error updating service with ID: {}", serviceId, e);
            return ResponseEntity.status(500).body("Error updating service");
        }
    }
    
    @GetMapping("/test")
    public ResponseEntity<?> test() {
        logger.info("Received request for test");

        return ResponseEntity.status(200).body("test success");
    }
}
