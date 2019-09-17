# WC University Portal 1.0
A software made of several micro-service back-ends, each of which are Spring Boot projects.

### Note: The entire project is based on a fictional idea. WCU does not represent a real world university.
![WCU](https://user-images.githubusercontent.com/14056189/65015323-07e51280-d943-11e9-9f75-4f35e8e20487.png)

> Logo was crafted using Adobe Illustrator

### Overview:
This is a web application consisting of several microservices. Users of various roles can interact with the system by using the web frontend. As data source, NoSQL database is used (MongoDB). The backend microservices are deployed on Heroku. The frontend is deployed there as well. All the services are programmed using Spring Boot and Vaadin was used for UI components.

## Software Used:
- Intellij Idea Ultimate 2019.2
## Language Used:
- Java
## Frameworks Used:
- Spring Boot 2.1.7
- Vaadin


## Frontend Link:
#### 1. [WCU Portal](https://wc-university-portal.herokuapp.com/login)

## Backend Microservices Links (with Swagger for API viewing):
#### 1. [Discovery Server](http://wc-discovery-server.herokuapp.com/)
#### 2. [Gateway Server](http://wc-gateway-server.herokuapp.com/)
#### 3. [Authentication Server](http://wc-authentication-server.herokuapp.com/swagger-ui.html)
#### 4. [Student Server](http://wc-student-server.herokuapp.com/swagger-ui.html)
#### 5. [Human Resources Server](http://wc-human-resources-server.herokuapp.com/swagger-ui.html)
#### 6. [Academic Server](http://wc-academic-server.herokuapp.com/swagger-ui.html)
#### 7. [Convocation Server](http://wc-convocation-server.herokuapp.com/swagger-ui.html)

## Databases:
Every service (except discovery and gateway/proxy) has its own NoSQL database.

- studentdb (For storing Student data)
- hrdb (For storing Employee data)
- academicdb (For storing Program and Course data)
- convocationdb (For storing Convocation registration data)
- authdb (For storing user credentials)

## Authentication System:
Since authentication is a crucial issue. We added some features:

- Credentials can only be saved, never recover (no GET calls)
- When credentials are saved or updated, only LoginToken is returned. The password is never returned back anywhere. There is no API to list the credentials with passwords.
- LoginTokens may be viewed (through API call). It contains username and role.


## Role Activity:
### 1. Student:
- View Registered courses by him/her
- View current CGPA, number of credits completed
- Apply for convocation if eligible (depends on his/her program’s minimum credits for graduation criteria). Otherwise the option will be disabled
- Make payment for convocation (Selecting gateway and placing TrxID). This option will be disabled before applying. Also one can make payment only once.
### 2. Academic Deputy Registrar:
- View all the programs and courses offered in the university
- Create program
- Create courses. Course needs to be offered in any one of the created program. A program combo box makes it sure.
### 3. Admission Officer:
- View students registered in all the programs
- Add students in different programs. Only into one of the programs created in the system, a student can be admitted.
- Create initial login credentials for the students
### 4. Program Coordinator:
- View students registered in program he is coordinating
- View their grades and credits completed
- Select students and register them into different courses offered in that particular program.
### 5. Examination Officer:
- View students registered in all the programs
- Select student and submit grades for the courses the selected student registered.
- As soon as the officer grades a course, it goes to gradedCourseList and removed from registeredCourseList (in the Student model class). So same course can not be graded twice.
- As soon as the grading of a subject is done, the student grid with CGPA information automatically updates.
- Exam Officer can also view convocation applications and their payment and confirmation status.
### 6. Human Resources Deputy Registrar:
- View all the employees in all the departments
- Add employee into any department and create initial credentials.

### All users can update their information (not the unchangable ones like Student ID/Employee Initials etc) and can update password.
