package br.com.zup.nossocartao.bloqueiocartao;

import java.util.List;
import java.util.Map;

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

import br.com.zup.nossocartao.associacartao.Cartao;
import br.com.zup.nossocartao.criabiometria.CartaoRepository;
import br.com.zup.nossocartao.outrossistemas.Integracoes;
import feign.FeignException;

@RestController
public class BloqueiaCartaoController {

	private CartaoRepository cartaoRepository;

	private Integracoes integracoes;

	private static final Logger log = LoggerFactory
			.getLogger(BloqueiaCartaoController.class);

	public BloqueiaCartaoController(CartaoRepository cartaoRepository,
			Integracoes integracoes) {
		this.cartaoRepository = cartaoRepository;
		this.integracoes = integracoes;
	}

	@PostMapping(value = "/api/cartoes/{id}/bloqueia")
	@Transactional
	public void bloqueia(@PathVariable("id") Long id,
			@RequestHeader HttpHeaders headers, HttpServletRequest httpRequest)
			throws BindException {

		log.debug("Tentativa bloqueio cartao [{}] vindo do user-agent {}", id,
				headers.get(HttpHeaders.USER_AGENT));
		if (!headers.containsKey(HttpHeaders.USER_AGENT)
				|| headers.get(HttpHeaders.USER_AGENT).isEmpty()) {
			log.debug(
					"Não rolou bloqueio do cartao {id} por falta do user agent");
			throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,
					"user-agent");
		}

		String ipRemoto = httpRequest.getRemoteAddr();
		log.debug("Tentativa bloqueio cartao [{}] vindo do ip {}", id,
				ipRemoto);
		if (!StringUtils.hasText(ipRemoto)) {
			log.debug("Não rolou bloqueio do cartao {} por falta do ip remoto",
					id);
			throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,
					"ip");
		}

		String userAgent = headers.get(HttpHeaders.USER_AGENT).get(0);
		Cartao cartao = cartaoRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.UNPROCESSABLE_ENTITY));

		integracoes.bloqueiaCartaoCanais(Map.of("numero", cartao.getNumero()));
		/*
		 * Existe alguma forma de deixar travado no código que o bloqueio só
		 * é feito se a integracao foi chamada?
		 * 
		 * Existe. Estamos dispostos a fazer?
		 */
		cartao.bloqueia(userAgent, ipRemoto);

		log.debug("Solicitação de bloqueio para cartão {} finalizada", id);

	}

}
