package br.com.zup.nossocartao.bloqueiacartao;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.server.ResponseStatusException;

import br.com.zup.nossocartao.bloqueiocartao.BloqueiaCartaoController;
import br.com.zup.nossocartao.criabiometria.CartaoRepository;
import br.com.zup.nossocartao.novaproposta.Cartao;
import br.com.zup.nossocartao.outrossistemas.Integracoes;

public class BloqueiaCartaoControllerTest {

	private HttpHeaders headers = new HttpHeaders();
	private MockHttpServletRequest httpRequest = new MockHttpServletRequest();
	private CartaoRepository cartaoRepository = Mockito
			.mock(CartaoRepository.class);
	private Integracoes integracoes = Mockito.mock(Integracoes.class);
	private BloqueiaCartaoController controller = new BloqueiaCartaoController(
			cartaoRepository,integracoes);

	@Test
	@DisplayName("deveria bloquear se nao tiver user agent")
	void teste1() throws Exception {
		httpRequest.setRemoteAddr("127.0.0.1");

		Assertions.assertThrows(ResponseStatusException.class, () -> {
			controller.bloqueia(1l, headers, httpRequest);
		});

	}
	
	@Test
	@DisplayName("deveria bloquear se user agent tiver vazio")
	void teste2() throws Exception {
		httpRequest.setRemoteAddr("127.0.0.1");
		headers.set(HttpHeaders.USER_AGENT, "");
		
		Assertions.assertThrows(ResponseStatusException.class, () -> {
			controller.bloqueia(1l, headers, httpRequest);
		});
		
	}

	@Test
	@DisplayName("deveria bloquear se nao tiver ip")
	void teste3() throws Exception {
		headers.set(HttpHeaders.USER_AGENT, "chrome");

		Assertions.assertThrows(ResponseStatusException.class, () -> {
			controller.bloqueia(1l, headers, httpRequest);
		});

	}

	@Test
	@DisplayName("deveria bloquear o cartao se as precondicoes estiverem corretas")
	void teste4() throws Exception {

		httpRequest.setRemoteAddr("127.0.0.1");
		headers.set(HttpHeaders.USER_AGENT, "chrome");

		Cartao cartao = Mockito.mock(Cartao.class);
		Mockito.when(cartao.getNumero()).thenReturn("11111111111");
		Mockito.when(cartaoRepository.findById(1l))
				.thenReturn(Optional.of(cartao));

		controller.bloqueia(1l, headers, httpRequest);

		Mockito.verify(cartao).bloqueia("chrome", "127.0.0.1");
	}
}
