````markdown
# 🧪 Ausboard – API Testing Backend

This is the **backend repository** for **Ausboard**, a modern and scalable API testing platform. Built using **Spring Boot**, the backend handles test case execution, stores results, and provides RESTful APIs for running and analyzing various types of API tests, including:

- ✅ Functional Testing
- 🚬 Smoke Testing
- 🐞 Fuzz Testing
- 💣 Stress Testing
- 📈 Load Testing

---

## ⚙️ Key Features

- 🔄 REST API endpoints to trigger and manage test cases
- 📬 Test execution via `RestTemplate`
- 📊 Aggregated results including **latency**, **throughput**, **response codes**, and **execution time**
- 📁 JSON-based test definitions and dynamic payload support
- 🧵 Handles multiple concurrent test executions
- 🔒 Secure CORS and Spring security
- 🌐 Designed to integrate with the [Ausboard Frontend](https://github.com/MilanMishra19/ausboardfk)

---

## 🛠️ Tech Stack

- **Framework**: Spring Boot
- **HTTP Client**: RestTemplate
- **Build Tool**: Maven
- **Language**: Java 21+
- **Database**: Neon PostgreSQL

---

## 📦 Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/ausboardbk.git
cd ausboardbk
````

### 2. Configure Application Properties

Edit `src/main/resources/application.properties` as needed:

```properties
server.port=8080

# Example DB config
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# Hibernate
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
```

### 3. Build & Run the Application

Using Maven:

```bash
./mvnw clean install
./mvnw spring-boot:run
```


> The backend will run at: `http://localhost:5000`

---


## 📊 Test Result Metrics

* **Latency (ms)**
* **Throughput (requests/sec)**
* **Execution Time**
* **Success Rate**
* **Error Rate**
* **HTTP Status Codes**

All metrics are returned in a standardized format to be consumed by the frontend dashboard.

---

## 📂 Project Structure

```
ausboard-backend/
├── controller/        # REST controllers
├── service/           # Core business logic
├── entity/            # Data models 
├── dto/               # DTOs
├── repository/        # Database interaction (JPA/Mongo)
├── config/            # CORS, Spring Security
├── resources/
│   ├── application.properties
│   └── ...
└── ...
```

---

## 🔗 Frontend Repository

👉 **Ausboard Frontend:** [https://github.com/MilanMishra19/ausboardfk](https://github.com/MilanMishra19/ausboardfk)

---

## 🧠 Contribution Guidelines

1. Fork the repository
2. Create a new branch
3. Make your changes
4. Submit a pull request 🚀

---

## 📄 License

This project is licensed under the **MIT License**.

---

## 🙋‍♂️ Maintainer

Made with ❤️ by [Milan Mishra](https://github.com/MilanMishra19)

```
