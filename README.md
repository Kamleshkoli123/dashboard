# Dashboard Service Documentation

## Overview
The Dashboard Service allows users and administrators to manage services dynamically. It supports CRUD operations, JWT-based authentication, and role-specific access. The service is built using Spring Boot and MongoDB.

## API Endpoints

### 1. Get Dashboard Items
- **URL**: `/dashboard/getService`
- **Method**: GET
- **Headers**:
  - `Authorization`: Bearer `<JWT_TOKEN>`
- **Query Parameters**:
  - `path` (String): The path to fetch dashboard items for.
- **Responses**:

| Status Code | Description        |
|-------------|--------------------|
| 200         | List of dashboard items |
| 401         | Unauthorized access |
| 500         | Internal Server Error |

- **Sample Request**:

```http
GET /dashboard/getService?path=adhar
Authorization: Bearer <JWT_TOKEN>
2. Add Dashboard Item
URL: /dashboard/addService
Method: POST
Headers:
Authorization: Bearer <JWT_TOKEN> (Admin Only)
Request Body:
json

{
  "id": "update_adhar_card",
  "path": "adhar",
  "serviceName": "Update Aadhar Card",
  "identifier": "file",
  "docs": ["update_form.pdf"],
  "thumbnail": "thumbnail.jpg",
  "status": "active"
}
Responses:
Status Code	Description
200	Item added successfully
400	Validation errors
401	Unauthorized access
500	Internal Server Error
3. Update Dashboard Item
URL: /dashboard/updateService/{serviceId}
Method: PUT
Headers:
Authorization: Bearer <JWT_TOKEN> (Admin Only)
Request Body:
json

{
  "id": "update_adhar_card",
  "path": "adhar",
  "serviceName": "Update Aadhar Card",
  "identifier": "file",
  "docs": ["update_form_v2.pdf"],
  "thumbnail": "thumbnail_updated.jpg",
  "status": "active"
}
Responses:
Status Code	Description
200	Item updated successfully
400	Validation errors
401	Unauthorized access
500	Internal Server Error
4. Delete Dashboard Item
URL: /dashboard/deleteDashboardItem
Method: DELETE
Headers:
Authorization: Bearer <JWT_TOKEN> (Admin Only)
Query Parameters:
dashboardId (String): ID of the dashboard item to delete.
Responses:
Status Code	Description
200	Item deleted successfully
401	Unauthorized access
403	Access Denied
500	Internal Server Error
Sample Request:
http

DELETE /dashboard/deleteDashboardItem?dashboardId=update_adhar_card
Authorization: Bearer <JWT_TOKEN>
5. Test Endpoint
URL: /dashboard/test
Method: GET
Responses:
Status Code	Description
200	"test success"
Validation Rules
Service ID: Lowercase alphanumeric with underscores (^[a-z0-9_]+$).
Status: Must be either active or inactive.
Required Fields: id, path, serviceName, identifier, thumbnail.

Features
Role-based Access: Admins can perform add, update, and delete operations. Users can only fetch items.
JWT Authentication: Ensures secure communication and access control.
MongoDB Integration: Supports dynamic CRUD operations.
Validation: Strict payload validation for all requests.

Sample Payloads
Add Item

json

{
  "id": "create_pan_card",
  "path": "pan",
  "serviceName": "Create PAN Card",
  "identifier": "file",
  "docs": ["pan_form.pdf"],
  "thumbnail": "pan_thumbnail.jpg",
  "status": "active"
}
Update Item
json

{
  "id": "update_pan_card",
  "path": "pan",
  "serviceName": "Update PAN Card",
  "identifier": "file",
  "docs": ["updated_pan_form.pdf"],
  "thumbnail": "updated_thumbnail.jpg",
  "status": "inactive"
}
