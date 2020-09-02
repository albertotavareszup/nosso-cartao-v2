package br.com.zup.nossocartao.recuperacaosenha;

import java.net.URI;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.nossocartao.associacartao.Cartao;
import br.com.zup.nossocartao.criabiometria.CartaoRepository;

@RestController
public class RecuperaSenhaController {

	@Autowired
	private CartaoRepository cartaoRepository;
	@Autowired
	private EntityManager manager;

	@PostMapping(value = "/api/cartoes/{id}/recupera-senha")
	@Transactional
	public ResponseEntity<?> recupera(@PathVariable Long id,
			@RequestHeader(HttpHeaders.USER_AGENT) String navegador,
			HttpServletRequest httpRequest, UriComponentsBuilder uriBuilder) {

		Cartao cartao = cartaoRepository.getOne(id);
		RecuperaSenha recuperaSenha = new RecuperaSenha(cartao,
				httpRequest.getRemoteAddr(), navegador);
		
		manager.persist(recuperaSenha);
		
		URI uri = uriBuilder.path("/api/cartoes/{idCartao}/recupera-senha/{idSenha}")
				.build(id, recuperaSenha.getId().get());
		
		return ResponseEntity.created(uri).build();
	}

}
