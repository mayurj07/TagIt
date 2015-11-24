package edu.sjsu.cmpe275.tagit.controllers;

import edu.sjsu.cmpe275.tagit.configuration.AppConfig;
import edu.sjsu.cmpe275.tagit.exceptions.BadRequestException;
import edu.sjsu.cmpe275.tagit.exceptions.EntityNotFound;
import edu.sjsu.cmpe275.tagit.models.Notebook.Notebook;
import edu.sjsu.cmpe275.tagit.services.Notebook.NotebookService;
import edu.sjsu.cmpe275.tagit.services.Notebook.NotebookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.validation.Valid;


//@EnableAutoConfiguration
//@ComponentScan
//@Import(AppConfig.class)

@RestController
@Component("NotebookController")
@RequestMapping("/notebook")
public class NotebookController{

    @Autowired
    NotebookService notebookService;

    //Creating a moderator. POST method
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
//    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Notebook> createNotebook(@Valid @RequestBody Notebook notebook, BindingResult result) {
        if (notebook.getName() == null || notebook.getName().trim().equals(""))
            throw new BadRequestException("Notebook name required.");
        if (notebook.getOwner_id() == null || notebook.getOwner_id().trim().equals("")) {
            throw new BadRequestException("Owner Id required.");
        }

        Notebook newNB = null;
        try {
            newNB = new Notebook(notebook.getName(), notebook.getOwner_id());
            System.out.println(notebook.getNotebookid());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Notebook>(notebookService.create(newNB), HttpStatus.CREATED);
    }


    //Update a Notebook. PUT method
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
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

    //Get a Notebook by ID. GET method
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Notebook> getNotebook(@PathVariable(value = "id") int nbId) {
        try {
            Notebook noteBook = notebookService.getNotebookByID(nbId);
            if(noteBook.getName() != null)
                return new ResponseEntity<Notebook>(noteBook, HttpStatus.OK);
            else
                throw new EntityNotFound("Notebook " + nbId + " not found.");

        } catch (Exception e) {
            throw new EntityNotFound("Notebook " + nbId + " not found.");
        }
    }



}
