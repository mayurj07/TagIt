package edu.sjsu.cmpe275.tagit.controllers;
import edu.sjsu.cmpe275.tagit.exceptions.BadRequestException;
import edu.sjsu.cmpe275.tagit.exceptions.EntityNotFound;
import edu.sjsu.cmpe275.tagit.models.Bookmark.Bookmark;
import edu.sjsu.cmpe275.tagit.models.Comment.Comment;
//import edu.sjsu.cmpe275.tagit.models.Bookmark.Bookmark;
import edu.sjsu.cmpe275.tagit.services.Bookmark.BookmarkService;
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

    @Autowired
    private BookmarkService bookmarkService;

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

    @RequestMapping(value = "/{id}" , method = RequestMethod.DELETE)
    public ResponseEntity removeComment(@PathVariable(value = "id")Integer id)
    {
        Comment comment = commentService.getCommentByCommentId(id);
        try {
            if (comment.getCommentid() > 0) {
                commentService.removeComment(id);
                return new ResponseEntity(HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            throw new EntityNotFound("Comment " + id + " not found.");
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<Comment> updateCommentDescription(@PathVariable(value = "id") long commentId, @Valid @RequestBody Comment comment, BindingResult result) {
        if (result.hasErrors())
            throw new BadRequestException("Bad Request Exception");

        try {
            Comment commentToUpdate = commentService.getCommentByCommentId(commentId);
            System.out.println(" comment to update :"+commentToUpdate.getCommentDescription());
            if (comment.getCommentDescription() != null || !comment.getCommentDescription().trim().equals(""))
                commentToUpdate.setCommentDescription(comment.getCommentDescription());

            return new ResponseEntity<Comment>(commentService.updateComment(commentToUpdate), HttpStatus.OK);

        } catch (Exception e) {
            throw new EntityNotFound("Comment " + commentId + " cannot be updated.");
        }
    }

    @RequestMapping(value = "/getAll/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ArrayList<Comment>> getAllComment(@PathVariable(value = "id") int bookmarkId) {

        try {
            Bookmark bookmark = bookmarkService.getBookmarkByID(bookmarkId);
            System.out.println("Got bookmark");
            if (bookmark.getBookmarkName() != null) {
                System.out.println("name is valid");

                ArrayList<Comment> comment = commentService.getCommentByBookmarkId(bookmarkId);

                return new ResponseEntity<ArrayList<Comment>>(comment, HttpStatus.OK);
            } else {
                throw new EntityNotFound("Bookmark " + bookmarkId + " not found.");
            }
        } catch (Exception e) {
            throw new EntityNotFound(" Exception Bookmark " + bookmarkId + " not found.");
        }
    }
}
