package edu.sjsu.cmpe275.tagit.controllers;

import edu.sjsu.cmpe275.tagit.exceptions.BadRequestException;
import edu.sjsu.cmpe275.tagit.exceptions.EntityNotFound;
import edu.sjsu.cmpe275.tagit.models.User.User;
import edu.sjsu.cmpe275.tagit.services.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@RestController
@Component("UserController")
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;

  //=================================================
  //          Create a new User
  //=================================================
  @RequestMapping(method = RequestMethod.POST, produces = "application/json")
  public ResponseEntity<User> createUser(@Valid @RequestBody User user, BindingResult result, HttpServletResponse response){
    if(user.getName() == null || user.getName().trim().equals(""))
      throw new BadRequestException("User name required.");
    if(user.getEmail() == null || user.getEmail().trim().equals(""))
      throw new BadRequestException("Email required.");

    User userob = new User();

    try{
      userob = new User(user.getName(), user.getEmail());
      userob.setName(user.getName());
      userob.setEmail(user.getEmail());
     // System.out.println(user.getUserid());

    }catch(Exception e){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    User createdUser = userService.create(userob);
    User userT = userService.getUserById(createdUser.getUserid());
    System.out.println(" in user controller :: userid is : "+userT.getUserid());
    response.addCookie(new Cookie("userid",String.valueOf(userT.getUserid()))); ////change to store the user email

    return new ResponseEntity<User>(createdUser, HttpStatus.CREATED);
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


} // class UserController
