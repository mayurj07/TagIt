package edu.sjsu.cmpe275.tagit.services.Share;


import edu.sjsu.cmpe275.tagit.exceptions.EntityNotFound;
import edu.sjsu.cmpe275.tagit.models.Share.Share;
import edu.sjsu.cmpe275.tagit.models.Share.ShareDao;
import org.springframework.beans.factory.annotation.Autowired;

public class ShareServiceImpl implements ShareService{

    @Autowired
    ShareDao shareDao;

    @Override
    public Share shareBookmark(Share share)throws EntityNotFound {
        shareDao.save(share);
        return share;
    }
    @Override
    public long unShareBookmark(long shareid)throws EntityNotFound {
        shareDao.delete(shareid);
        return shareid;
    }

    @Override
    public Long verifyIfAlredyShared(String sharedOwnerId, long notebookId)throws EntityNotFound {

        Long shareid;

        shareid = shareDao.getShareByNotebookIdAndSharedOwnerId(sharedOwnerId, notebookId);
       //throw new BadRequestException("This notebook has already shared with the given user");

        return shareid;
    }

}
