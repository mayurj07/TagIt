package edu.sjsu.cmpe275.tagit.services.Bookmark;


import edu.sjsu.cmpe275.tagit.models.Bookmark.Bookmark;
import edu.sjsu.cmpe275.tagit.models.Notebook.Notebook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface BookmarkService  {

    public Bookmark create(Bookmark bookmark);

    public Bookmark getBookmarkByID(long id);

    public void removeBookmark(long id);

    public ArrayList<Bookmark> getAllBookmarkByNotebookId(long id);
}
