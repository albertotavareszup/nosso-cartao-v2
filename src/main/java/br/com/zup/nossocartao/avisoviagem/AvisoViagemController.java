package br.com.zup.nossocartao.avisoviagem;

import java.net.URI;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.nossocartao.criabiometria.CartaoRepository;
import br.com.zup.nossocartao.novaproposta.Cartao;

@RestController
public class AvisoViagemController {

	@Autowired
	private CartaoRepository cartaoRepository;
	@Autowired
	private EntityManager manager;

	@PostMapping(value = "/api/cartoes/{id}/aviso-viagem")
	@Transactional
	public ResponseEntity<?> avisa(@PathVariable("id") Long id,
			@RequestBody NovoAvisoViagemRequest request,
			@RequestHeader(HttpHeaders.USER_AGENT) String navegador,
			HttpServletRequest httpRequest,UriComponentsBuilder uriBuilder) {
		
		Cartao cartao = cartaoRepository.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		AvisoViagem avisoViagem = new AvisoViagem(cartao,request.getDestino(),navegador,httpRequest.getRemoteAddr());
		manager.persist(avisoViagem);
		
		//eu sei que aqui o avisoViagem tem id
		URI enderecoAviso = uriBuilder.path("/api/avisos/{id}").build(avisoViagem.getId().get());
		return ResponseEntity.created(enderecoAviso).build();
		
	}

}
