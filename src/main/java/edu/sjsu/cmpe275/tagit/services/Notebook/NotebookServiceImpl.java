package edu.sjsu.cmpe275.tagit.services.Notebook;

import edu.sjsu.cmpe275.tagit.exceptions.EntityNotFound;
import edu.sjsu.cmpe275.tagit.models.Notebook.NoteBookDao;
import edu.sjsu.cmpe275.tagit.models.Notebook.Notebook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service("NotebookService")
@Component
@Transactional
public class NotebookServiceImpl implements NotebookService {

    @Autowired
    private NoteBookDao noteBookDao;

    @Override
    public Notebook create(Notebook notebook) {
        noteBookDao.save(notebook);
        return notebook;
    }

    @Override
    public Notebook getNotebookByID(long id) throws EntityNotFound {
        Notebook notebook = noteBookDao.findOne(id);
        return notebook;
    }

    @Override
    public void removeNotebook(long id) throws EntityNotFound {
        noteBookDao.delete(id);
    }

    @Override
    public ArrayList<Notebook> getAllNotebooks(long id) throws EntityNotFound {
        ArrayList<Notebook> notebookArrayList = new ArrayList<Notebook>();
        Iterable<Notebook> notebooks = noteBookDao.findNotebookByOwnerId(id);
        for (Notebook nb : notebooks) {
            notebookArrayList.add(nb);
        }
        if(notebookArrayList.isEmpty()){
            throw new EntityNotFound("User does not have any notebooks.");
        }
        return notebookArrayList;
    }

    @Override
    public ArrayList<Notebook> getShared(long id) throws EntityNotFound {
        ArrayList<Notebook> notebookArrayList = new ArrayList<Notebook>();
        Iterable<Notebook> notebooks = noteBookDao.findSharedNotebook(id);
        for (Notebook nb : notebooks) {
            System.out.println("id: " + nb.getNotebookid() + " NotebookName: " + nb.getName() + " owner_id: " + nb.getOwner_id());
            notebookArrayList.add(nb);
        }
        if(notebookArrayList.isEmpty()){
            throw new EntityNotFound("User does not have any notebooks.");
        }
        return notebookArrayList;
    }

    @Override
    public boolean validateOwner(String ownerId, String notebookId){
        Notebook notebook = noteBookDao.findNotebookByNotebookId(Long.parseLong(notebookId));

        if(notebook.getOwner_id().equals(ownerId)) {
            return true;
        }
        else{
            return false;
        }
    }
}
