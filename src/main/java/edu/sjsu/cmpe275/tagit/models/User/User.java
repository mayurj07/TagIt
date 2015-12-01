package edu.sjsu.cmpe275.tagit.models.User;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Users")
public class User {

  @Id
  @JsonProperty
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name= "userid")
  private long userid;

  // The user's name
  @NotNull
  @JsonProperty
  @Column(name= "name")
  private String name;

  // The user's email
  @NotNull
  @JsonProperty
  @Column(name= "email")
  private String email;

  @NotNull
  @JsonProperty
  @Column(name= "password")
  private String password;

  @NotNull
  @JsonProperty
  @Column(name= "country")
  private String country;

  @NotNull
  @JsonProperty
  @Column(name= "state")
  private String state;

  @JsonProperty
  @Column(name = "sessionid")
  private String sessionid;

  public String getSessionid() {
    return sessionid;
  }

  public void setSessionid(String sessionid) {
    this.sessionid = sessionid;
  }

  //Constructors
  public User() { }

  public User(long userid) {
    this.userid = userid;
  }

  public User(@JsonProperty String name,
              @JsonProperty String email) {
    this.email = email;
    this.name = name;
  }

  public User(@JsonProperty String name,
              @JsonProperty String email,
              @JsonProperty String password,
              @JsonProperty String country,
              @JsonProperty String state) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.country = country;
    this.state = state;
  }

  //setters and getters
  @Id
  @Column(name = "userid", unique = true, nullable = false)
  public long getUserid() {
    return userid;
  }

  public void setUserid(long userid) {
    this.userid = userid;
  }

  @Column(name = "name", unique = false, nullable = false)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Column(name = "email", unique = true, nullable = false)
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public String getCountry() {
    return country;
  }

  public String getState() {
    return state;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public void setState(String state) {
    this.state = state;
  }
} // class User



