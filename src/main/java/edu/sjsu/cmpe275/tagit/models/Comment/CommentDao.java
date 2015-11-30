package edu.sjsu.cmpe275.tagit.models.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
/**
 * Created by Cherisha on 11/28/15.
 */
public interface CommentDao extends CrudRepository<Comment, Long> {
    @Query(value="SELECT * FROM comments c  WHERE  c.comment_bookmark_id=?1",nativeQuery = true)
    Iterable<Comment> getCommentByBookmarkId(long bookmarkid);

    @Query(value="SELECT * FROM comments c  WHERE  c.commentid=?1",nativeQuery = true)
    Comment getCommentByCommentId(long commentId);
}
