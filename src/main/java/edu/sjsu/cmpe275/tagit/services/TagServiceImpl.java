package edu.sjsu.cmpe275.tagit.services;

import org.springframework.stereotype.Service;
import edu.sjsu.cmpe275.tagit.models.Tag;
/**
 * Created by akanksha on 11/22/2015.
 */
@Service("tagService")
public class TagServiceImpl implements TagService{
    public Tag createTag(Tag tag)
    {
    //
        return new Tag();
    }
}
