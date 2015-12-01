package edu.sjsu.cmpe275.tagit.models.User;

import edu.sjsu.cmpe275.tagit.models.Comment.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface UserDao extends CrudRepository<User, Long> {

    @Query(value="SELECT * FROM users u  WHERE  u.email=?1",nativeQuery = true)
    ArrayList<User> getUserByEmail(String email);

    @Query(value="update users set sessioid=?1 where userid=?2",nativeQuery = true)
    User updateUser(String sessionid,int id);

    @Query(value = "select * from users where userid=?1 and sessionid=?2",nativeQuery = true)
    User getUserByUseridAndSessionid(long userid,String sessionid);
}
