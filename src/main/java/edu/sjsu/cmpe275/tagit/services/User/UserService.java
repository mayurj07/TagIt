package edu.sjsu.cmpe275.tagit.services.User;

import edu.sjsu.cmpe275.tagit.models.User.User;


public interface UserService {
    public User create(User user);
    public User getUserById(long userid);
    public void isEmailAvailable(String email);
    public User getUserByEmail(String email);
}
