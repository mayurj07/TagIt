package edu.sjsu.cmpe275.tagit.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "bookmark")
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="bookmarkid")
    private long bookmarkId;

    @NotNull
    @Column(name="bookmark_name")
    private String bookmarkName;

    @NotNull
    @Column(name="bookmark_description")
    private String bookmarkDescription;

    @NotNull
    @Column(name="notebook_id")
    private long notebookId;




    public Bookmark(){

    }

    public Bookmark(String bookmarkName, String bookmarkDescription, long notebookId) {
        this.bookmarkName = bookmarkName;
        this.bookmarkDescription = bookmarkDescription;
        this.notebookId = notebookId;
    }

    public long getBookmarkId() {
        return bookmarkId;
    }

    public String getBookmarkName() {
        return bookmarkName;
    }

    public long getNotebookId() {
        return notebookId;
    }

    public void setBookmarkId(long bookmarkId) {
        this.bookmarkId = bookmarkId;
    }

    public void setBookmarkName(String bookmarkName) {
        this.bookmarkName = bookmarkName;
    }

    public void setNotebookId(long notebookId) {
        this.notebookId = notebookId;
    }
}
