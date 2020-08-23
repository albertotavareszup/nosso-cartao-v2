package br.com.zup.nossocartao.bloqueiocartao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class VerificaPrecondicaoBloqueioCartao {
	
	private static final Logger log = LoggerFactory
			.getLogger(VerificaPrecondicaoBloqueioCartao.class);


	public void executa(Long idCartao,InfoNecessariaBloqueioCartao infoBloqueio,
			Runnable executavel) {

		if(!infoBloqueio.temNavegador()) {			
			log.debug("Não rolou bloqueio do cartao {} por falta do user agent",idCartao);
			throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,"user-agent");
		}	
		
		if(!infoBloqueio.temIpRemoto()) {
			log.debug("Não rolou bloqueio do cartao {} por falta do ip remoto",idCartao);
			throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,"ip");
		}		
		
		executavel.run();
	}

}
