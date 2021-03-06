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
import javax.servlet.http.HttpServletRequest;
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

    //=================================================
    //          Create a new User
    //=================================================
    @RequestMapping(value = "/signup", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user, BindingResult result, HttpServletResponse response) {
        if (user.getName() == null || user.getName().trim().equals(""))
            throw new BadRequestException("User name required.");
        if (user.getEmail() == null || user.getEmail().trim().equals(""))
            throw new BadRequestException("Email required.");
        if (user.getCountry() == null || user.getCountry().trim().equals(""))
            throw new BadRequestException("Country required.");
        if (user.getPassword() == null || user.getPassword().trim().equals(""))
            throw new BadRequestException("Password required.");
        if (user.getState() == null || user.getState().trim().equals(""))
            throw new BadRequestException("State required.");


        //this call validated that email is not already in use
        userService.isEmailAvailable(user.getEmail());

        String tokenString = Utils.verificationTokenGenerator(user.getEmail());
        String encryptPass = Utils.passwordEncrypter(user.getPassword());
        System.out.println("encrypted pass: " + encryptPass);

        User userob = null;

        try {
            userob = new User(user.getName(), user.getEmail(), encryptPass, user.getCountry(), user.getState(),tokenString,false);
            System.out.println(user.getUserid());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        emailNotification.sendEmailonSignUp(user.getEmail(), user.getName(),tokenString);
        userService.create(userob);
        userob.setPassword(null);
        userob.setToken(null);
        return new ResponseEntity<User>(userob, HttpStatus.CREATED);
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
    public ResponseEntity<User> validateUser(@Valid @RequestBody User tmpUser, BindingResult result, HttpServletResponse response) {

        User user = userService.getUserByEmail(tmpUser.getEmail());
        String savedPass = user.getPassword();
        String enteredPass = Utils.passwordEncrypter(tmpUser.getPassword());

        if(!user.isVerified()){
            throw new BadRequestException("Please verify your email address");
        }

        //compare given password with the actual password
        if (savedPass != null) {
            if (savedPass.equals(enteredPass)) {

                Integer sessionToken = Utils.sessionTokenGenerator(); //generate a session id
                user.setSessionid(sessionToken.longValue());
                User userWithSession = userService.create(user); // update the user with sessionid
                if (userWithSession != null) {
                    userWithSession.setPassword(null);
                    userWithSession.setToken(null);
                    Cookie cookie1 = new Cookie("tagit", userWithSession.toString());
                    cookie1.setMaxAge(30000);       //8.5 hrs expiry
                    cookie1.setPath("/");
                    response.addCookie(cookie1);
                }
                user.setPassword(null);
                return new ResponseEntity<User>(user, HttpStatus.OK);
            } else {
                throw new BadRequestException("Incorrect Password");
            }
        } else {
            throw new BadRequestException("User not found.");
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    private boolean logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie1 = new Cookie("tagit", null);
        cookie1.setPath("/");
        cookie1.setMaxAge(0);
        response.addCookie(cookie1);
        return true;
    }


    @RequestMapping(value = "/verify/{email}/{token}", method = RequestMethod.GET, produces = "application/json")
    public String verifyUser(@PathVariable(value = "email") String  email, @PathVariable(value = "token") String  token) {


        User user = userService.getUserByEmail(email);
        if(user.getToken().equals(token))
        {
            user.setVerified(true);
            userService.create(user);
            user.setPassword(null);
            user.setToken(null);
            return "Email Account verified";
        }
        else{
            throw new BadRequestException("Incorrect user");
        }
    }


}
