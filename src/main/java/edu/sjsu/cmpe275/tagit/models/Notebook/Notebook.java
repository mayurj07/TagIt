package edu.sjsu.cmpe275.tagit.models.Notebook;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Email;

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

    //constructors
    public Notebook(){}

    public Notebook( @JsonProperty String name,
                     @JsonProperty String owner_id){
        this.name = name;
        this.owner_id = owner_id;
    }

    //setter and getters


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
