package edu.sjsu.cmpe275.tagit.services.Notebook;


import edu.sjsu.cmpe275.tagit.models.Notebook.Notebook;

import java.util.ArrayList;

public interface NotebookService {

    public Notebook create(Notebook notebook);

    public Notebook getNotebookByID(long id);

    public void removeNotebook(long id);

    public ArrayList<Notebook> getAllNotebooks(long id);

    public ArrayList<Notebook> getShared(String id);

    public boolean validateOwner(String ownerId, String notebookId);
}