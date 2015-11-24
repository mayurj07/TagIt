package edu.sjsu.cmpe275.tagit.controllers;

import edu.sjsu.cmpe275.tagit.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created by akanksha on 11/22/2015.
 */
@Controller
public class TagController {

    @Autowired
    private TagService tagService;

}
