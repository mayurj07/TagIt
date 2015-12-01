package edu.sjsu.cmpe275.tagit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Application {

  /*public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }*/

    public static void main(String[] args) {
        SpringApplication.run(AppConfig.class, args);
    }

}
