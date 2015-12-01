package edu.sjsu.cmpe275.tagit.models.Share;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface ShareDao extends CrudRepository<Share, Long> {
}
