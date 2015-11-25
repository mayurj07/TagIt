package edu.sjsu.cmpe275.tagit.services.User;

import edu.sjsu.cmpe275.tagit.models.User.User;

/**
 * Created by 010028252 on 11/24/2015.
 */
public interface UserService {
    public User create(User user);
    public User getUserById(long userid);
}
