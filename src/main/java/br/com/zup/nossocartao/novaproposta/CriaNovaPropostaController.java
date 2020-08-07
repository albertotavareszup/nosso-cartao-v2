package br.com.zup.nossocartao.novaproposta;

import java.net.URI;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class CriaNovaPropostaController {
	
	private EntityManager manager;
	private BloqueiaDocumentoIgualValidator bloqueiaDocumentoIgualValidator;
	
	
	
	public CriaNovaPropostaController(EntityManager manager,
			BloqueiaDocumentoIgualValidator bloqueiaDocumentoIgualValidator) {
		super();
		this.manager = manager;
		this.bloqueiaDocumentoIgualValidator = bloqueiaDocumentoIgualValidator;
	}



	@PostMapping(value = "/propostas")
	@Transactional
	public ResponseEntity<?> cria(
			@RequestBody @Valid NovaPropostaRequest request,UriComponentsBuilder builder) {
		if(!bloqueiaDocumentoIgualValidator.estaValido(request)) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
		}

		Proposta novaProposta = request.toModel();
		manager.persist(novaProposta);
		
		URI enderecoConsulta = builder.path("/propostas/{id}").build(novaProposta.getId());
		return ResponseEntity.created(enderecoConsulta).build();
	}

}
