package edu.sjsu.cmpe275.tagit.controllers;

import edu.sjsu.cmpe275.tagit.models.Tag;
import edu.sjsu.cmpe275.tagit.services.Tag.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by akanksha on 11/22/2015.
 */
@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public Tag createNewTag()
    {
      //
    }

    public Tag addTag()
    {

    }


}
