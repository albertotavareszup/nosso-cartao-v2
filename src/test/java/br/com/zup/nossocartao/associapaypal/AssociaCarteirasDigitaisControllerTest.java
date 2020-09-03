package br.com.zup.nossocartao.associapaypal;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.nossocartao.associacartao.Cartao;
import br.com.zup.nossocartao.associacartao.CarteiraSamsung;
import br.com.zup.nossocartao.criabiometria.CartaoRepository;
import br.com.zup.nossocartao.novaproposta.ExecutorTransacao;
import br.com.zup.nossocartao.outrossistemas.Integracoes;

public class AssociaCarteirasDigitaisControllerTest {
	private CartaoRepository cartaoRepository = Mockito
			.mock(CartaoRepository.class);
	private ExecutorTransacao executorTransacao = Mockito
			.mock(ExecutorTransacao.class);
	private Integracoes integracoes = Mockito.mock(Integracoes.class);
	private AssociaCarteirasDigitaisController controller = new AssociaCarteirasDigitaisController(
			cartaoRepository, executorTransacao, integracoes);
	private Cartao cartao = Mockito.mock(Cartao.class);
	{
		Mockito.when(cartao.getNumero()).thenReturn("1234");
	}

	@Test
	@DisplayName("bloqueia associacao caso já tenha paypal para aquele cartao")
	void teste1() throws Exception {

		Mockito.when(cartaoRepository.getOne(1l)).thenReturn(cartao);

		UriComponentsBuilder componentsBuilder = UriComponentsBuilder
				.newInstance();
		ResponseEntity<?> response = controller.associaPaypal(1l, "a@a.com.br",
				componentsBuilder);

		Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY,
				response.getStatusCode());

	}

	@Test
	@DisplayName("libera associacao com paypal na primeira vez")
	void teste2() throws Exception {

		Mockito.when(cartaoRepository.getOne(1l)).thenReturn(cartao);
		CarteiraPaypal paypal = Mockito.mock(CarteiraPaypal.class);
		Mockito.when(paypal.getId()).thenReturn(Optional.of(1l));
		Mockito.when(cartao.adicionaPaypal("a@a.com.br"))
				.thenReturn(Optional.of(paypal));

		UriComponentsBuilder componentsBuilder = UriComponentsBuilder
				.newInstance();
		ResponseEntity<?> response = controller.associaPaypal(1l, "a@a.com.br",
				componentsBuilder);

		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
		Assertions.assertEquals("/api/cartoes/1/carteiras/paypal/1",
				response.getHeaders().getLocation().toString());

	}

	@Test
	@DisplayName("bloqueia associacao caso já tenha samsung para aquele cartao")
	void teste3() throws Exception {

		Mockito.when(cartaoRepository.getOne(1l)).thenReturn(cartao);

		UriComponentsBuilder componentsBuilder = UriComponentsBuilder
				.newInstance();
		ResponseEntity<?> response = controller.associaSamsung(1l, "a@a.com.br",
				componentsBuilder);

		Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY,
				response.getStatusCode());

	}

	@Test
	@DisplayName("libera associacao com paypal na primeira vez")
	void teste4() throws Exception {

		Mockito.when(cartaoRepository.getOne(1l)).thenReturn(cartao);
		CarteiraSamsung samsung = Mockito.mock(CarteiraSamsung.class);
		Mockito.when(samsung.getId()).thenReturn(Optional.of(1l));
		Mockito.when(cartao.adicionaSamsung("a@a.com.br"))
				.thenReturn(Optional.of(samsung));

		UriComponentsBuilder componentsBuilder = UriComponentsBuilder
				.newInstance();
		ResponseEntity<?> response = controller.associaSamsung(1l, "a@a.com.br",
				componentsBuilder);

		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
		Assertions.assertEquals("/api/cartoes/1/carteiras/samsung/1",
				response.getHeaders().getLocation().toString());

	}
}
