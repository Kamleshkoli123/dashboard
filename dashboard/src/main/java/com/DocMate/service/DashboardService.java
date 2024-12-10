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
}
