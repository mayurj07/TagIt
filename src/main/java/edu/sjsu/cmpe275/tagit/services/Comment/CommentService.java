package edu.sjsu.cmpe275.tagit.services.Comment;

import edu.sjsu.cmpe275.tagit.models.Comment.Comment;

import java.util.ArrayList;
import java.util.List;
import java.lang.Object;

/**
 * Created by Cherisha on 11/28/15.
 */

public interface CommentService {

    public Comment createComment(Comment comment);
    //public Comment getCommentByCommentId(int id);
    //public void removeComment(long id);
    public ArrayList<Comment> getCommentByBookmarkId(int bookmarkId);

    //void removeComment(int commentId);
    //public ArrayList<Comment> getAllCommentsByBookmarkId(int id);

}
