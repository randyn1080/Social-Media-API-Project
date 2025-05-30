# Social Media Blog API

## Project Description

A RESTful API backend for a social media platform that enables users to manage accounts and share messages. The application provides comprehensive user authentication, message management, and social interaction capabilities without a frontend interface. Built using fundamental Java technologies with raw JDBC implementation to demonstrate core backend development principles and database interaction patterns.

## Technologies Used

* Java - version 11
* Javalin - version 5.0.1
* H2 Database - version 2.1.214
* JDBC - version 4.2
* Maven - version 3.8
* Jackson - version 2.14.0
* JUnit - version 4.13.2
* SLF4J - version 1.7.36

## Features

List of features ready and TODOs for future development
* User account registration with validation
* User authentication and login system
* Create, read, update, and delete messages
* Retrieve all messages from all users
* Get specific messages by message ID
* Retrieve all messages from a specific user
* Input validation and error handling
* RESTful API design with proper HTTP status codes

To-do list:
* Add JWT token authentication
* Implement message pagination
* Add user profile management
* Implement message reactions/likes
* Add message filtering and search capabilities
* Implement user following/follower system

## Getting Started

### Prerequisites
* Java 11 or higher
* Maven 3.6 or higher (for command line build)
* Git
* IntelliJ IDEA (recommended) or any Java IDE

### Clone the repository
git clone https://github.com/randyn1080/Social-Media-API-Project.git

cd Social-Media-API-Project

### Option 1: Run with IntelliJ IDEA (Recommended)
1. Open IntelliJ IDEA
2. File → Open → Select the cloned project folder
3. Wait for Maven import to complete (IntelliJ will automatically download dependencies)
4. Navigate to `src/main/java/Main.java`
5. Right-click on `Main.java` → Run 'Main.main()'
6. Application will start on http://localhost:8080

### Option 2: Command Line (Windows)
mvn clean compile

mvn exec:java -Dexec.mainClass="Main"

### Option 3: Command Line (macOS/Linux/Unix)
mvn clean compile

mvn exec:java -Dexec.mainClass="Main"

> Note: Commands work on all platforms (Windows, macOS, Linux, Unix)
> Maven must be installed and configured in system PATH
> Java must be installed and JAVA_HOME configured

The application will start on http://localhost:8080
Press Ctrl+C in the terminal to stop the application

## Usage

Once the application is running, you can interact with the API using tools like Postman, curl, or any HTTP client.

### API Endpoints

**User Registration**
POST /register
Content-Type: application/json

{
"username": "your_username",
"password": "your_password"
}

**User Login**
POST /login
Content-Type: application/json

{
"username": "your_username",
"password": "your_password"
}

**Create Message**
POST /messages
Content-Type: application/json

{
"posted_by": 1,
"message_text": "Your message here",
"time_posted_epoch": 1669947792
}

**Get All Messages**
GET /messages

**Get Message by ID**
GET /messages/{message_id}

**Update Message**
PATCH /messages/{message_id}
Content-Type: application/json

{
"message_text": "Updated message text"
}

**Delete Message**
DELETE /messages/{message_id}

**Get Messages by User**
GET /accounts/{account_id}/messages

### Example Usage with curl

Test user registration:
curl -X POST http://localhost:8080/register -H "Content-Type: application/json" -d "{\"username\":\"testuser\",\"password\":\"password123\"}"

Test getting all messages:
curl http://localhost:8080/messages

## Contributors

Randy Nava
Revature

## License

This project uses the following license: MIT License.