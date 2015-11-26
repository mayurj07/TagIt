package edu.sjsu.cmpe275.tagit.models.Bookmark;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "bookmarks")
public class Bookmark {

    @Id
    @JsonProperty
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="bookmarkid")
    private long bookmarkId;

    @NotNull
    @JsonProperty
    @Column(name="bookmark_name")
    private String bookmarkName;

    @NotNull
    @JsonProperty
    @Column(name="bookmarkDesc")
    private String bookmarkDescription;

    @NotNull
    @JsonProperty
    @Column(name="notebook_id")
    private long notebookId;


    public Bookmark(){}

    public Bookmark(@JsonProperty String bookmarkName, @JsonProperty String bookmarkDescription, @JsonProperty long notebookId) {
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

    public String getBookmarkDescription() {
        return bookmarkDescription;
    }

    public void setBookmarkDescription(String bookmarkDescription) {
        this.bookmarkDescription = bookmarkDescription;
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
