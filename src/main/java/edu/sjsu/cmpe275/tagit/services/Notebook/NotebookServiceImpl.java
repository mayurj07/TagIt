package edu.sjsu.cmpe275.tagit.services.Notebook;

import edu.sjsu.cmpe275.tagit.exceptions.EntityNotFound;
import edu.sjsu.cmpe275.tagit.models.Notebook.NoteBookDao;
import edu.sjsu.cmpe275.tagit.models.Notebook.Notebook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
