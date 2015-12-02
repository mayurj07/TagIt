package edu.sjsu.cmpe275.tagit.services.Tag;

import edu.sjsu.cmpe275.tagit.exceptions.EntityNotFound;
import edu.sjsu.cmpe275.tagit.models.Bookmark.Bookmark;
import edu.sjsu.cmpe275.tagit.models.Tag.TagDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.sjsu.cmpe275.tagit.models.Tag.Tag;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.lang.Object;
import java.util.Map;

/**
 * Created by akanksha on 11/22/2015.
 */
@Service("tagService")
@Transactional
public class TagServiceImpl implements TagService {

    @Autowired
    TagDao tagDao;

    /**
     * Method to create a tag
     */
    @Override
    public Tag createTag(Tag tag)
    {
       tagDao.save(tag);
        return tag;
    }

    @Override
    public Tag getTagById(long tagId) throws EntityNotFound
    {
        Tag tag = tagDao.findOne(tagId);
        return tag;
    }

    @Override
    public List<Object[]> getTagByName(String tagName) {
        return null;
    }

    @Override
    public ArrayList<Tag> getTagByUserId(long userId) {
        ArrayList<Tag> tags = (ArrayList<Tag>) tagDao.findTagByUserId(userId);
        return tags;
    }

    public Map<String, String> getTagCount(long userid)
    {
        List<Object[]> tags = (ArrayList<Object[]>)tagDao.findTagCount(userid);

        Map<String, String > tagCount = new HashMap<String, String >();

        for(int i =0; i<tags.size(); i++){
            tagCount.put(tags.get(i)[1].toString(),  tags.get(i)[0].toString());
        }

        for(String s: tagCount.keySet()){
            System.out.println("key: " + s + ", value: " + tagCount.get(s));
        }
        return tagCount;
    }

    @Override
    public ArrayList<Bookmark> getBookmarkByTag(long userid, String tagName) {
        ArrayList<Bookmark> bookmarksList = (ArrayList<Bookmark>)tagDao.findBookmarkByTag(userid,tagName);
        return bookmarksList;
    }

    @Override
    public void removeTag(long tagId)
    {
       tagDao.delete(tagId);

    }
}
