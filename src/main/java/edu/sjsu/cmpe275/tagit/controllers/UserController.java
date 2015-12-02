package edu.sjsu.cmpe275.tagit.controllers;

import edu.sjsu.cmpe275.tagit.Utils.EmailNotification;
import edu.sjsu.cmpe275.tagit.Utils.Utils;
import edu.sjsu.cmpe275.tagit.exceptions.BadRequestException;
import edu.sjsu.cmpe275.tagit.exceptions.EntityNotFound;
import edu.sjsu.cmpe275.tagit.interceptor.LoginInterceptor;
import edu.sjsu.cmpe275.tagit.models.User.User;
import edu.sjsu.cmpe275.tagit.services.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@RestController
@EnableAutoConfiguration
@ComponentScan
@Component("UserController")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    EmailNotification emailNotification;

    @Autowired
    LoginInterceptor loginInterceptor;

    /*public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("::::::::::::: in the add interceptor :::::::");
        registry.addInterceptor(loginInterceptor).addPathPatterns("/user/*");
    }*/


        //=================================================
  //          Create a new User
  //=================================================
  @RequestMapping(method = RequestMethod.POST, produces = "application/json")
  public ResponseEntity<User> createUser(@Valid @RequestBody User user, BindingResult result, HttpServletResponse response){
    if(user.getName() == null || user.getName().trim().equals(""))
      throw new BadRequestException("User name required.");
    if(user.getEmail() == null || user.getEmail().trim().equals(""))
      throw new BadRequestException("Email required.");
    if(user.getCountry() == null || user.getCountry().trim().equals(""))
      throw new BadRequestException("Country required.");
    if(user.getPassword() == null || user.getPassword().trim().equals(""))
      throw new BadRequestException("Password required.");
    if(user.getState() == null || user.getState().trim().equals(""))
      throw new BadRequestException("State required.");


    //this call validated that email is not already in use
    userService.isEmailAvailable(user.getEmail());

    String encryptPass = Utils.passwordEncrypter(user.getPassword());
    System.out.println("encrypted pass: "+encryptPass);

      User userob = null;

      try{
          userob = new User(user.getName(), user.getEmail() , encryptPass, user.getCountry(),user.getState());
          System.out.println(user.getUserid());
      }
      catch(Exception e){
          return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }

      emailNotification.sendEmailonSignUp(user.getEmail(),user.getName());
      return new ResponseEntity<User>(userService.create(userob), HttpStatus.CREATED);
  }


  //=================================================
  //          Get a User by ID
  //=================================================
  @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
  public ResponseEntity<User> getUser(@PathVariable(value = "id") long UID) {
    try {
     User user = userService.getUserById(UID);
      if (user.getName() != null)
        return new ResponseEntity<User>(user, HttpStatus.OK);
      else
        throw new EntityNotFound("User with ID: " + UID + " is not present in the system");

    } catch (Exception e) {
      throw new EntityNotFound("User with ID: " + UID + " not present in the system.");
    }
  }


  //=================================================
  //          User Login
  //=================================================
  @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
  @ResponseBody
   public ResponseEntity<User> validateUser(@Valid @RequestBody User user, BindingResult result, HttpServletResponse response){

      User tempUser = userService.getUserByEmail(user.getEmail());
      String savedPass = tempUser.getPassword();
      String enteredPass =Utils.passwordEncrypter(user.getPassword());
      System.out.println(savedPass);
      System.out.println(enteredPass);
      if(savedPass != null){
          if( savedPass.equals(enteredPass) ){

          String sessionid = Utils.sessionIdGenerator(); //generate a session id
          tempUser.setSessionid(sessionid);
          User userWithSession = userService.create(tempUser); // update the user with sessionid
          System.out.println(" user's session is is :"+userWithSession.getSessionid());
          if(userWithSession!=null)
              {
                  userWithSession.setPassword(null);
                  Cookie cookie1 = new Cookie("user",userWithSession.toString());
                  cookie1.setMaxAge(30000);
                  response.addCookie(cookie1);
                  Cookie cookie2 = new Cookie("sessionid",userWithSession.getSessionid());
                  cookie2.setMaxAge(30000);
                  response.addCookie(cookie2);

              }
              tempUser.setPassword(null);
              return new ResponseEntity<User>(tempUser, HttpStatus.OK);
          }
          else{
              System.out.println("Password incorrect");
              throw new BadRequestException("Password incorrect");
          }
      }else {
          System.out.println("User not found.");
          throw new BadRequestException("User not found.");
      }


  }
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    private boolean logout(HttpServletResponse response) {

        response.addCookie(new Cookie("sessionid", ""));
        response.addCookie(new Cookie("username", ""));
        response.addCookie(new Cookie("userid", ""));
        return true;
    }

} // class UserController
