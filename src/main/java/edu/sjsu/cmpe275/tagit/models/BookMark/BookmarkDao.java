package edu.sjsu.cmpe275.tagit.models.Bookmark;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
public interface BookmarkDao extends CrudRepository<Bookmark, Long> {

    @Query(value = "select b.bookmarkid, b.bookmark_name, b.bookmark_desc " +
            "from bookmarks b join notebooks n on b.notebook_id=n.notebookid " +
            "where n.owner_id=?1 ",nativeQuery = true)
    ArrayList<Bookmark> findBookmarkByUser(long userid);
}
