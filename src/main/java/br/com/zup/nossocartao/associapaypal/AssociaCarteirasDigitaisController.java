package br.com.zup.nossocartao.associapaypal;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.nossocartao.associacartao.Cartao;
import br.com.zup.nossocartao.associacartao.CarteiraSamsung;
import br.com.zup.nossocartao.criabiometria.CartaoRepository;
import br.com.zup.nossocartao.novaproposta.ExecutorTransacao;
import br.com.zup.nossocartao.outrossistemas.Integracoes;

@RestController
@Validated
public class AssociaCarteirasDigitaisController {

	private CartaoRepository cartaoRepository;
	private ExecutorTransacao executorTransacao;
	private Integracoes integracoes;
	
	private static final Logger log = LoggerFactory
			.getLogger(AssociaCarteirasDigitaisController.class);
	

	public AssociaCarteirasDigitaisController(CartaoRepository cartaoRepository,
			ExecutorTransacao executorTransacao, Integracoes integracoes) {
		super();
		this.cartaoRepository = cartaoRepository;
		this.executorTransacao = executorTransacao;
		this.integracoes = integracoes;
	}




	@PostMapping(value = "/api/cartoes/{id}/associa-paypal")
	public ResponseEntity<?> associaPaypal(@PathVariable("id") Long id,
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
	
	@PostMapping(value = "/api/cartoes/{id}/associa-samsung")
	public ResponseEntity<?> associaSamsung(@PathVariable("id") Long id,
			@NotBlank @Email String email,
			UriComponentsBuilder componentsBuilder) {
		
		Cartao cartao = cartaoRepository.getOne(id);
		Optional<CarteiraSamsung> possivelPaypal = cartao.adicionaSamsung(email);
		if(possivelPaypal.isEmpty()) {
			return ResponseEntity.unprocessableEntity().build();
		}
		
		log.debug("Associando cartao {} com samsung",id);
		
		integracoes.associaPaypal(
				Map.of("numero", cartao.getNumero(), "email", email));
		
		log.debug("Cartao {} associado com samsung",id);
		
		executorTransacao.atualizaEComita(cartao);
		
		log.debug("Associacao entre cartao {} e samsung feita no sistema",id);
		
		URI uri = componentsBuilder.path("/api/cartoes/{id}/carteiras/samsung/{idPaypal}")
				.build(id, possivelPaypal.get().getId().get());
		
		return ResponseEntity.created(uri).build();
	}

}
