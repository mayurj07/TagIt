package edu.sjsu.cmpe275.tagit.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



//@RestController
//@EnableAutoConfiguration
//@ComponentScan
//@RequestMapping("/")
//@Import(AppConfig.class)

@Controller
public class MainController{

  @RequestMapping("/")
  @ResponseBody
  public String index() {

      return "/webapp/hangoutamigosapp/index.html";

//      return "Proudly handcrafted by " +
//        "<a href='http://netgloo.com/en'>netgloo</a> :)";
  }

}
