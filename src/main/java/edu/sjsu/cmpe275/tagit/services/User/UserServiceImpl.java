package edu.sjsu.cmpe275.tagit.services.User;

import edu.sjsu.cmpe275.tagit.exceptions.BadRequestException;
import edu.sjsu.cmpe275.tagit.models.User.User;
import edu.sjsu.cmpe275.tagit.models.User.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public User getUserById(long userid) {
        User user = userDao.findOne(userid);
        System.out.println(" in the user dao :: "+user.getUserid()+user.getName());
        return user;
    }

    @Override
    public void isEmailAvailable(String email){
        ArrayList<User> users = userDao.getUserByEmail(email);
       if(users.size()!=0){
           throw new BadRequestException("Email already in use");
       }
    }

    @Override
    public User getUserByEmail(String email){

        ArrayList<User> users = userDao.getUserByEmail(email);
        if(users.size()==0){
            throw new BadRequestException("Email does not exist");
        }
        else{
            return users.get(0);
        }
    }

   /* @Override
    public void updateSessionId(long userid,String sessionid)
    {
        userDao.updateUserSession(sessionid, userid);
    }*/

}
