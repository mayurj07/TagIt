package edu.sjsu.cmpe275.tagit.controllers;

import edu.sjsu.cmpe275.tagit.exceptions.BadRequestException;
import edu.sjsu.cmpe275.tagit.exceptions.EntityNotFound;
import edu.sjsu.cmpe275.tagit.models.Notebook.Notebook;
import edu.sjsu.cmpe275.tagit.models.User.User;
import edu.sjsu.cmpe275.tagit.services.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
//@Component("UserController")
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;

  //=================================================
  //          Create a new User
  //=================================================
  @RequestMapping(method = RequestMethod.POST, produces = "application/json")
  public ResponseEntity<User> createUser(@Valid @RequestBody User user, BindingResult result){
    if(user.getName() == null || user.getName().trim().equals(""))
      throw new BadRequestException("User name required.");
    if(user.getEmail() == null || user.getEmail().trim().equals(""))
      throw new BadRequestException("Email required.");

    User userob = null;

    try{
      userob = new User(user.getName(), user.getEmail());
      System.out.println(user.getUserid());
    }catch(Exception e){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
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


} // class UserController
