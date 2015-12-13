package edu.sjsu.cmpe275.tagit.models.Share;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface ShareDao extends CrudRepository<Share, Long> {

    @Query(value = "select s.shareid " +
            "from share s " +
            "where s.share_user_id=?1 and s.share_notebook_id=?2",nativeQuery = true)
    Long getShareByNotebookIdAndSharedOwnerId(String sharedOwnerId,long notebookId);
}
