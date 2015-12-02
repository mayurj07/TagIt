package edu.sjsu.cmpe275.tagit.services.Bookmark;


import edu.sjsu.cmpe275.tagit.models.Bookmark.Bookmark;

import java.util.ArrayList;

public interface BookmarkService  {

    public Bookmark create(Bookmark bookmark);

    public Bookmark getBookmarkByID(long id);

    public void removeBookmark(long id);

    public ArrayList<Bookmark> getAllBookmarkByNotebookId(long id);

    public ArrayList<Bookmark> getAllBookmarkByUserId(long id);
}
