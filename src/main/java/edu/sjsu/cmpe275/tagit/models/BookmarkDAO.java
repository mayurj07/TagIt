package edu.sjsu.cmpe275.tagit.models;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface BookmarkDAO extends CrudRepository<Bookmark, Long> {
}
