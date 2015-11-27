package edu.sjsu.cmpe275.tagit.models.Tag;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Generated;
import org.hibernate.id.UUIDGenerationStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by akanksha on 11/22/2015.
 */
@Entity
@Table(name = "tags")
public class Tag {

    @Column(name = "tagid")
    @JsonProperty
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long tagid;

    @JsonProperty
    @NotNull
    @Column(name="name")
    private String tagName;

    @JsonProperty
    @Column(name="bookmark_id")
    private long bookmarkID;

    public long getTag_userid() {
        return tag_userid;
    }

    public void setTag_userid(long tag_userid) {
        this.tag_userid = tag_userid;
    }

    @JsonProperty
    @Column(name="tag_userid")
    private long tag_userid;

    @JsonProperty
    @Transient
    private int tagCount;

    public int getTagCount() {
        return tagCount;
    }

    public void setTagCount(int tagCount) {
        this.tagCount = tagCount;
    }


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
