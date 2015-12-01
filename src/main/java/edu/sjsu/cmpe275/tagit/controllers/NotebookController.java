package edu.sjsu.cmpe275.tagit.controllers;

import edu.sjsu.cmpe275.tagit.exceptions.BadRequestException;
import edu.sjsu.cmpe275.tagit.exceptions.EntityNotFound;
import edu.sjsu.cmpe275.tagit.models.Notebook.Notebook;
import edu.sjsu.cmpe275.tagit.models.User.User;
import edu.sjsu.cmpe275.tagit.services.Notebook.NotebookService;
import edu.sjsu.cmpe275.tagit.services.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;


@RestController
@Component("NotebookController")
@RequestMapping("/notebook")
public class NotebookController {

    @Autowired
    NotebookService notebookService;

    @Autowired
    UserService userService;

    /**
     * Create a notebook. POST method
     *
     * @param notebook
     * @return New Notebook object.
     */
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Notebook> createNotebook(@Valid @RequestBody Notebook notebook, BindingResult result) {
        if (notebook.getName() == null || notebook.getName().trim().equals(""))
            throw new BadRequestException("Notebook name required.");
        if (notebook.getOwner_id() == null || notebook.getOwner_id().trim().equals(""))
            throw new BadRequestException("Owner Id required.");
        if (notebook.getAccess() == null || notebook.getAccess().trim().equals("")) {
            notebook.setAccess("yes");
        }

        Notebook newNB = null;
        try {
            newNB = new Notebook(notebook.getName(), notebook.getOwner_id(),notebook.getAccess());
            System.out.println(notebook.getNotebookid());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Notebook>(notebookService.create(newNB), HttpStatus.CREATED);
    }

    /**
     * Update a Notebook. PUT method
     *
     * @param nbId
     * @param notebook
     * @return Updated Notebook object.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<Notebook> updateNotebook(@PathVariable(value = "id") int nbId, @Valid @RequestBody Notebook notebook, BindingResult result) {
        if (result.hasErrors())
            throw new BadRequestException("Bad Request Exception");

        try {
            Notebook noteBookToUpdate = notebookService.getNotebookByID(nbId);

            if (notebook.getName() != null || !notebook.getName().trim().equals(""))
                noteBookToUpdate.setName(notebook.getName());

            if (notebook.getOwner_id() == null || notebook.getOwner_id().trim().equals(""))
                noteBookToUpdate.setOwner_id(notebook.getOwner_id());

            return new ResponseEntity<Notebook>(notebookService.create(noteBookToUpdate), HttpStatus.OK);

        } catch (Exception e) {
            throw new EntityNotFound("Notebook " + nbId + " does not exist to update.");
        }
    }

    /**
     * Get a Notebook by ID. GET method
     *
     * @param nbId
     * @return Notebook object.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Notebook> getNotebook(@PathVariable(value = "id") int nbId) {
        try {
            Notebook noteBook = notebookService.getNotebookByID(nbId);
            if (noteBook.getName() != null)
                return new ResponseEntity<Notebook>(noteBook, HttpStatus.OK);
            else
                throw new EntityNotFound("Notebook " + nbId + " not found.");

        } catch (Exception e) {
            throw new EntityNotFound("Notebook " + nbId + " not found.");
        }
    }

    /**
     * Delete a Notebook. DELETE method
     *
     * @param nbId
     * @return Deleted Notebook object.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<Notebook> deleteNotebook(@PathVariable(value = "id") int nbId) {
        try {
            Notebook noteBook = notebookService.getNotebookByID(nbId);
            notebookService.removeNotebook(nbId);
            return new ResponseEntity<Notebook>(noteBook, HttpStatus.OK);
        } catch (Exception e) {
            throw new EntityNotFound("Notebook " + nbId + " not found.");
        }
    }

    /**
     * Get all Notebooks for the user.
     * 
     * @param userId
     * @return List of notebooks
     */
    @RequestMapping(value = "/getAll/user/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ArrayList<Notebook>> getAllNotebooks(@PathVariable(value = "id") int userId) {
        try {
            User user = userService.getUserById(userId);
            if (user.getName() != null) {
                ArrayList<Notebook> notebooks = notebookService.getAllNotebooks(userId);
                return new ResponseEntity<ArrayList<Notebook>>(notebooks, HttpStatus.OK);
            } else
                throw new EntityNotFound("User " + userId + " not found.");
        } catch (Exception e) {
            throw new EntityNotFound("" + e);
        }
    }

    /**
     * Get all Notebooks for the user.
     * @param userId
     * @return List of notebooks
     */
    @RequestMapping(value = "/getShared/user/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ArrayList<Notebook>> getSharedNotebooks(@PathVariable(value = "id") int userId) {
        try {
            User user = userService.getUserById(userId);
            if (user.getName() != null) {
                ArrayList<Notebook> notebooks = notebookService.getShared(userId);
                return new ResponseEntity<ArrayList<Notebook>>(notebooks, HttpStatus.OK);
            } else
                throw new EntityNotFound("User " + userId + " not found.");
        } catch (Exception e) {
            throw new EntityNotFound("" + e);
        }
    }


}
