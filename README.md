# 📚 Library Management System  

A full-stack **Library Management System** built with **Spring Boot, Thymeleaf, and Spring Security**.  
This application helps manage books, users, and issued/returned records with proper authentication and role-based access control.  

---

## ✨ Features  

### 👩‍💼 Admin  
- Manage books (Add, Edit, Delete, View).  
- Manage users (Add, Edit role, Delete, View).  
- Issue and return books to users.  
- Track all issued books with due dates and fines.  

### 👤 User  
- Secure login and role-based dashboard.  
- View personal issued books.  
- Return books directly from dashboard.  
- Issue new books from available stock.  

---

## 🛠️ Tech Stack  

- **Backend:** Spring Boot, Spring Data JPA, Spring Security  
- **Frontend:** Thymeleaf, HTML5, CSS3  
- **Database:** MySQL  
- **Build Tool:** Maven  
- **Language:** Java 17+  

---

## 🗂️ Database Schema (Simplified)  

### Book  
- `id` (PK)  
- `title`, `author`, `isbn`, `genre`, `short_description`  
- `total_stock`, `available_stock`  

### User  
- `id` (PK)  
- `name`, `email`, `password`, `role`  

### IssuedBook  
- `id` (PK)  
- `user_id` (FK), `book_id` (FK)  
- `issue_date`, `due_date`, `return_date`, `fine`, `status`  

---

## 🚀 Getting Started  

### Prerequisites  
- Java 17+  
- Maven  
- MySQL  

### Setup  

1. **Clone the repository**  
   ```bash
   git clone https://github.com/your-username/library-management-system.git
   cd library-management-system
