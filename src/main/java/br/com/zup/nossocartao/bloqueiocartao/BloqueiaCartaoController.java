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

	@Autowired
	private VerificaPrecondicaoBloqueioCartao verificaPrecondicaoBloqueioCartao;
	
	private static final Logger log = LoggerFactory
			.getLogger(BloqueiaCartaoController.class);


	@PostMapping(value = "/api/cartoes/{id}/bloqueia")
	@Transactional
	public void bloqueia(@PathVariable("id") Long id,@RequestHeader HttpHeaders headers,HttpServletRequest httpRequest) throws BindException {
		
		log.debug("Tentativa bloqueio cartao [{}]",id);		
		
		String ipRemoto = httpRequest.getRemoteAddr();
		InfoNecessariaBloqueioCartao infoBloqueio = new InfoNecessariaBloqueioCartao() {

			@Override
			public boolean temNavegador() {
				return headers.containsKey(HttpHeaders.USER_AGENT);
			}

			@Override
			public boolean temIpRemoto() {
				return StringUtils.hasText(ipRemoto);
			}
			
		};
		
		verificaPrecondicaoBloqueioCartao.executa(id,infoBloqueio,() -> {
			List<String> userAgents = headers.get(HttpHeaders.USER_AGENT);		
			
			Cartao cartao = cartaoRepository.findById(id)
					.orElseThrow(() -> new ResponseStatusException(
							HttpStatus.UNPROCESSABLE_ENTITY));
			
			cartao.bloqueia(userAgents.toString(),ipRemoto);
			log.debug("Solicitação de bloqueio para cartão {} finalizada",id);
		});
		
		
		
	}

}
