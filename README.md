# Electronic Device Repair Management System Backend

## Student Information
- **Name:** Hatunguramye Elichadai
- **Student ID:** 27004
- **Institution:** Adventist University of Central Africa (AUCA)
- **Course:** INSY 8322 – Web Technology & Internet

## Project Description
The Electronic Device Repair Management System Backend is a REST API developed using Spring Boot. It manages customers, technicians, workbenches, device types, repair orders, repair statuses, and spare parts.

## Technologies Used
- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven
- Bruno

## Prerequisites
- Java 21
- Maven
- PostgreSQL
- Git

## Steps to Run the API

### 1. Clone the repository
```bash
git clone https://github.com/Elichadai25/electronic-device-repair-management-system-backend.git
```

### 2. Enter the project directory
```bash
cd electronic-device-repair-management-system-backend
```

### 3. Create the database
Create a PostgreSQL database named `electronic_device_repair_db`.

```sql
CREATE DATABASE electronic_device_repair_db;
```

### 4. Configure the database
Configure the PostgreSQL URL, username, and password in `src/main/resources/application.properties`. Use your own local database credentials. Do not commit real passwords to the public repository.

### 5. Start the API
```bash
mvn spring-boot:run
```

### 6. Test the API
Use Postman to send requests to the endpoints implemented in the project. The default local server address is `http://localhost:8081`.

## Postman Collection
The Postman collection will be available in the root directory as:

`electronic-device-repair-management-system-midterm.postman_collection.json`

## Author
**Hatunguramye Elichadai**  
Student ID: 27004  
Adventist University of Central Africa (AUCA)
