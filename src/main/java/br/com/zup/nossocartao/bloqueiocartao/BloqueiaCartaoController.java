package br.com.zup.nossocartao.bloqueiocartao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.zup.nossocartao.criabiometria.CartaoRepository;
import br.com.zup.nossocartao.novaproposta.Cartao;

@RestController
public class BloqueiaCartaoController {

	@Autowired
	private CartaoRepository cartaoRepository;
	
	private static final Logger log = LoggerFactory
			.getLogger(BloqueiaCartaoController.class);


	@PostMapping(value = "/api/cartoes/{id}/bloqueia")
	@Transactional
	public void bloqueia(@PathVariable("id") Long id,@RequestHeader HttpHeaders headers,HttpServletRequest httpRequest) throws BindException {
		
		log.debug("Tentativa bloqueio cartao [{}] vindo do user-agent {}",id,headers.get(HttpHeaders.USER_AGENT));
		if(!headers.containsKey(HttpHeaders.USER_AGENT)) {			
			log.debug("Não rolou bloqueio do cartao {id} por falta do user agent");
			throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,"user-agent");
		}
		
		String ipRemoto = httpRequest.getRemoteAddr();
		log.debug("Tentativa bloqueio cartao [{}] vindo do ip {}",id,ipRemoto);
		if(!StringUtils.hasText(ipRemoto)) {
			log.debug("Não rolou bloqueio do cartao {id} por falta do ip remoto");
			throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,"ip");
		}
		
//		verificaPrecondicaoBloqueioCartao.executa(infoNecessariaBloqueioCartao,(info) -> {
//			//codigo vem aqui
//		});
		
		List<String> userAgents = headers.get(HttpHeaders.USER_AGENT);		
		
		Cartao cartao = cartaoRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.UNPROCESSABLE_ENTITY));
		
		cartao.bloqueia(userAgents.toString(),ipRemoto);
		
		log.debug("Solicitação de bloqueio para cartão {} finalizada",id);
		
	}

}
