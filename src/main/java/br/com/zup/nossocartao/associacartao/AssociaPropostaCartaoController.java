package br.com.zup.nossocartao.associacartao;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.nossocartao.novaproposta.ExecutorTransacao;
import br.com.zup.nossocartao.novaproposta.Proposta;
import br.com.zup.nossocartao.outrossistemas.DocumentoProposta;
import br.com.zup.nossocartao.outrossistemas.Integracoes;

@RestController
public class AssociaPropostaCartaoController {

	@Autowired
	private Integracoes integracoes;
	@Autowired
	private EntityManager manager;
	@Autowired
	private ExecutorTransacao executorTransacao;

	@PostMapping(value = "/api-interna/associa-proposta-cartao")
	public void associa() {
		List<Proposta> propostas = manager.createQuery("select p from Proposta p", Proposta.class)
				.getResultList();
		
		for (Proposta proposta : propostas) {
			String numero = integracoes.buscaNumeroCartao(new DocumentoProposta(proposta.getDocumento()));
			System.out.println(numero);
		}
	}

}
