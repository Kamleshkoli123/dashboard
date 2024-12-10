package com.DocMate.repository;

import com.DocMate.model.DashboardItem;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

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

            for (Document doc : documents) {
                DashboardItem item = new DashboardItem();
                item.setId(doc.getObjectId("_id").toString());
                item.setPath(doc.getString("path"));
                item.setServiceName(doc.getString("serviceName"));
                item.setIdentifier(doc.getString("identifier")); // Fetching 'identifier'
                item.setDocs(doc.getList("docs", String.class)); // Fetching 'docs' as a list of strings
                item.setThumbnail(doc.getString("thumbnail")); // Fetching 'thumbnail'
                dashboardItems.add(item);
            }
            logger.info("Fetched {} items for path: {}", dashboardItems.size(), path);
        } catch (Exception e) {
            logger.error("Error fetching dashboard items for path: {}", path, e);
        }
        return dashboardItems;
    }
}
