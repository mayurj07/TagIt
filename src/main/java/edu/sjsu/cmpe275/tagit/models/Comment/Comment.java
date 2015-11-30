package edu.sjsu.cmpe275.tagit.models.Comment;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Cherisha on 11/28/15.
 */
@Entity
@Table(name = "comments")
public class Comment {
    @NotNull
    @Column(name = "commentid")
    @JsonProperty
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long  commentid;

    @JsonProperty
    @Column(name="Description")
    private String commentDescription;


    @JsonProperty
    @Column(name="comment_user_id")
    private int commentUserId;

    @JsonProperty
    @Column(name="comment_bookmark_id" )
            private int commentBookmarkId;

    public Comment(){}

    public Comment( @JsonProperty String commentDescription, @JsonProperty int commentBookmarkId,@JsonProperty int commentUserId ) {
        this.commentDescription = commentDescription;
        this.commentBookmarkId = commentBookmarkId;
        this.commentUserId = commentUserId;
    }


    public long getCommentid() {
        return commentid;
    }

    public void setCommentid(int commentid) {
        this.commentid = commentid;
    }

    public String getCommentDescription() {
        return commentDescription;
    }

    public void setCommentDescription(String commentDescription) {
        this.commentDescription = commentDescription;
    }

    public int getCommentUserId() {
        return commentUserId;
    }

    public void setCommentUserId(int commentUserId) {
        this.commentUserId = commentUserId;
    }

    public int getCommentBookmarkId() {
        return commentBookmarkId;
    }

    public void setCommentBookmarkId(int commentBookmarkId) {
        this.commentBookmarkId = commentBookmarkId;
    }
}


















































































































































































































































































































