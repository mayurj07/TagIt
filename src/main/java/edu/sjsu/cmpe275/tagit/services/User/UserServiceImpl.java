package edu.sjsu.cmpe275.tagit.services.User;

import edu.sjsu.cmpe275.tagit.models.User.User;
import edu.sjsu.cmpe275.tagit.exceptions.EntityNotFound;
import edu.sjsu.cmpe275.tagit.models.User.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by 010028252 on 11/24/2015.
 */

@Service("UserService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public User create(User user) {
        userDao.save(user);
        return user;
    }

    @Override
    public User getUserById(long userid) throws EntityNotFound{
        User user = userDao.findOne(userid);
        return user;
    }

}
