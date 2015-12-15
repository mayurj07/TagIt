#CMPE-275 Project - TAGIT

### TagIT - Tag & Share your Bookmarks with your friends.

#### Team-8
 1. Mayur Jain,       mayurj07@gmail.com,      (009991059)
 2. Harkirat Singh,   harkirat.singh@sjsu.edu, (010027823)
 3. Akanksha Singh,   akanksha848@gmail.com,   (010030839)
 4. Deep Trivedi,     deep.trivedi@sjsu.edu,   (010028252)
 5. Cherisha Choukse, cherisha58@gmail.com,    (010114845)

### Built using:-

- Spring Boot via Spring Data JPA annotations and Hibernate ORM
- Spring AOP (Aspect Oriented Programming)
- Spring Transactions Management
- Javascript, Angular.js, HTML5, CSS, Bootstrap
- Bower (Frontend package management)
- Mysql Database

#### Prerequisites

- Java 8
- Maven 3
- Mysql
- Node.js and NPM
- Bower


### Deployment Steps for Ubuntu :-

#### From terminal

1. Clone project from github:-

   $ `git clone https://github.com/mayurj07/TagIt.git`

2. Install Maven:-

   Go on the project's root folder, then type:-

   $ `cd TagIt/`

   $ `apt-cache search maven`

   $ `sudo apt-get install maven`

3. Import Database schema for Mysql:-

   Inside Mysql terminal:

   $ `source tagit-schema`

4. After making sure that you have npm installed:-

    4.1 Install Bower:

        $ `npm install -g bower`

    4.2 Install Bower dependencies:

        $ `bower install`

5. Run the startup script:-

   $ `chmod 777 startup.sh`

   $ `./startup.sh`


#### From Eclipse / Spring Tool Suite / Intellij IDEA:-

Import as *Existing Maven Project* and run it as *Spring Boot App*.

### Usage:-

Run the application using startup.sh script and go on http://localhost:8080/

### Live Demo:-

[View Demo](http://52.8.241.222:8080/tagitapp/index.html)