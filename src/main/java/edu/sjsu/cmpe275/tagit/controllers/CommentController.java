package edu.sjsu.cmpe275.tagit.controllers;
import edu.sjsu.cmpe275.tagit.exceptions.BadRequestException;
import edu.sjsu.cmpe275.tagit.exceptions.EntityNotFound;
import edu.sjsu.cmpe275.tagit.models.Comment.Comment;
//import edu.sjsu.cmpe275.tagit.models.Bookmark.Bookmark;
import edu.sjsu.cmpe275.tagit.services.Comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.ArrayList;
/**
 * Created by Cherisha on 11/28/15.
 */


@RestController
@Component("CommentController")
@RequestMapping("/comment")

public class CommentController {


    @Autowired
    private CommentService commentService;


    @RequestMapping(method = RequestMethod.POST, produces = "application/json")

    public ResponseEntity<Comment>  createComment(@Valid @RequestBody Comment comment, BindingResult result) {



        if (comment.getCommentBookmarkId()<= 0 ) {
            throw new BadRequestException("Bookmark must belong to a valid bookmark.");
        }
        if (comment.getCommentUserId()<= 0 ) {
            throw new BadRequestException("invalid user id.");
        }


        Comment commentObj = null;
        try {
            commentObj = new Comment(comment.getCommentDescription(),comment.getCommentUserId(),comment.getCommentBookmarkId());
            // commentService.create(comment);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Comment>(commentService.createComment(commentObj), HttpStatus.CREATED);
    }


  /* @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<Comment> deleteComment(@PathVariable(value = "id") int commentId) {
        try {
             ArrayList<Comment> comment = commentService.getCommentByBookmarkId(commentId);
            commentService.removeComment(commentId);
            return new ResponseEntity<Comment>(comment, HttpStatus.OK);
        } catch (Exception e) {
            throw new EntityNotFound("comment " + commentId + " not found.");
        }
    }
*/




}
