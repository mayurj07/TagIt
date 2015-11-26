package edu.sjsu.cmpe275.tagit.models.Bookmark;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface BookmarkDao extends CrudRepository<Bookmark, Long> {


}
