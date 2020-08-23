package br.com.zup.nossocartao.associacartao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.nossocartao.novaproposta.ExecutorTransacao;
import br.com.zup.nossocartao.novaproposta.Proposta;
import br.com.zup.nossocartao.novaproposta.StatusAvaliacaoProposta;
import br.com.zup.nossocartao.outrossistemas.DocumentoProposta;
import br.com.zup.nossocartao.outrossistemas.Integracoes;

@RestController
public class AssociaPropostaCartaoScheduler {
	
	@Autowired
	private Integracoes integracoes;
	@Autowired
	private ExecutorTransacao executorTransacao;
	@Autowired
	private PropostaRepository propostaRepository;
	
	private static final Logger log = LoggerFactory
			.getLogger(AssociaPropostaCartaoScheduler.class);


	@Scheduled(fixedDelayString = "${periodicidade.associa-proposta-cartao}")
	@GetMapping("/executa-associacao-proposta")
	public void associa() {
		List<Proposta> propostas = propostaRepository.todasSemCartao(StatusAvaliacaoProposta.elegivel);
		
		log.info("Existem {} propostas para avaliar",propostas.size());
		
		for (Proposta proposta : propostas) {
			String numero = integracoes.buscaNumeroCartao(new DocumentoProposta(proposta.getDocumento()));
			proposta.associaCartao(numero);
			executorTransacao.atualizaEComita(proposta);
			log.info("Proposta [{}] teve cartão associada",proposta.getId());
		}
	}	
}
