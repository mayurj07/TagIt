package edu.sjsu.cmpe275.tagit.models.Tag;

import edu.sjsu.cmpe275.tagit.models.Bookmark.Bookmark;
import org.omg.CORBA.Object;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


/**
 * Created by akanksha on 11/25/2015.
 */
public interface TagDao extends CrudRepository<Tag, Long> {

    @Query(value="SELECT * FROM tags t WHERE t.tag_userid=?1",nativeQuery = true)
    Iterable<Tag> findTagByUserId(long userid);

    @Query(value = "select count(*) as tagCount,name  from tags where tag_userid=?1 group by name",nativeQuery = true)
    List<java.lang.Object[]> findTagCount(long userid);

    @Query(value = "select b.bookmarkid, b.bookmark_name, b.bookmark_desc " +
            "from bookmarks b join tags t on t.bookmark_id=b.bookmarkid " +
            "where t.tag_userid=?1 and t.name=?2",nativeQuery = true)
    List<Bookmark> findBookmarkByTag(long userid,String tagName);

    @Query(value="select distinct name from tags where tagName=?1",nativeQuery = true)
    Tag findTagByName(String name);
}
