# Tourist Guide  

## Project Overview  
The **RAF Tourist Guide** is an online platform that allows users to explore and discover travel destinations worldwide.  
The system is divided into two main parts:  

1. **Content Management System (CMS)** – requires authentication and provides role-based access:  
   - **Content Editor** – can create, edit, view, and delete tourist articles.  
   - **Administrator** – has all editor privileges, plus user management (adding, editing, activating/deactivating users).  

2. **Public Tourist Guide Platform** – open to all visitors, providing access to articles and destinations:  
   - Browse published articles by **destination** or **activity**  
   - View **most popular articles** in the last 30 days  
   - Read full articles with author details, publication date, activities, and comments  
   - Leave and view comments on articles  
   - Track article visits and popularity  

---

## Key Features  

### CMS (Admin & Editor)  
- Authentication & Authorization (hashed passwords, role-based access)  
- Destination Management (create, edit, delete destinations)  
- Article Management (create, edit, delete articles, assign to destinations and activities)  
- User Management (Admin only) – add, edit, activate/deactivate users  

### Public Platform  
- Browse all published articles  
- Filter articles by destination or activity (e.g., hiking, skiing, swimming)  
- View most-read articles in the last 30 days  
- Article page with full text, author, date, activities, and comments  
- Comment system (reader’s name, text, timestamp)  
- Incremental visit counter  
- Pagination for all lists and tables  

---

## Data Model  
- **User** – email, name, surname, role (editor/admin), status, hashed password  
- **Destination** – name, description  
- **Article** – title, text, creation date, visits, author, activities, comments, destination  
- **Activity** – keywords describing tourist activities  
- **Comment** – author name, text, creation date  

---

## Technology Stack  
- **Backend**: JAX-RS or SparkJava  
- **Frontend**: Vue.js / React / Angular / jQuery (with optional CSS framework)  
- **Database**: Relational Database (support for persistence, validation, and error handling)  
- **Authentication**: Cookies or JWT (role & status-based access control)  

---

## Learning Outcomes  
This project provided hands-on experience in:  
- Building full-stack web applications (frontend + backend integration)  
- Implementing authentication and authorization mechanisms  
- Designing relational data models and managing persistence  
- Developing CMS functionality with role-based access  
- Handling validation, error management, and pagination  
- Creating an interactive public-facing platform with filtering and commenting  

---

🚀 Developed as part of the **Web Programming** course at RAF.  
