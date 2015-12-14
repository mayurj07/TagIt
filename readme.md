#CMPE-275 Project - TAGIT

#### Team-8
 1. Mayur Jain, mayurj07@gmail.com, 009991059
 2. Harkirat Singh, harkirat.singh@sjsu.edu, 010027823
 3. Akanksha Singh, akanksha848@gmail.com, 010030839
 4. Deep Trivedi, deep.trivedi@sjsu.edu, 010028252
 5. Cherisha Choukse, cherisha58@gmail.com, 010114845

## Using MySQL in Spring Boot via Spring Data JPA and Hibernate

#### Prerequisites

- Java 8
- Maven 3
- Mysql


### Deployment Steps for Ubuntu :-

#### From terminal

1. Clone project from github:-

   $ `git clone https://github.com/mayurj07/TagIt.git`

2. Install Maven:-

   Go on the project's root folder, then type:-

   $ 'cd TagIt/'

   $ `apt-cache search maven`

   $ `sudo apt-get install maven`

3. Import Database schema for Mysql:-

   Inside Mysql terminal:

   $ `source tagit-schema`

3. Run the startup script:-

   $ `chmod 777 startup.sh`

   $ `./startup.sh`



#### From Eclipse (Spring Tool Suite)

Import as *Existing Maven Project* and run it as *Spring Boot App*.


### Usage
- Run the application and go on http://localhost:8080/