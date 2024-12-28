package com.DocMate.util;

import com.DocMate.model.DashboardItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PayloadValidationUtil {

    private static final Logger logger = LoggerFactory.getLogger(PayloadValidationUtil.class);

    private static final String SERVICE_ID_PATTERN = "^[a-z0-9_]+$";
    private static final String STATUS_PATTERN = "^(open|close)$";

    public static List<String> validateService(DashboardItem service) {
        List<String> errors = new ArrayList<>();

        if (service == null) {
            String error = "Service object is null";
            logger.error(error);
            errors.add(error);
            return errors;
        }

        if (service.getServiceId() == null || service.getServiceId().isEmpty()) {
            String error = "Service ID is null or empty";
            logger.error(error);
            errors.add(error);
        } else if (!Pattern.matches(SERVICE_ID_PATTERN, service.getServiceId())) {
            String error = "Invalid serviceId format: " + service.getServiceId();
            logger.error(error);
            errors.add(error);
        }

        if (service.getStatus() == null || !Pattern.matches(STATUS_PATTERN, service.getStatus())) {
            String error = "Invalid or missing status: " + service.getStatus();
            logger.error(error);
            errors.add(error);
        }

        if (service.getServiceName() == null || service.getServiceName().isEmpty()) {
            String error = "Service name is null or empty";
            logger.error(error);
            errors.add(error);
        }

        if (service.getPath() == null || service.getPath().isEmpty()) {
            String error = "Path is null or empty";
            logger.error(error);
            errors.add(error);
        }

        if (service.getIdentifier() == null || 
            (!service.getIdentifier().equals("folder") && !service.getIdentifier().equals("file"))) {
            String error = "Invalid identifier: " + service.getIdentifier();
            logger.error(error);
            errors.add(error);
        }

        if ("file".equals(service.getIdentifier())) {
            if (service.getDocs() == null || service.getDocs().isEmpty()) {
                String error = "Docs cannot be null or empty for identifier 'file'";
                logger.error(error);
                errors.add(error);
            }
        } else if ("folder".equals(service.getIdentifier()) && service.getDocs() != null) {
            String error = "Docs must not be present for identifier 'folder'";
            logger.error(error);
            errors.add(error);
        }

        if (service.getThumbnail() == null || service.getThumbnail().isEmpty()) {
            String error = "Thumbnail is null or empty";
            logger.error(error);
            errors.add(error);
        }

        return errors;
    }
}
