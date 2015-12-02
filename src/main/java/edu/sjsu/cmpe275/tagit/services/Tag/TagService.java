package edu.sjsu.cmpe275.tagit.services.Tag;

import edu.sjsu.cmpe275.tagit.models.Bookmark.Bookmark;
import edu.sjsu.cmpe275.tagit.models.Tag.Tag;

import java.util.ArrayList;
import java.util.List;
import java.lang.Object;
import java.util.Map;

/**
 * Created by akanksha on 11/22/2015.
 */
public interface TagService {
    public Tag createTag(Tag tag);

    public Tag getTagById(long tagId);

    public List<Object[]> getTagByName(String tagName);

    public ArrayList<Tag> getTagByUserId(long userId);

    public Map<String, String> getTagCount(long userId);

    public ArrayList<Bookmark> getBookmarkByTag(long userid,String tagName);

    public void removeTag(long tagId);
}
