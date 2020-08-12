package br.com.zup.nossocartao.novaproposta;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

@Component
public class ExecutorTransacao {

	@PersistenceContext
	private EntityManager manager;
	
	@Transactional
	public <T> T salvaEComita(T objeto) {
		manager.persist(objeto);
		return objeto;
	}

	@Transactional
	public <T> T atualizaEComita(T objeto) {
		return manager.merge(objeto);
	}
}
