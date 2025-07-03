# 🖱️ Devices API

A simple Spring Boot RESTful API for managing devices.  
Includes CRUD operations, PostgreSQL integration, and Docker support.

---

## 📚 Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Additional features](#additional-features)
- [What is missing?](#what-is-missing)
- [Final Notes](#final-notes)

---

## 🚀 Features

- Create, read, update, delete devices
- Search devices by state or brand
- List auditable actions in the database
- Logging every endpoint call
- PostgreSQL database integration
- Dockerized for easy setup
- Swagger UI for documentation

---

## 🛠 Tech Stack

- **Java 21**
- **Spring Boot 3.5.3**
- **PostgreSQL**
- **Maven**
- **Docker**
- **Swagger**

---

## ⚙️ Getting Started

### Prerequisites

- Docker & Docker Compose
- Java 21
- Maven

### Run with Docker

* Run the following command from the project root directory to build and start the application with Docker:

```bash
docker-compose up --build
```

Or simple import the project into your IDE and run the `DevicesApiApplication` class.

### Usage

You can access and execute all endpoints through Swagger UI at:

```
http://localhost:8080/swagger-ui/index.html
```

or 

you can use one of the API clients of your choice, like Postman or Insomnia.
I used Bruno (a simple and lightweight API client) to test the endpoints.
You can access the Requests collection in the `API Client - Bruno` folder.


### Additional features

- **Auditing**: Automatically create audit events into the database for every create, update, and delete actions in the application.
  - I added a auditing feature to save some actions performed in the application because I believe that in a real scenario we would need to
know who did what and when.
  - Even though it is a way basic auditing, I believe is enough for this project.
  - With more time I could implement authentication and authorization to restrict access to the endpoints and use the logged user to enhance audit information.
- **Logging**: Every endpoint call is logged with details.
  - An application with no logs will most likely be in the dark when it comes to debugging and monitoring.
  - As logging is a cross-cutting concern, I used aspects to not couple the logging part at any component.


### What is missing?
From the original requirements:
- **Better test coverage**: I couldn't write the tests for the web layer (Controller) which I could do with more two hours.
- Not only unit tests but also integration tests would be good to have.

From what I would like to implement:
- **Authentication and Authorization**: Implement security to restrict access to endpoints and also to enhance the auditing.

### Final Notes
Sorry for the delay in delivering this project. I received it Friday night, but this weekend was a bit busy for me.
I kind of just worked in the project a bit on Saturday, a bit on Monday and yesterday but still, I think it is in a good shape (despite the missing tests as mentioned earlier).
I hope you like what you see and we can have a chat about it soon.

Thanks for the opportunity!

Cheers!
