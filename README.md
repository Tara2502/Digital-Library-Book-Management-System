#  Digital Library Book Management System

## Description
The *Digital Library Book Management System* is a Spring Boot-based application designed to help librarians efficiently manage books in a library. It allows adding, updating, searching, and deleting books while maintaining their availability status.

## Features
-  *Add a Book*: Add a new book with ID, Title, Author, Genre, and Availability Status.
-  *View All Books*: List all books along with their details.
-  *Search a Book*: Find books by their ID or Title.
-  *Update Book Details*: Modify book attributes like title, author, or availability status.
-  *Delete a Book*: Remove a book record from the catalog.
-  *Exit System*: Option to close the application.

## Tech Stack
- *Backend*: Java, Spring Boot
- *Database*: H2 (for in-memory testing)
- *Build Tool*: Maven
- *API Testing*: Postman

## Setup & Installation
1. *Clone the Repository*:
   bash
   git clone [(https://github.com/Tara2502/Digital-Library-Book-Management-System.git)]
   cd Digital-Library-Book-Management-System
   
2. *Configure Database* in application.properties:
   properties
   # H2 Database Configuration
    spring.datasource.url=jdbc:h2:mem:testdb
    spring.datasource.driver-class-name=org.h2.Driver
    spring.datasource.username=sa
    spring.datasource.password=
   
3. *Build & Run the Application*:
   bash
   mvn clean install
   mvn spring-boot:run
   
4. *Access API* via Postman or Browser:
   
   http://localhost:8080/api/books/getAllBooks

##  Constraints
- Book ID must be unique.
- Title & Author cannot be empty.
- Availability status must be Available or Checked Out.

