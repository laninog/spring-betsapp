package com.betsapp.mgr.repositories;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.betsapp.mgr.domain.Match;

@Repository
public class MatchRepository {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Match> findAll() {
		return em.createQuery("from Match").getResultList();
	}
	
	public Match findById(Long id) {
		return em.find(Match.class, id);
	}

	public Optional<Match> findByIdOptional(Long id) {
		return Optional.ofNullable(em.find(Match.class, id));
	}
	
	@Transactional
	public void saveOrUpdate(Match match) {
		if (match.getId() != null && match.getId() > 0) {
			em.merge(match);
		} else {
			em.persist(match);
		}
	}
	
}
