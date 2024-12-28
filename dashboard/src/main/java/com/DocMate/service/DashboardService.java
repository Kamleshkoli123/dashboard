package com.DocMate.service;

import com.DocMate.model.DashboardItem;
import com.DocMate.repository.DashboardDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    private static final Logger logger = LoggerFactory.getLogger(DashboardService.class);
    private final DashboardDao dashboardRepository;

    public DashboardService(DashboardDao dashboardRepository) {
        this.dashboardRepository = dashboardRepository;
    }

    public List<DashboardItem> fetchItemsByPath(String path) {
        logger.info("Fetching items for path: {}", path);
        return dashboardRepository.getDashboardItemsByPath(path);
    }
    
    public void addService(DashboardItem service) {
        try {
            logger.info("Adding new service: {}", service);
            dashboardRepository.addDashboardItem(service);
            logger.info("Service added successfully: {}", service.getServiceId());
        } catch (Exception e) {
            logger.error("Error while adding service: {}", service, e);
            throw new RuntimeException("Failed to add service", e);
        }
    }
    
    public void updateService(String serviceId, DashboardItem service) {
        logger.info("Attempting to update service with ID: {}", serviceId);

        if (!serviceId.equals(service.getServiceId())) {
            logger.error("Mismatch between path variable ID and service ID");
            throw new IllegalArgumentException("Service ID mismatch");
        }

        if (dashboardRepository.getServiceById(serviceId) == null) {
            logger.error("Service with ID: {} not found in database", serviceId);
            throw new IllegalArgumentException("Service not found");
        }

        dashboardRepository.updateService(service);
        logger.info("Service successfully updated with ID: {}", serviceId);
    }
}
