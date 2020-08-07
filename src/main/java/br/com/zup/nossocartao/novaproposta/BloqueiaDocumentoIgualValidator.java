package br.com.zup.nossocartao.novaproposta;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

@Component
public class BloqueiaDocumentoIgualValidator {

	@PersistenceContext
	private EntityManager manager;

	/**
	 * 
	 * @param request dados de uma nova proposta
	 * @return true se o documento não estiver sendo utilizado
	 */
	public boolean estaValido(NovaPropostaRequest request) {
		return manager.createQuery(
				"select p.documento from Proposta p where p.documento = :documento")
				.setParameter("documento", request.getDocumento())
				.getResultList().isEmpty();
	}

}
