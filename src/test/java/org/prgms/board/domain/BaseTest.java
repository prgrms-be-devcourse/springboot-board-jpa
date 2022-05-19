package org.prgms.board.domain;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.annotations.ColumnTransformers;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class BaseTest {

	@PersistenceContext
	protected EntityManager em;

	public void clearPersistenceContext(){
		em.flush();
		em.clear();
	}
}
