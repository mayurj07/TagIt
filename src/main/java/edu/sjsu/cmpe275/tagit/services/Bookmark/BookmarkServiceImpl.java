package edu.sjsu.cmpe275.tagit.services.Bookmark;


import edu.sjsu.cmpe275.tagit.exceptions.EntityNotFound;
import edu.sjsu.cmpe275.tagit.models.Bookmark.Bookmark;
import edu.sjsu.cmpe275.tagit.models.Bookmark.BookmarkDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service("BookmarkService")
@Transactional
public class BookmarkServiceImpl implements BookmarkService {



    @Autowired
    BookmarkDao bookmarkDao;

    @Override
    public Bookmark create(Bookmark bookmark) {

        bookmarkDao.save(bookmark);
        return bookmark;
    }

    @Override
    public Bookmark getBookmarkByID(long id) throws EntityNotFound {
        Bookmark bookmark = bookmarkDao.findOne(id);
        return bookmark;
    }

    @Override
    public void removeBookmark(long id) throws EntityNotFound {
        bookmarkDao.delete(id);
    }

    @Override

    public ArrayList<Bookmark> getAllBookmarkByNotebookId(long id) throws EntityNotFound {


        ArrayList<Bookmark> bookmark = new ArrayList<Bookmark>();
        Iterable<Bookmark> bookmarks = bookmarkDao.findAll();
        for(Bookmark b : bookmarks){
            System.out.println(b.getBookmarkId());
            if(b.getNotebookId() == id){
                bookmark.add(b);
            }
        }
        return bookmark;
    }

    @Override

    public ArrayList<Bookmark> getAllBookmarkByUserId(long id) throws EntityNotFound {


        ArrayList<Bookmark> bookmark = bookmarkDao.findBookmarkByUser(id);


        return bookmark;
    }

}

