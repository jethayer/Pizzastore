# Pizzastore

## Overview

Pizzastore is a **Pizza Management System** designed to help pizza store owners manage their pizza toppings and allow chefs to create pizza master pieces. This project is built with a **Java** and **Spring Boot** backend, **React** with **Vite** and **TypeScript** on the frontend, and a **PostgreSQL** database for persistent data storage.

### Key Features

- **Manage Toppings**: The pizza store owner can view, add, update, or delete pizza toppings. Duplicate toppings are not allowed.
- **Manage Pizzas**: The pizza chef can view, create, update, or delete pizzas. Each pizza can have toppings, and duplicate pizza names are prevented.
- **Role-based Authentication**: Role-specific dashboards for the owner and chef are protected using **JWT**.
- **Responsive UI**: The user interface is functional, intuitive, and designed to be responsive.
- **Automated Test Suite**: The backend includes unit and integration tests for critical functionality.
- **Deployment**: The backend is deployed to **Railway**, and the frontend is hosted on **Netlify**.

## Local Setup

### My system for local development

- **Java 21**
- **Node.js 18+**
- **Maven**
- **Docker**
- **PostgreSQL**

#### Note: Going forward the steps to build and run locally will use docker. I believe this is the simplest and most consistent way, although you can generate a jar file with maven and java, run the frontend with npm separately, and then setup a local postgres server. However, Docker will achieve the same in less steps and provide a local volume for persistent storage.  
  
1. If you wish to use docker for simplicity be sure to have it installed and running  
   You can download docker desktop here: https://www.docker.com/products/docker-desktop/

2. Open a command prompt or terminal

3. Clone the repository:
   ```bash
   git clone https://github.com/jethayer/Pizzastore.git
   
4. Navigate to the project directory
   ```bash
   cd Pizzastore

5. Run the docker command
   ```bash
   docker-compose up --build

The build may take a few minutes to complete if running it for the first time since it needs to create the images, install all dependencies, etc.

6. In the brower, visit: http://localhost:5173

After the first build you can stop and start the application with docker-compose down and docker-compose up respectively  
<br>

To run tests locally: 
1. You will need Java 21. You can check your version with the command java --version. JDK 21 is the latest Long-Term Support (LTS) release of the Java SE Platform.
   You can downdload JDK 21 here: https://www.oracle.com/java/technologies/downloads/#java21

3. navigate to the server directory from the root directory (Pizzastore)
   ```bash
   cd server

4. Run tests with Maven wrapper
   ```bash
   ./mvnw test
<br>

## Thought Process Behind Technical Choices

### Backend (Java + Spring Boot + PostgreSQL)

### Data Persistence
For data persistence, I chose to store the data in a **PostgreSQL** database because it is an ACID compliant relational database and has good compatibility with **Spring Boot**'s JPA/Hibernate ORM.

### Database
The actual database istelf has four tables: Users, Pizza, Topping, Pizza_Toppings. The Pizza_Toppings table stores the pizza table and topping table keys, and allows a many to many relationship between pizzas and toppings.

### Automated Test Suite
API endpoints were tested with JUnit. Unit tests were creatd for the services and controllers to test the crud operations as well as the repositories. The test suite can be automatically ran with the **mvn test** command from the server directory.

### Authentication and Security
The requirements stated to have owners be able to make toppings and chefs be able to make pizzas. Because of this, I decided to use **Spring Security** with **JWT** for role-based access control. The JWT tokens handle authentication and protect specific routes based on user roles (Owner and Chef). For simplicity, I felt it was appropriate to allow owners to have access to the chef dashboard to be able to make pizzas as well, (they are the owners afterall). Chefs, however, do not have access to the owner dahsboard and therefor cannot create toppings. It is also trivial to change this in the security configuration to account for future roles like a customer or to change permissions.

### User Interface (Frontend with Vite + React + TypeScript)
The UI was made to be intuitive and easy to use. The user is first brought to a login screen where they can login and are directed to whatever dashboard matches their role. They can then begin making toppings or pizzas and can switch dashboards if they are an owner via the navigation bar. You can also register an user with a chosen username, password, and role by selecting the option on the navigation bar. **React** was chosen for the frontend due to its component-based architecture. Using **Vite** as the build tool allowed for a more optimized deployment (create-react-app was awful with all the vulnerabilites and long build times. Thank you Vite!) **TypeScript** was used to provide static typing. Since I am most familiar with Java, I prefer TS over JS for the typing and this also makes things better at scale.

### Deployment
The backend is deployed to **Railway**. The frontend is deployed on **Netlify**. Separate deployment was chosen to maintain flexibility in the frontend and backend.
