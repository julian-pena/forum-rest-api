<a name="readme-top"></a>

[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![LinkedIn][linkedin-shield]][linkedin-url]

<br />
<div align="center">
  <a href="https://github.com/github_username/repo_name">
    <img src="https://github.com/user-attachments/assets/03a49e32-5415-4c5b-ba00-5444315840de" alt="Logo" width="150" height="150"

  </a>
<h1 align="center">Forum REST API</h1>
<p align="center">

  <strong>Spring Boot, Spring Security, Spring Data, PostgreSQL, JPA, MapStruct, Rest API</strong>
  <br />
  <a href="https://github.com/julian-pena/forum-rest-api"><strong>Explore the docs »</strong></a>
  <br />
  <br />
  <a href="https://github.com/julian-pena/forum-rest-api/issues">Report Bug</a>
  ·
  <a href="https://github.com/julian-pena/forum-rest-api/issues">Request Feature</a>
</p>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->
## About The Project

Robust RESTful API for managing forum topics and responses. Secure and scalable with JWT authentication for seamless user interaction.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Built With

* [![SpringBoot][SpringBoot.java]][SpringBoot-url]
* [![SpringSecurity][SpringSecurity.java]][SpringSecurity-url]
* [![JsonWebToken][JsonWebToken.java]][JsonWebToken-url]
* [![Java][Java.java]][Java-url]
* [![PostgreSQL][PostgreSQL.sql]][PostgreSQL-url]
* [![Hibernate][Hibernate.java]][Hibernate-url]
* [![MapStruct][MapStruct.com]][MapStruct-url]
* [![Maven][Maven.com]][Maven-url]
* [![Flyway][Flyway.com]][Flyway-url]
* [![Koyeb][Koyeb.com]][Koyeb-url] 


<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Live Application
Access the live version of the Forum REST API here: [Forum REST API](https://forum-rest-api.onrender.com/). <br><br>
This project is deployed on [Render](https://www.render.com) with PostgreSQL as the database. Environment variables are used to manage sensitive data like database credentials. You can access the live version through the link above. It might take a few minutes to load the application due to Render free tier restrictions for deployment. 


<!-- GETTING STARTED -->
## Local Setup
Although the app is deployed and accessible online, you can still run it locally by following the steps below:

### Prerequisites

**1. Java 17 or higher**
- Before you begin, you should check your current Java installation by using the following command:

For Windows:
   ```bash
   java --version
   ```
For Linux:
   ```bash
  $ java -version
   ```

**2. Maven**

- Compatible with Apache Maven 3.8.1 or later. If you do not already have Maven installed, you can follow the instructions at [maven.apache.org](https://maven.apache.org/download.cgi/).

**3. PostgreSQL**
- If you do not already have PostgreSQL installed, you can follow the instructions at [postgresql.org](https://www.postgresql.org/download/).

<br>

## Steps to Setup
**1. Clone the repo**

   ```bash
   git clone https://github.com/julian-pena/forum-rest-api.git
   ```

**2. Set up environment variables for PostgreSQL**

- You will need to set the following environment variables for the database connection:
  - `SPRING_DATASOURCE_URL`
  - `SPRING_DATASOURCE_USERNAME`
  - `SPRING_DATASOURCE_PASSWORD`

On **Windows**, you can set these variables by running the following commands in your terminal:

```bash
set SPRING_DATASOURCE_URL=jdbc:postgresql://<your_postgres_url>
set SPRING_DATASOURCE_USERNAME=<your_username>
set SPRING_DATASOURCE_PASSWORD=<your_password>
```

On **Linux** or **macOS**, use the following commands:

```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://<your_postgres_url>
export SPRING_DATASOURCE_USERNAME=<your_username>
export SPRING_DATASOURCE_PASSWORD=<your_password>
```

#### Make sure to replace <your_postgres_url>, <your_username>, and <your_password> with your actual PostgreSQL credentials.

<br>

**3. Run the app using Maven**

  ```bash
  mvn spring-boot:run
  ```
The app will start running at <http://localhost:8080>

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Authentication and Authorization

This API uses Spring Security and JWT tokens to secure its endpoints. Below are the steps to understand the authentication process:

### User Registration

**Endpoint: POST /users**

- This endpoint is open to the public.
- A new user, along with their assigned role, can be created by sending a request to this endpoint.
```json
{
    "name": "Paul Hanks" 
    "email": "newuser@hotmail.com",
    "password": "securepassword",
    "role": "USER"
}
```

### User Authentication

**Endpoint: POST /auth/login**

- The user sends a JSON request containing their username and password.
- Upon successful authentication, a JWT token is returned.

```json
{
    "username": "newuser",
    "password": "securepassword"
}
```

**Response**
```json
{
    "username": "julianpr88@hotmail.com",
    "message": "User logged sucessfully",
    "jwt": "eyJhbGciOiJIU...",
    "status": true
}
```


### Accessing Protected Endpoints

To access any other non-public endpoint, the JWT token must be included in the Authorization header of the request as follows:

```makefile
Authorization: Bearer <your_token_here>
```

The token is used to validate the user's roles and the authenticity of the token, ensuring secure access to the API's protected resources.



<!-- USAGE EXAMPLES -->
## Usage

The app defines following CRUD APIs.


### Authentication Endpoints

| Method | Url           | Description                                          | Sample Valid Request Body | Protected | Roles Required |
|--------|---------------|------------------------------------------------------|---------------------------|-----------|----------------|
| POST   | /auth/login   | Authenticate user and receive a JWT                  | [JSON](#login)            | No        | None           |
| POST   | /auth/register| Register a new user and receive confirmation         | [JSON](#register)         | No        | None           |

### User Endpoints

| Method | Url         | Description                                 | Sample Valid Request Body | Protected | Roles Required |
|--------|-------------|---------------------------------------------|---------------------------|-----------|----------------|
| GET    | /users      | Get all users present in database as a page |                           | Yes       | ADMIN          |
| GET    | /users/{id} | Get userEntity based on ID                  |                           | Yes       | ADMIN          |
| POST   | /users      | Create a new userEntity                     | [JSON](#newuser)          | No        | None           |
| PUT    | /users/{id} | Update userEntity based on ID               | [JSON](#updateuser)       | Yes       | ADMIN          |
| DELETE | /users/{id} | Delete userEntity based on ID               |                           | Yes       | ADMIN          |


### Topics

| Method | Url          | Description                                  | Sample Valid Request Body | Protected? | Roles Required |
|--------|--------------|----------------------------------------------|---------------------------|------------|----------------|
| GET    | /topics      | Get all topics present in database as a page |                           | Yes        | ADMIN, USER    |
| GET    | /topics/{id} | Get topic based on ID                        |                           | Yes        | ADMIN, USER    |
| POST   | /topics      | Create a new topic                           | [JSON](#createtopic)      | Yes        | ADMIN          |
| PUT    | /topics/{id} | Update topic based on ID                     | [JSON](#updatetopic)      | Yes        | ADMIN          |
| DELETE | /topics/{id} | Delete topic based on ID                     |                           | Yes        | ADMIN          |


### Responses

| Method | Url             | Description                                     | Sample Valid Request Body | Protected | Roles Required |
|--------|-----------------|-------------------------------------------------|---------------------------|-----------|----------------|
| GET    | /responses      | Get all responses present in database as a page |                           | Yes       | USER, ADMIN    |
| GET    | /responses/{id} | Get response based on ID                        |                           | Yes       | USER, ADMIN    |
| POST   | /responses      | Create a new response                           | [JSON](#createresponse)   | Yes       | USER, ADMIN    |
| PUT    | /responses/{id} | Update response based on ID                     | [JSON](#updateresponse)   | Yes       | ADMIN          |
| DELETE | /responses/{id} | Delete response based on ID                     |                           | Yes       | ADMIN          |


****Test them using postman or any other rest client.****

## Sample Valid JSON Request Bodys

##### <a id="login">Login -> /auth/login </a>
```json
{
  "username" : "nimona_flowers@gmail.com",
  "password": "Nimonapassword123*"
}
```

##### <a id="newuser">Create userEntity -> /users</a>
```json
{
  "name" : "Nimona flowers",
  "email" : "nimona_flowers@gmail.com",
  "password": "Nimonapassword123*"
}
```
#### <a id="updateuser">Update User -> /users/{id}</a> 
```json
{
  "name" : "Scott Pilgrim",
  "email" : "scott_pilgrim_love_ramona@gmail.com",
  "password": "NewNimonapassword123*"
}
```

#### <a id="createtopic">Create Topic -> /topics</a>
```json
{
  "title": "New Topic Title",
  "message": "This is the content of the new topic",
  "authorId": 1,
  "courseId": 1
}
```

#### <a id="updatetopic">Update Topic -> /topics/{id}</a>
```json
{
  "title": "Updated Topic Title",
  "message": "This is the updated content of the topic",
  "forumStatus": "PENDING"
}
```

#### <a id="createresponse">Create Response -> /responses</a>
```json
{
  "message": "This is a new response message",
  "topicId": 1,
  "responderId": 2
}
```

#### <a id="updateresponse">Update Response -> /responses/{id}</a>
```json
{
  "message": "This is an updated response message",
  "solution": true
}
```

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- ROADMAP -->
## Roadmap

- [x] Add Spring Security
- [x] Add JWT
- [x] Documentation(Swagger)
- [ ] Unit Testing
- [ ] Integration Testing
- [ ] Improve deployment process with Docker and CI/CD pipelines

See the [open issues](https://github.com/julian-pena/forum-rest-api/issues) for a full list of proposed features (and known issues).

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTACT -->
## Contact

Julian David Peña Rojas - julianpr8@hotmail.com

Project Link: [https://github.com/julian-pena/forum-rest-api](https://github.com/julian-pena/forum-rest-api)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- ACKNOWLEDGMENTS -->
## Acknowledgments

* [Spring Boot, Java and Rest API design](https://app.aluracursos.com/degree/certificate/b3d7abfe-8bfa-43bb-8021-6fb6a38253a1?lang)
* [MapStruct documentation](https://mapstruct.org/documentation/1.6/reference/html/)
* [ChatGPT](https://chat.openai.com/)
* [SOLID principles](https://www.youtube.com/watch?v=_jDNAf3CzeY)
* [Spring security short course](https://www.youtube.com/watch?v=IPWBQDMIYkc)

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/julian-pena/forum-rest-api.svg?style=for-the-badge
[contributors-url]: https://github.com/julian-pena/forum-rest-api/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/julian-pena/forum-rest-api.svg?style=for-the-badge
[forks-url]: https://github.com/julian-pena/forum-rest-api/network/members
[stars-shield]: https://img.shields.io/github/stars/julian-pena/forum-rest-api.svg?style=for-the-badge
[stars-url]: https://github.com/julian-pena/forum-rest-api/stargazers
[issues-shield]: https://img.shields.io/github/issues/julian-pena/forum-rest-api.svg?style=for-the-badge
[issues-url]: https://github.com/julian-pena/forum-rest-api/issues
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/julian-peña-java
[SpringBoot.java]: https://img.shields.io/badge/Spring%20Boot-6DB33F?logo=springboot&logoColor=fff&style=flat
[SpringBoot-url]: https://spring.io/projects/spring-boot
[SpringSecurity.java]: https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white
[SpringSecurity-url]: https://spring.io/projects/spring-security
[JsonWebToken.java]: https://img.shields.io/badge/-JSON_Web_Tokens-black?style=for-the-badge&logoColor=white&logo=jsonwebtokens&color=000000
[JsonWebToken-url]: https://spring.io/projects/spring-security](https://jwt.io/
[Java.java]: https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white
[Java-url]: https://www.java.com/es/
[PostgreSQL.sql]: https://img.shields.io/badge/postgresql-4169e1?style=for-the-badge&logo=postgresql&logoColor=white
[PostgreSQL-url]: https://www.postgresql.org/
[Hibernate.java]: https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white
[Hibernate-url]: https://hibernate.org/
[MapStruct.com]: https://img.shields.io/badge/Map_Struct-blue%20?style=for-the-badge
[MapStruct-url]: https://mapstruct.org/
[Maven.com]: https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white
[Maven-url]: https://maven.apache.org/
[Flyway.com]: https://img.shields.io/badge/Flyway-CC0200?logo=flyway&logoColor=fff&style=for-the-badge
[Flyway-url]: https://www.red-gate.com/products/flyway/
[Koyeb.com]: https://img.shields.io/badge/Koyeb-121212?logo=koyeb&logoColor=fff&style=flat
[Koyeb-url]: https://www.red-gate.com/products/flyway/](https://www.koyeb.com/
