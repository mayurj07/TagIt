package edu.sjsu.cmpe275.tagit.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController{

  @RequestMapping("/")
  @ResponseBody
  public String index() {

      return "<h1>Welcome to CMPE 275 Project - Team 8 </h1>" +
              "<h1>TagIt App - Save and Share your bookmarks like Evernote.</h1>" +
              "<h2>" +
                "<a href=\"http://localhost:8080/tagitapp/index.html\"> Open Web App </a>" +
              "</h2>";
  }

}
