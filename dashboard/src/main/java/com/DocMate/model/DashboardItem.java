package com.DocMate.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "dashboard")
public class DashboardItem {
    @Id
    private String serviceId;
    private String path;
    private String serviceName;
    private String identifier; // Either 'folder' or 'file'
    private List<String> docs; // Optional, only if identifier = 'file'
    private String thumbnail; // Holds Base64 encoded image data
    private String status;
    
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String id) {
		this.serviceId = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public List<String> getDocs() {
		return docs;
	}
	public void setDocs(List<String> docs) {
		this.docs = docs;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	@Override
	public String toString() {
		return "DashboardItem [id=" + serviceId + ", path=" + path + ", serviceName=" + serviceName + ", identifier="
				+ identifier + ", docs=" + docs + ", thumbnail=" + thumbnail + ", status=" + status + "]";
	}
    
    
}
