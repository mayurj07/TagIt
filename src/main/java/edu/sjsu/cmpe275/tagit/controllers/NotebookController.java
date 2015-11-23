package edu.sjsu.cmpe275.tagit.controllers;

import edu.sjsu.cmpe275.tagit.execptions.BadRequestException;
import edu.sjsu.cmpe275.tagit.models.Notebook.Notebook;
import edu.sjsu.cmpe275.tagit.services.Notebook.NotebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/notebook")
public class NotebookController {

    @Autowired
    NotebookService notebookService;

    //Creating a moderator. POST method
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Notebook> createNotebook(@Valid @RequestBody Notebook notebook, BindingResult result)
    {
        if(notebook.getName()==null || notebook.getName().trim().equals(""))
            throw new BadRequestException("Notebook name required.");
        if (notebook.getOwner_id()==null || notebook.getOwner_id().trim().equals("")){
            throw new BadRequestException("Owner Id required.");
        }

        Notebook newNB = null;
        try {
            newNB = new Notebook(notebook.getName(), notebook.getOwner_id());
            System.out.println(notebook.getNotebookid());
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Notebook>(notebookService.create(newNB), HttpStatus.CREATED);
    }

}
