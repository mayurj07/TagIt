package edu.sjsu.cmpe275.tagit.controllers;

import edu.sjsu.cmpe275.tagit.exceptions.BadRequestException;
import edu.sjsu.cmpe275.tagit.exceptions.EntityNotFound;
import edu.sjsu.cmpe275.tagit.models.Bookmark.Bookmark;
import edu.sjsu.cmpe275.tagit.models.Notebook.Notebook;
import edu.sjsu.cmpe275.tagit.services.Bookmark.BookmarkService;
import edu.sjsu.cmpe275.tagit.services.Notebook.NotebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@Component("BookmarkController")
@RequestMapping("/bookmark")
public class BookmarkController {


    @Autowired
    private BookmarkService bookmarkService;

    @Autowired
    private NotebookService notebookService;

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")

    public ResponseEntity<Bookmark>  createBookmark(@Valid @RequestBody Bookmark bookmark, BindingResult result) {

        if (bookmark.getBookmarkName() == null || bookmark.getBookmarkName().trim().equals(""))
            throw new BadRequestException("Bookmark name is required.");
        if (bookmark.getBookmarkDescription() == null || bookmark.getBookmarkDescription().trim().equals("")) {
            throw new BadRequestException("Bookmark description must contain the valid links.");
        }
        if (bookmark.getNotebookId() <= 0 ) {
            throw new BadRequestException("Bookmark must belong to a valid notebook.");
        }


        if(notebookService.getNotebookByID(bookmark.getNotebookId())==null){
            throw new BadRequestException("Notebook Id must be a valid id.");
        }

        Bookmark bookmarkObj = null;
        try {
            bookmarkObj = new Bookmark(bookmark.getBookmarkName(), bookmark.getBookmarkDescription(),bookmark.getNotebookId());
           // bookmarkService.create(bookmark);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Bookmark>(bookmarkService.create(bookmarkObj), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<Bookmark> deleteBookmark(@PathVariable(value = "id") int bookmarkId) {
        try {
            Bookmark bookmark = bookmarkService.getBookmarkByID(bookmarkId);
            bookmarkService.removeBookmark(bookmarkId);
            return new ResponseEntity<Bookmark>(bookmark, HttpStatus.OK);
        } catch (Exception e) {
            throw new EntityNotFound("Bookmark " + bookmarkId + " not found.");
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Bookmark> getBookmark(@PathVariable(value = "id") int bookmarkId) {
        try {
            Bookmark bookmark = bookmarkService.getBookmarkByID(bookmarkId);
            if (bookmark.getBookmarkName() != null)
                return new ResponseEntity<Bookmark>(bookmark, HttpStatus.OK);
            else
                throw new EntityNotFound("Bookmark " + bookmarkId + " not found.");

        } catch (Exception e) {
            throw new EntityNotFound("Bookmark " + bookmarkId + " not found.");
        }
    }

    @RequestMapping(value = "/getAll/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ArrayList<Bookmark>> getAllBookmark(@PathVariable(value = "id") int notebookId) {

        try {
            Notebook notebook = notebookService.getNotebookByID(notebookId);
            if(notebook.getName()!=null){

                ArrayList<Bookmark> bookmarks = bookmarkService.getAllBookmarkByNotebookId(notebookId);

                return new ResponseEntity<ArrayList<Bookmark>>(bookmarks, HttpStatus.OK);
            }
            else{
                throw new EntityNotFound("Notebook " + notebookId + " not found.");
            }
        }
        catch (Exception e) {
            throw new EntityNotFound("Notebook " + notebookId + " not found.");
        }

    }
}
