package edu.sjsu.cmpe275.tagit.controllers;

import edu.sjsu.cmpe275.tagit.configuration.AppConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;



//@RestController
//@EnableAutoConfiguration
//@ComponentScan
//@RequestMapping("/")
//@Import(AppConfig.class)

@Controller
public class MainController extends WebMvcConfigurerAdapter {

  @RequestMapping("/")
  @ResponseBody
  public String index() {

      return "/webapp/hangoutamigosapp/index.html";

//      return "Proudly handcrafted by " +
//        "<a href='http://netgloo.com/en'>netgloo</a> :)";
  }

}
