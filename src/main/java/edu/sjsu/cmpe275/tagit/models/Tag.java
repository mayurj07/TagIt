package edu.sjsu.cmpe275.tagit.models;

import org.hibernate.annotations.Generated;
import org.hibernate.id.UUIDGenerationStrategy;

import javax.persistence.*;

/**
 * Created by akanksha on 11/22/2015.
 */
@Entity
@Table(name = "tags")
public class Tag {

    @Column(name = "tagid")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long tagid;

    @Column(name="name")
    String tagName;

    @Column(name="bookmark_id")
    long bookmarkID;

    public long getBookmarkID() {
        return bookmarkID;
    }

    public long getTagid() {
        return tagid;
    }

    public String getTagName() {
        return tagName;
    }

    public void setBookmarkID(long bookmarkID) {
        this.bookmarkID = bookmarkID;
    }

    public void setTagid(long tagid) {
        this.tagid = tagid;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
