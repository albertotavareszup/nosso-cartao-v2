package br.com.zup.nossocartao.associacartao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import br.com.zup.nossocartao.outrossistemas.IntegracoesInternas;

@Controller
public class AssociaPropostaCartaoScheduler {
	
	@Autowired
	private IntegracoesInternas integracoesInternas;

	@Scheduled(fixedDelayString = "${periodicidade.associa-proposta-cartao}")
	public void associa() {
		integracoesInternas.associaPropostasECartoes();
	}
}
