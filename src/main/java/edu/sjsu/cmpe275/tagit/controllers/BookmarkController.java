package edu.sjsu.cmpe275.tagit.controllers;

import edu.sjsu.cmpe275.tagit.models.Bookmark;
import edu.sjsu.cmpe275.tagit.models.BookmarkDAO;
import edu.sjsu.cmpe275.tagit.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookmarkController {

    @RequestMapping(value= "/createBookmark", method = RequestMethod.POST)
    @ResponseBody
    public String create(@RequestParam(value = "bookmarkName") String bookmarkName,
                         @RequestParam(value = "bookmarkDescription") String bookmarkDescription,
                         @RequestParam(value = "notebookId") String notebookIdString) {

        long notebookId = Long.parseLong(notebookIdString);
        System.out.println("Harkirat Test : "+bookmarkName );
        Bookmark bookmark = null;
        try {
            bookmark = new Bookmark(bookmarkName, bookmarkDescription,notebookId);
            bookmarkDao.save(bookmark);
        }
        catch (Exception ex) {
            return "Error creating the bookmark: " + ex.toString();
        }
        return "Bookmark succesfully created! (id = " + bookmark.getBookmarkId() + ")";
    }

    @Autowired
    private BookmarkDAO bookmarkDao;
}
