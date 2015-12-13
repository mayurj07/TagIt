package edu.sjsu.cmpe275.tagit.services.Share;

import edu.sjsu.cmpe275.tagit.models.Share.Share;

/**
 * Created by harkirat singh on 11/29/2015.
 */
public interface ShareService {

    public Share shareBookmark(Share share);
    public long unShareBookmark(long shareid);
    public Long verifyIfAlredyShared(String sharedOwnerId, long notebookId);
}
