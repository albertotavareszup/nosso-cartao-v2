package br.com.zup.nossocartao.associapaypal;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.nossocartao.associacartao.Cartao;
import br.com.zup.nossocartao.criabiometria.CartaoRepository;
import br.com.zup.nossocartao.novaproposta.ExecutorTransacao;
import br.com.zup.nossocartao.outrossistemas.Integracoes;

@RestController
@Validated
public class AssociaPaypalController {

	@Autowired
	private CartaoRepository cartaoRepository;
	@Autowired
	private ExecutorTransacao executorTransacao;
	@Autowired
	private Integracoes integracoes;
	
	private static final Logger log = LoggerFactory
			.getLogger(AssociaPaypalController.class);


	@PostMapping(value = "/api/cartoes/{id}/associa-paypal")
	public ResponseEntity<?> associa(@PathVariable("id") Long id,
			@NotBlank @Email String email,
			UriComponentsBuilder componentsBuilder) {

		Cartao cartao = cartaoRepository.getOne(id);
		Optional<CarteiraPaypal> possivelPaypal = cartao.adicionaPaypal(email);
		if(possivelPaypal.isEmpty()) {
			return ResponseEntity.unprocessableEntity().build();
		}

		log.debug("Associando cartao {} com paypal",id);
		
		integracoes.associaPaypal(
				Map.of("numero", cartao.getNumero(), "email", email));
	
		log.debug("Cartao {} associado com Paypal",id);
		
		executorTransacao.atualizaEComita(cartao);
		
		log.debug("Associacao entre cartao {} e paypal feita no sistema",id);

		URI uri = componentsBuilder.path("/api/cartoes/{id}/carteiras/paypal/{idPaypal}")
				.build(id, possivelPaypal.get().getId().get());
		
		return ResponseEntity.created(uri).build();
	}

}
