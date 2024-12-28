package com.DocMate.repository;

import com.DocMate.model.DashboardItem;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.mongodb.client.model.Filters;  // For Filters like eq

import java.util.ArrayList;
import java.util.List;

@Repository
public class DashboardDao {

    private static final Logger logger = LoggerFactory.getLogger(DashboardDao.class);
    private static MongoDatabase mongoDatabase;

    public DashboardDao(MongoDatabase db) {
        DashboardDao.mongoDatabase = db;
        logger.info("MongoDatabase initialized for Dashboard.");
    }

    
    public List<DashboardItem> getDashboardItemsByPath(String path) {
        List<DashboardItem> dashboardItems = new ArrayList<>();
        try {
            MongoCollection<Document> collection = mongoDatabase.getCollection("dashboard");
            List<Document> documents = collection.find(new Document("path", path)).into(new ArrayList<>());
            logger.info("fetched items for path {}", path);
            for (Document doc : documents) {
                DashboardItem item = new DashboardItem();
                
                if(doc.getString("status").equalsIgnoreCase("close")){
                	continue;
                }
                
                item.setServiceId(doc.getString("serviceId"));
                item.setPath(doc.getString("path"));
                item.setServiceName(doc.getString("serviceName"));
                item.setIdentifier(doc.getString("identifier")); // Fetching 'identifier'
                item.setDocs(doc.getList("docs", String.class)); // Fetching 'docs' as a list of strings
                item.setThumbnail(doc.getString("thumbnail")); // Fetching 'thumbnail'
//                item.setStatus(doc.getString("status"));
                dashboardItems.add(item);
            }
            logger.info("Fetched {} items for path: {}", dashboardItems.size(), path);
        } catch (Exception e) {
            logger.error("Error fetching dashboard items for path: {}", path, e);
        }
        return dashboardItems;
    }
    
    public void addDashboardItem(DashboardItem item) {
        try {
            MongoCollection<Document> collection = mongoDatabase.getCollection("dashboard");

            // Check if a document with the same serviceId already exists
            Document existingDoc = collection.find(Filters.eq("serviceId", item.getServiceId())).first();
            
            if (existingDoc != null) {
                // If a document with the same serviceId exists, throw an error
                logger.error("ServiceId {} already exists.", item.getServiceId());
                throw new RuntimeException("ServiceId already exists: " + item.getServiceId());
            }
            
            Document doc = new Document()
                    .append("serviceId", item.getServiceId()) // Using serviceId as primary key
                    .append("path", item.getPath())
                    .append("serviceName", item.getServiceName())
                    .append("identifier", item.getIdentifier())
                    .append("docs", item.getDocs())
                    .append("thumbnail", item.getThumbnail())
                    .append("status", item.getStatus());
            collection.insertOne(doc);
            logger.info("Dashboard item added to the database: {}", doc.toJson());
        } catch (Exception e) {
            logger.error("Error while adding dashboard item: {}", item, e);
            throw new RuntimeException("Failed to add dashboard item", e);
        }
    }
    
    public DashboardItem getServiceById(String serviceId) {
        try {
        	MongoCollection<Document> collection = mongoDatabase.getCollection("dashboard");
            Document doc = collection.find(Filters.eq("serviceId", serviceId)).first();
            if (doc != null) {
                logger.info("Service found with ID: {}", serviceId);
                return mapDocumentToDashboardItem(doc);
            }
            logger.warn("No service found with ID: {}", serviceId);
        } catch (Exception e) {
            logger.error("Error fetching service with ID: {}", serviceId, e);
        }
        return null;
    }

    public void updateService(DashboardItem service) {
        try {
            Document updatedDoc = mapDashboardItemToDocument(service);
            MongoCollection<Document> collection = mongoDatabase.getCollection("dashboard");
            collection.replaceOne(Filters.eq("serviceId", service.getServiceId()), updatedDoc);
            logger.info("Service updated in database with ID: {}", service.getServiceId());
        } catch (Exception e) {
            logger.error("Error updating service with ID: {}", service.getServiceId(), e);
            throw e;
        }
    }

    private DashboardItem mapDocumentToDashboardItem(Document doc) {
        DashboardItem item = new DashboardItem();
        item.setServiceId(doc.getString("serviceId"));
        item.setPath(doc.getString("path"));
        item.setServiceName(doc.getString("serviceName"));
        item.setIdentifier(doc.getString("identifier"));
        item.setDocs(doc.getList("docs", String.class));
        item.setThumbnail(doc.getString("thumbnail"));
        item.setStatus(doc.getString("status"));
        return item;
    }

    private Document mapDashboardItemToDocument(DashboardItem item) {
        return new Document("serviceId", item.getServiceId())
                .append("path", item.getPath())
                .append("serviceName", item.getServiceName())
                .append("identifier", item.getIdentifier())
                .append("docs", item.getDocs())
                .append("thumbnail", item.getThumbnail())
                .append("status", item.getStatus());
    }

}

/*
 * Multiple markers at this line
	- The method eq(String, String) is undefined for the type DashboardDao
	- collection cannot be resolved
 */
