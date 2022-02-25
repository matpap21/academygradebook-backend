
### `Project description`

Electronic gradebook \

The aim is to design and implement an application as an electronic gradebook for students, which provides the possibility of adding and removing students, student groups and subjects and a preview of the student grades issued, after logging in, the student should be able to view his grades issued by the lecturer
Frontend of this application is written in ReactJs and it can be found in directory React-UI
### `Technologies`

-JavaScript,
-Spring Boot,\
-Spring Security,\
-Spring Data JPA,\
-Hibernate,\
-MySQL,\
-ReactJs,\
-Redux,\
-Material-UI 

### `Implementation`

The project is part of the "Junior Java Developer" training course conducted by the Software Development Academy. \
The project was invented and created for the purpose of completing the course.

### `Features`

• Adding fields of studies,\
• Adding subjects,\
• Adding student groups,\
• Adding / removing students,\
• Adding students to groups -> option for the Dean and Lecturers,\
• Adding grades -> option for Dean and Lecturers,\
• View of student grades given by Dean and Lecturers, 

### `Use Case`

• The dean first adds the field of study
• Dean or lecturer adds a subject
• Dean or lecturer creates a group of students
• Dean or lecturer adds grades to the student
• The Dean or lecturer can view the student's grades
• The student can view his / her grades

### `Configuration`

The only requirement for this application is reachable database. Current version of the application allows configuring
MySQL database. Configuration file is located in: `src\main\resources\application.properties`. Required database 
parameters are:
```
spring.datasource.url=?
spring.datasource.username=?
spring.datasource.password=?
```

Which can also be provided in command line along with application run command:
```bash
$ mvn -Dspring.datasource.url=? -Dspring.datasource.username=? -Dspring.datasource.password=? spring-boot:run
```

### `Build and run`

Application is built in 2 steps: frontend and backend. 

To build UI we can run maven command which runs plugin, triggering npm commands:
```bash
$ mvn install
# or
$ mvn package
```

To start the application run:
```bash
$ mvn spring-boot:run
```

### `Author`
Mateusz Paprocki 
E-mail: mateusz.jan.paprocki@gmail.com
Github: https://github.com/matpap21

