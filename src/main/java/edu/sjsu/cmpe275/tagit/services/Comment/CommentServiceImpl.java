package edu.sjsu.cmpe275.tagit.services.Comment;
import edu.sjsu.cmpe275.tagit.exceptions.EntityNotFound;
import edu.sjsu.cmpe275.tagit.models.Comment.Comment;
import edu.sjsu.cmpe275.tagit.models.Comment.CommentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
/**
 * Created by Cherisha on 11/28/15.
 */
@Service("CommentService")
@Transactional
public class CommentServiceImpl implements CommentService {



    @Autowired
    CommentDao commentDao;

    @Override
    public Comment createComment(Comment comment) {
        commentDao.save(comment);
        return comment;
    }


    @Override
    public ArrayList<Comment> getCommentByBookmarkId(int bookmarkId) {
        ArrayList<Comment> comment = (ArrayList<Comment>) commentDao.getCommentByBookmarkId(bookmarkId);
        return comment;
    }


  /*  @Override
    public void removeComment(int id) throws EntityNotFound {
        commentDao.delete(id);
    }*/

}
