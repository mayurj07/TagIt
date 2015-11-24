package edu.sjsu.cmpe275.tagit.services.Bookmark;


import edu.sjsu.cmpe275.tagit.models.Bookmark.Bookmark;
import edu.sjsu.cmpe275.tagit.models.Bookmark.BookmarkDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
