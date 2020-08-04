package br.com.zup.nossocartao.novaproposta;

import java.net.URI;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class CriaNovaPropostaController {
	
	@PersistenceContext
	private EntityManager manager;

	@PostMapping(value = "/propostas")
	@Transactional
	public ResponseEntity<?> cria(
			@RequestBody @Valid NovaPropostaRequest request,UriComponentsBuilder builder) {

		Proposta novaProposta = request.toModel();
		manager.persist(novaProposta);
		
		URI enderecoConsulta = builder.path("/propostas/{id}").build(novaProposta.getId());
		return ResponseEntity.created(enderecoConsulta).build();
	}

}
