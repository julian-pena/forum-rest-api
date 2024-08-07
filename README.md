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

Restful CRUD API for a forum with topics and responses, allowing users to interact with and manage forum data efficiently. Engineered for scalability and simplicity.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Built With

* [![SpringBoot][SpringBoot.java]][SpringBoot-url]
* [![SpringSecurity][SpringSecurity.java]][SpringSecurity-url]
* [![Java][Java.java]][Java-url]
* [![PostgreSQL][PostgreSQL.sql]][PostgreSQL-url]
* [![Hibernate][Hibernate.java]][Hibernate-url]
* [![MapStruct][MapStruct.com]][MapStruct-url]
* [![Maven][Maven.com]][Maven-url]
* [![Flyway][Flyway.com]][Flyway-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- GETTING STARTED -->
## Getting Started

This guide will help you set up and run the REST API locally on your machine.

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


### Steps to Setup
**1. Clone the repo**

   ```bash
   git clone https://github.com/julian-pena/forum-rest-api.git
   ```

**2. Configure PostgreSQL connection as per your installation**

- Open `src/main/resources/application.properties`
- Update `spring.datasource.username` and `spring.datasource.password` to your PostgreSQL configuration.

**3. Run the app using Maven**

  ```bash
  mvn spring-boot:run
  ```
The app will start running at <http://localhost:8080>


<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- USAGE EXAMPLES -->
## Usage

The app defines following CRUD APIs.

### users

| Method | Url         | Description                                 | Sample Valid Request Body |
|--------|-------------|---------------------------------------------|---------------------------|
| GET    | /users      | Get all users present in database as a page |                           |
| GET    | /users/{id} | Get user based on ID                        |                           |
| POST   | /users      | Create a new user                           | [JSON](#newuser)          |
| PUT    | /users/{id} | Update user based on ID                     | [JSON](#updateuser)       |
| DELETE | /users/{id} | Delete user based on ID                     |                           |

### Topics

| Method | Url          | Description                                  | Sample Valid Request Body |
|--------|--------------|----------------------------------------------|---------------------------|
| GET    | /topics      | Get all topics present in database as a page |                           |
| GET    | /topics/{id} | Get topic based on ID                        |                           |
| POST   | /topics      | Create a new topic                           | [JSON](#createtopic)      |
| PUT    | /topics/{id} | Update topic based on ID                     | [JSON](#updatetopic)      |
| DELETE | /topics/{id} | Delete topic based on ID                     |                           |

### Responses

| Method | Url             | Description                                     | Sample Valid Request Body |
|--------|-----------------|-------------------------------------------------|---------------------------|
| GET    | /responses      | Get all responses present in database as a page |                           |
| GET    | /responses/{id} | Get response based on ID                        |                           |
| POST   | /responses      | Create a new response                           | [JSON](#createresponse)   |
| PUT    | /responses/{id} | Update response based on ID                     | [JSON](#updateresponse)   |
| DELETE | /responses/{id} | Delete response based on ID                     |                           |


****Test them using postman or any other rest client.****

## Sample Valid JSON Request Bodys

##### <a id="newuser">Create user -> /users</a>
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

- [ ] Add Spring Security
- [ ] Add JWT
- [ ] Documentation(Swagger)

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
