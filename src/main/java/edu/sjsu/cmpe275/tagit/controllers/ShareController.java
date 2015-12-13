package edu.sjsu.cmpe275.tagit.controllers;

import edu.sjsu.cmpe275.tagit.Utils.EmailNotification;
import edu.sjsu.cmpe275.tagit.exceptions.BadRequestException;
import edu.sjsu.cmpe275.tagit.exceptions.UnauthorizedException;
import edu.sjsu.cmpe275.tagit.models.Notebook.Notebook;
import edu.sjsu.cmpe275.tagit.models.Share.Share;
import edu.sjsu.cmpe275.tagit.models.User.User;
import edu.sjsu.cmpe275.tagit.services.Notebook.NotebookService;
import edu.sjsu.cmpe275.tagit.services.Share.ShareService;
import edu.sjsu.cmpe275.tagit.services.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Component("ShareController")
@RequestMapping("/share")
public class ShareController {

    @Autowired
    ShareService shareService;

    @Autowired
    NotebookService notebookService;

    @Autowired
    private UserService userService;


    @Autowired
    EmailNotification emailNotification;

    @RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Share> shareNotebook(@PathVariable(value = "id") String ownerId, @Valid @RequestBody Share share, BindingResult result) {

        if(share.getShareWithEmailId() == null || share.getShareWithEmailId().trim().equals(""))
            throw new BadRequestException("Please tell whome with you want to share.");
        if(share.getShareNotebookId() == null || share.getShareNotebookId().trim().equals(""))
            throw new BadRequestException("Valid notebook must be shared.");
        Share shareObj=null;

        User user1 =userService.getUserById(Long.parseLong(ownerId));

        User user2;
        try {
            user2 = userService.getUserByEmail(share.getShareWithEmailId());
        }
        catch(Exception e){
                emailNotification.sendEmailUserNotExist(share.getShareWithEmailId(),share.getShareWithEmailId(),user1.getName());
                throw new BadRequestException("Given user2 is not valid. Email invite has been sent to him. Once he joins the application you can again try to share with him.");

        }


        Notebook notebook = notebookService.getNotebookByID(Long.parseLong(share.getShareNotebookId()));
        if (notebook.getName() == null || notebook.getName().trim().equals("")){
            throw new BadRequestException("Given notebook is not valid. Please share a valid notebook.");
        }

        if(notebookService.validateOwner(ownerId,share.getShareNotebookId())) {


            try {
                shareObj = new Share(share.getShareWithEmailId(), share.getShareNotebookId(), share.getWrite());

            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }


            Long shareid=shareService.verifyIfAlredyShared(share.getShareWithEmailId(),Long.parseLong(share.getShareNotebookId()));

            if(shareid != null){
                throw new BadRequestException("This notebook has already shared with the user : "+ share.getShareWithEmailId());
            }

            emailNotification.sendEmailOnSharing(user2.getEmail(),user2.getName(),user1.getName(),notebook.getName());


            return new ResponseEntity<Share>(shareService.shareBookmark(shareObj), HttpStatus.CREATED);
        }else{
            throw new UnauthorizedException("Unauthorized Access....This notebook does not belong to this owner.");
        }
    }
/*
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public void unShareNotebook(@PathVariable(value = "id") String ownerId, @Valid @RequestBody Share share, BindingResult result) {

        if(share.getShareWithEmailId() == null || share.getShareWithEmailId().trim().equals(""))
            throw new BadRequestException("Please tell whome with you want to unshare.");
        if(share.getShareNotebookId() == null || share.getShareNotebookId().trim().equals(""))
            throw new BadRequestException("Valid notebook must be shared.");

        long shareid;
        User user1 =userService.getUserById(Long.parseLong(ownerId));
        User user2 = userService.getUserById(Long.parseLong(share.getShareWithEmailId()));
        if (user2.getName() == null || user2.getName().trim().equals("")){
            throw new BadRequestException("Given user2 is not valid. Please share with valid user2.");
        }

        Notebook notebook = notebookService.getNotebookByID(Long.parseLong(share.getShareNotebookId()));
        if (notebook.getName() == null || notebook.getName().trim().equals("")){
            throw new BadRequestException("Given notebook is not valid. Please share a valid notebook.");
        }

        if(notebookService.validateOwner(ownerId,share.getShareNotebookId())) {

            shareid = shareService.verifyIfAlredyShared(Long.parseLong(share.getShareWithEmailId()),Long.parseLong(share.getShareNotebookId()));
            shareService.unShareBookmark(shareid);
        }else{
            throw new UnauthorizedException("Unauthorized Access....This notebook does not belong to this owner.");
        }
    }
*/
}
