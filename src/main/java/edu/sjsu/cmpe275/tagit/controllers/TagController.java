package edu.sjsu.cmpe275.tagit.controllers;

import edu.sjsu.cmpe275.tagit.exceptions.BadRequestException;
import edu.sjsu.cmpe275.tagit.exceptions.EntityNotFound;
import edu.sjsu.cmpe275.tagit.interceptor.LoginInterceptor;
import edu.sjsu.cmpe275.tagit.models.Bookmark.Bookmark;
import edu.sjsu.cmpe275.tagit.models.Tag.Tag;
import edu.sjsu.cmpe275.tagit.models.User.User;
import edu.sjsu.cmpe275.tagit.services.Tag.TagService;
import edu.sjsu.cmpe275.tagit.services.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@EnableAutoConfiguration
@ComponentScan
@Component("TagController")
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @Autowired
    private UserService userService;

    @Autowired
    LoginInterceptor loginInterceptor;

       /**
         *  Method to create a Tag
         * @param tag
         * @param result
         * @return
         */
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Tag> createNewTag(@Valid @RequestBody Tag tag,BindingResult result)
    {
        if(tag.getTagName()==null||tag.getTagName().trim().equals(""))
            throw new BadRequestException(new String("Tag name is required"));
        if(tag.getBookmarkID()<=0)
            throw new BadRequestException(new String("Tag not given to invalid Bookmark"));
        if(tag.getTag_userid()<=0)
            throw new BadRequestException(new String("User id is invalid"));

        Tag newTag = new Tag();

        try {
            newTag.setBookmarkID(tag.getBookmarkID());
            newTag.setTagName(tag.getTagName());
            newTag.setTag_userid(tag.getTag_userid());
            System.out.println(" userid is : "+newTag.getTag_userid());
        }catch (Exception ex)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Tag>(tagService.createTag(newTag),HttpStatus.OK);
    }

    /**
     *  Method to get Tag by Id
     * @param tagId
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Tag> getTagById(@PathVariable(value = "id") int tagId) {
        try {
            Tag tag = tagService.getTagById(tagId);
            if (tag.getTagName() != null)
                return new ResponseEntity<Tag>(tag, HttpStatus.OK);
            else
                throw new EntityNotFound("Tag " + tagId + " not found.");

        } catch (Exception e) {
            throw new EntityNotFound("Tag " + tagId + " not found.");
        }
    }

    /**
     *  Method to update a Tag
     * @param tagId
     * @param tag
     * @param result
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<Tag> updateTag(@PathVariable(value = "id") int tagId, @Valid @RequestBody Tag tag, BindingResult result) {
        if (result.hasErrors())
            throw new BadRequestException("Bad Request Exception");

        try {
            Tag tagtoUpdate = tagService.getTagById(tagId);
            System.out.println(" tag to update :"+tagtoUpdate.getTagName());
            if (tag.getTagName() != null || !tag.getTagName().trim().equals(""))
                tagtoUpdate.setTagName(tag.getTagName());

            return new ResponseEntity<Tag>(tagService.createTag(tagtoUpdate), HttpStatus.OK);

        } catch (Exception e) {
            throw new EntityNotFound("Tag " + tagId + " cannot be updated.");
        }
    }

    /**
     *  Method to get all tags and count for a user
     * @param userid
     * @return
     */
    @RequestMapping(value ="/getAll/user/{userid}", method = RequestMethod.GET,produces = "application/json")
    public ResponseEntity<Map<String, String>> getTagsByUser (@PathVariable("userid") Integer userid )
    {
        Map<String, String> tagWithCount = new HashMap<String, String>();

        User user = userService.getUserById(userid);
        if(user.getUserid()!=0) {
            tagWithCount =  tagService.getTagCount(userid);
        }
        return new ResponseEntity<Map<String, String>>(tagWithCount,HttpStatus.OK);

    }

    /**
     * Method to get all bookmarks tagged with a tagname for the logged in user
     * @param tagName
     * @param userid
     * @return list of bookmarks
     */
    @RequestMapping("/user/{userid}/tag/{name}")
    public ResponseEntity<ArrayList<Bookmark>> getBookmarkByTag(@PathVariable(value = "name") String tagName,
                                                                @PathVariable(value = "userid") Integer userid)
    {
        ArrayList<Bookmark> bookmarksByTag = new ArrayList<Bookmark>();
        User user = userService.getUserById(userid);

        if(user.getUserid()!=0)
        {
            bookmarksByTag = tagService.getBookmarkByTag(userid,tagName);
        }
        return new ResponseEntity<ArrayList<Bookmark>>(bookmarksByTag,HttpStatus.OK);
    }

    /**
     * Method to remove a tag
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}" , method = RequestMethod.DELETE)
    public ResponseEntity removeTag(@PathVariable(value = "id")Integer id)
    {
        Tag tag = tagService.getTagById(id);
        if(tag.getTagid()>0)
        {
            tagService.removeTag(id);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

}
