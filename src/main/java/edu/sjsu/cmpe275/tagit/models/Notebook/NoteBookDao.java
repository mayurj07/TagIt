package edu.sjsu.cmpe275.tagit.models.Notebook;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface NoteBookDao extends CrudRepository<Notebook, Long> {

    @Query(value = "SELECT * FROM tagit.notebooks nb WHERE nb.owner_id = ?1", nativeQuery = true)
    Iterable<Notebook> findNotebookByOwnerId(long id);
}
