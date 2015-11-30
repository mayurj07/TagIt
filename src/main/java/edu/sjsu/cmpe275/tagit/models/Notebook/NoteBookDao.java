package edu.sjsu.cmpe275.tagit.models.Notebook;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NoteBookDao extends CrudRepository<Notebook, Long> {

//    @Query("select '*' from notebooks nb where nb.owner_id = ?0", nativeQuery = true)

//    @Query(value = "SELECT * FROM notebooks WHERE owner_id = ?0", nativeQuery = true)
    @Query(value = "SELECT * FROM tagit.notebooks nb WHERE nb.owner_id = ?1", nativeQuery = true)
    Iterable<Notebook> findNotebookByOwnerId(long id);

    @Query(value = "select n.notebookid,n.name,n.owner_id, s.write from notebooks n " +
            "join share s on s.share_notebook_id=n.notebookid " +
            "where s.share_user_id=?1",nativeQuery = true)
    Iterable<Notebook> findSharedNotebook(long id);
}
