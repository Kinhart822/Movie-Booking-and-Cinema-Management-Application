# **Movie Booking and Cinema Management Application**

## **Overview**

The **Movie Booking and Cinema Management Application** is a full-stack project designed to improve the cinema experience. It connects moviegoers and cinema administrators through two platforms:

1.  **Mobile Application (Customer)**: A seamless app for users to browse movies, book tickets, order snacks, and manage their rewards.
2.  **Web Portal (Administrator)**: A powerful dashboard for cinema managers to control schedules, inventory, ticket sales, and reporting.

This project leverages modern technologies like **Spring Boot** for the backend, **React** for the responsive admin interface, and **Android** mobile development to deliver a good user experience.

---

## **Key Features**

### **For Customers (Mobile App)**

- **🎬 Smart Booking System**: Browse "Now Showing" and "Coming Soon" movies with detailed info (trailers, cast, etc.).
- **⚡ 5-Minute Seat Reservation Rule**:
  > **Smart Seat Hold Policy**: To ensure fair availability, selected seats are temporarily reserved for **5 minutes** during the booking process. If the transaction is not completed within this timeframe, the seats are automatically released for other customers.
- **🥤 Food & Beverage Ordering**: Pre-order popcorn and drinks along with your tickets.
- **🎟️ Real-time Seat Selection**: Visual seat map to choose your preferred spot.
- **⭐ Community & Rewards**: Rate movies, write reviews, and earn points for discounts.

### **For Administrators (Web Portal)**

- **📅 Showtimes Management**: Drag-and-drop scheduling for movie screenings.
- **📊 Dashboard & Analytics**: Real-time overview of ticket sales, revenue, and occupancy rates.
- **🎫 Ticket & Seat Management**: Configure seat layouts, pricing tiers, and handle cancellations/refunds.
- **🍿 Inventory Control**: Manage F&B items, stocks, and pricing.
- **🏷️ Promotions**: Create and manage valid vouchers and discount codes.

---

## **System Architecture**

The system follows a classic client-server architecture:

- **Clients**: Android App (User) & React Web App (Admin).
- **Server**: Spring Boot REST API handling business logic and security.
- **Database**: MariaDB for persistent storage.

![System Architecture](https://drive.google.com/file/d/1N7aI_6qR-TRWzSlAbwxMTFtJa08B-IHH/view?usp=sharing)

---

## **Technology Stack**

### **Backend (Server)**

- **Language**: Java 17
- **Framework**: Spring Boot 3.3.3
- **Security**: Spring Security, JWT (JSON Web Tokens)
- **Database**: MariaDB
- **ORM**: Spring Data JPA / Hibernate
- **API Documentation**: Swagger / OpenAPI (SpringDoc)
- **Tools**: Maven, Lombok

### **Frontend (Admin Dashboard)**

- **Framework**: React (v18)
- **Build Tool**: Create React App (React Scripts)
- **Styling**: CSS / Lucide React (Icons)
- **HTTP Client**: Axios
- **Routing**: React Router DOM

### **Mobile (Customer App)**

- **Platform**: Android
- **Language**: Java (Gradle Build System)

---

## **Getting Started**

### **Prerequisites**

- **Java Development Kit (JDK) 17** or higher.
- **Node.js** and **npm** (for the Admin Web).
- **Android Studio** (for the Mobile App).
- **MariaDB** installed and running.

### **Installation & Running**

#### **1. Backend Setup**

Navigate to the `mcma-backend` directory:

```bash
cd mcma-backend
# Update application.properties with your database credentials
mvn spring-boot:run
```

The server usually starts on `http://localhost:8080`.

#### **2. Admin Web Setup**

Navigate to the `mcma-frontend` directory:

```bash
cd mcma-frontend
npm install
npm start
```

The admin portal will open at `http://localhost:3000`.

#### **3. Mobile App Setup**

- Open the `mcma-app` folder in **Android Studio**.
- Sync the project with Gradle files.
- Run the app on an Emulator or a physical device.

---

## **Demo & Resources**

- **Project Demo Files**: [Google Drive Link](https://drive.google.com/drive/folders/1BLHmjmCd6TOZjq8Gvcs7_le8O5O-lD4O?usp=drive_link)
- **API Documentation**: (Available at `/swagger-ui/index.html` when server is running)

---

### **Developed by Group 14**

- **Leader**: Pham Hoang Anh
- **Members**:
  - Nguyen Viet Anh
  - Nguyen At
  - Nguyen Minh Duc
  - Nguyen Minh Hien
  - Pham Tien Nhat
  - Ha Thanh Tung
