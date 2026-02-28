# 🚀 INVENTRA  
### Smart Inventory & Billing Management System

INVENTRA is a real-world Inventory & Billing Management System built using Java, JDBC, and MySQL.  
It simulates how retail stores manage products, track sales, handle billing, and monitor revenue — all through a structured backend system.

This project demonstrates practical backend development concepts including authentication, database design, revenue analytics, and stock automation.

---

## 🎯 What This Project Solves

- Manages product inventory efficiently
- Prevents stock mismatches
- Automates billing workflow
- Tracks revenue and sales performance
- Provides business insights like best-selling products

---

## 🛠 Tech Stack

- ☕ Java (Core Java)
- 🗄 MySQL
- 🔌 JDBC
- 🔐 jBCrypt (Secure Password Hashing)
- 📁 File Handling (Invoice Generation)
- 🧱 DAO Pattern Architecture

---

## 👥 System Roles

---

## 🔐 Owner Panel – Business Control Center

### 🔒 Secure Authentication
- Encrypted password storage using BCrypt
- Secure login validation
- No plain-text passwords stored

### 📦 Complete Product Management
- Add new products
- Update price, category, and stock
- Delete products
- View all inventory items

### 📊 Sales & Revenue Insights
- Monthly revenue calculation
- Identify best-selling products
- Detect low stock items with threshold alerts
- Monitor total sales performance

### 🔄 Smart Stock Handling
- Automatic stock deduction after each purchase
- Manual stock update functionality

---

## 🛒 Customer Panel – Seamless Shopping Flow

### 🛍 Product Browsing
- View all available products
- Filter products by category

### 🧺 Cart System
- Add items to cart
- Quantity validation
- Prevents purchasing more than available stock
- Updates cart dynamically if item already exists

### 🧾 Automated Billing System
- Generates unique invoice number
- Stores order details in database
- Records individual order items
- Updates stock automatically
- Generates invoice file in `.txt` format

---

## 🧾 Sample Invoice

```
===== INVENTRA BILL =====
Invoice Number: INV-1772253293005
Date: 2026-02-28T10:04:53

Paneer | Qty: 1 | Price: 100.0 | Total: 100.0
Curd   | Qty: 4 | Price: 30.0  | Total: 120.0

----------------------------
Grand Total: 220.0
```

---

## 🗄 Database Design Overview

The system uses a relational database structure with:

- **owner** – Secure credential storage
- **products** – Product details & stock tracking
- **orders** – Invoice and total billing records
- **order_items** – Detailed order breakdown

This structure ensures proper normalization and real-world billing flow simulation.

---

## 🏗 Architecture

The project follows a clean layered architecture:

Main  
→ Service Layer (Business Logic)  
→ DAO Layer (Database Operations)  
→ MySQL Database  

This ensures separation of concerns and maintainable code structure.

---

## ⭐ Key Highlights

- Secure authentication using industry-standard hashing
- Real-time stock management
- Revenue analytics using SQL aggregation
- Automatic invoice generation
- Business-level reporting features
- Practical implementation of DAO pattern
- Designed to simulate real retail backend system

---

