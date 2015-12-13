package edu.sjsu.cmpe275.tagit.models.Share;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "share")
public class Share {

    @Id
    @JsonProperty
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="shareid")
    private long shareId;

    @NotNull
    @JsonProperty
    @Column(name="share_user_id")
    private String shareWithEmailId;

    @NotNull
    @JsonProperty
    @Column(name="share_notebook_id")
    private String shareNotebookId;

    @NotNull
    @JsonProperty
    @Column(name="access")
    private String write;


    public Share(){}


    public Share(@JsonProperty String shareWithEmailId, @JsonProperty String shareNotebookId, @JsonProperty String write) {
        this.shareWithEmailId = shareWithEmailId;
        this.shareNotebookId = shareNotebookId;
        this.write = write;
    }

    public long getShareId() {
        return shareId;
    }

    public String getShareWithEmailId() {
        return shareWithEmailId;
    }

    public String getShareNotebookId() {
        return shareNotebookId;
    }

    public String getWrite() {
        return write;
    }

    public void setShareId(long shareId) {
        this.shareId = shareId;
    }

    public void setShareWithEmailId(String shareWithEmailId) {
        this.shareWithEmailId = shareWithEmailId;
    }

    public void setShareNotebookId(String shareNotebookId) {
        this.shareNotebookId = shareNotebookId;
    }

    public void setWrite(String write) {
        this.write = write;
    }
}
