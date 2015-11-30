package edu.sjsu.cmpe275.tagit.models.Notebook;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "notebooks")
public class Notebook {

    @Id
    @JsonProperty
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="notebookid")
    private long notebookid;

    @NotNull
    @JsonProperty
    @Column(name="name")
    private String name;

    @NotNull
    @JsonProperty
    @Column(name="owner_id")
    private String owner_id;


    @JsonProperty
    @Column(name="access")
    private String access;

    //constructors
    public Notebook(){}

    public Notebook( @JsonProperty String name,
                     @JsonProperty String owner_id,
                     @JsonProperty String access){
        this.name = name;
        this.owner_id = owner_id;
        this.access = access;
    }

    //setter and getters
    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public long getNotebookid() {
        return notebookid;
    }

    public void setNotebookid(long notebookid) {
        this.notebookid = notebookid;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
