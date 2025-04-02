# P6 - MDD (Monde de Dév)

Welcome to MDD (Monde de Dev), our innovative solution: the next-generation social network dedicated to developers.

Our goal is to connect developers seeking job opportunities by facilitating networking and emphasizing collaboration between people with shared interests.

## Prerequisites

### Front-End

- Angular | Version : 14.1.0
- Angular Material | Version : 14.2.5
- Node.js and npm | Version : 22.11.0

 ### Back-End

 - Java | 17
 - Spring Boot | Version : 3.1.5
 - Maven | Version : (Inherited from Spring Boot parent)
 - Spring Data JPA | Version : (Inherited from Spring Boot parent)
 - SpringDoc Open API | Version : 2.2.0

 ### Database
- Mysql Version : 8.0.33

## Project Structure

The project is divided into two main parts:
- `front/`: Angular frontend application
- `back/`: Spring Boot backend application

## Database Setup

### 1. Create MySQL Database and User

1. Open MySQL Command Line Client or MySQL Workbench

2. Connect as root user:
   ```sql
   mysql -u root -p
   ```
3. Create the database:
   ```sql
   CREATE DATABASE mdd;
   ```
4. Create a new user and grant privileges:
   ```sql
   CREATE USER 'mdd_user'@'localhost' IDENTIFIED BY 'your_password';
   GRANT ALL PRIVILEGES ON mdd_db.* TO 'mdd_user'@'localhost';
   FLUSH PRIVILEGES;
   ```
### 2. Execute SQL Script

1. Navigate to the SQL script location:
   ```bash
   cd back/src/main/resources
   ```
2. Execute the SQL script using MySQL client:
   ```bash
   mysql -u mdd_user -p mdd_db < data.sql
   ```

### Frontend Setup

1. Navigate to the frontend directory:
```bash
cd front
```

2. Install dependencies:
```bash
npm install
```

3. Start the development server:
```bash
ng serve
```
The application will be available at `http://localhost:4200`

### Backend Setup

1. Navigate to the backend directory:
```bash
cd back
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

The backend will be available at `http://localhost:8080`

### API Documentation

- Swagger UI: http://localhost:8080/swagger-ui/index.html
- Generate Javadoc: `mvn javadoc:javadoc`
- Javadoc will be available in `target/site/javadoc`

## Development Notes

- The frontend uses Angular Material for UI components.
- The backend uses JWT for authentication
- The application uses MySQL as the database
- MapStruct is configured for object mapping with Spring component model
- Lombok is used to reduce boilerplate code

## Additional Information

- The project uses Spring Security for authentication and authorization
- JWT tokens are used for secure communication between frontend and backend
- The application follows RESTful API principles
- Swagger UI is available for API documentation and testing