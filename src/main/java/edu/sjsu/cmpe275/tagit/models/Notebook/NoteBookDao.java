package edu.sjsu.cmpe275.tagit.models.Notebook;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface NoteBookDao extends CrudRepository<Notebook, Long> {

    @Query(value = "SELECT * FROM tagit.notebooks nb WHERE nb.owner_id = ?1", nativeQuery = true)
    Iterable<Notebook> findNotebookByOwnerId(long id);

    @Query(value = "select n.notebookid,n.name,n.owner_id, s.access from notebooks n " +
            "join share s on s.share_notebook_id=n.notebookid " +
            "where s.share_user_id=?1",nativeQuery = true)
    Iterable<Notebook> findSharedNotebook(long id);
}
