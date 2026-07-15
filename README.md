# 💰 Personal Expense Tracker Backend

A RESTful backend application built with **Spring Boot** that helps users manage their personal expenses. It provides secure authentication and APIs to create, update, delete, and track expenses efficiently.

## 🚀 Features

- 🔐 User Registration & Login
- 🔑 JWT Authentication
- ➕ Add New Expenses
- ✏️ Update Existing Expenses
- ❌ Delete Expenses
- 📋 View All Expenses
- 🔍 Get Expense by ID
- 💾 MySQL Database Integration
- ✅ Input Validation
- ⚡ RESTful API Architecture

## 🛠️ Tech Stack

- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- JWT Authentication
- Lombok

## 📂 Project Structure

```
src
 ├── controller
 ├── service
 ├── repository
 ├── entity
 ├── dto
 ├── config
 ├── security
 ├── exception
 └── resources
```

## ⚙️ Prerequisites

- Java 17+
- Maven
- MySQL Server
- IntelliJ IDEA / Eclipse

## 🔧 Installation

1. Clone the repository

```bash
git clone https://github.com/vasanthakumar-m3/Personal-Expense-Tracker-Backend.git
```

2. Navigate to the project

```bash
cd Personal-Expense-Tracker-Backend
```

3. Configure the database in `application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/expense_tracker
spring.datasource.username=your_username
spring.datasource.password=your_password
```

4. Build the project

```bash
mvn clean install
```

5. Run the application

```bash
mvn spring-boot:run
```

The server will start at:

```
http://localhost:8080
```

## 📌 API Endpoints

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | /api/auth/register | Register User |
| POST | /api/auth/login | Login User |
| GET | /api/expenses | Get All Expenses |
| GET | /api/expenses/{id} | Get Expense By ID |
| POST | /api/expenses | Add Expense |
| PUT | /api/expenses/{id} | Update Expense |
| DELETE | /api/expenses/{id} | Delete Expense |

> **Note:** Protected APIs require a valid JWT token.

## 🗄️ Database

Database: **MySQL**

Main Table:

- Users
- Expenses

## 📷 Future Enhancements

- Expense Categories
- Monthly Reports
- Dashboard Analytics
- Export to PDF/Excel
- Email Notifications

## 👨‍💻 Author

**Vasanth Kumar M**

- GitHub: https://github.com/vasanthakumar-m3

## 📄 License

This project is developed for learning and portfolio purposes.
