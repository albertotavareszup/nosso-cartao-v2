package br.com.zup.nossocartao.novaproposta;

import java.math.BigDecimal;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

public class CriaNovaPropostaControllerTest {

	private BloqueiaDocumentoIgualValidator validadorDocumento = Mockito
			.mock(BloqueiaDocumentoIgualValidator.class);
	private AvaliaProposta avaliaProposta = Mockito.mock(AvaliaProposta.class);
	private ExecutorTransacao executaTransacao = Mockito
			.mock(ExecutorTransacao.class);
	private CriaNovaPropostaController controller = new CriaNovaPropostaController(
			validadorDocumento, avaliaProposta, executaTransacao);

	@Test
	@DisplayName("nao pode processar proposta com documento igual")
	void teste1() {
		NovaPropostaRequest request = new NovaPropostaRequest("email@eamil.com",
				"Alberto", "endereco", new BigDecimal("1000"), "111111111");
		UriComponentsBuilder builder = UriComponentsBuilder.newInstance();

		Assertions.assertThrows(ResponseStatusException.class, () -> {
			controller.cria(request, builder);
		});
	}

	@Test
	@DisplayName("deve salvar se o documento está válido")
	void teste2() {
		NovaPropostaRequest request = new NovaPropostaRequest("email@eamil.com",
				"Alberto", "endereco", new BigDecimal("1000"), "111111111");

		UriComponentsBuilder builder = UriComponentsBuilder.newInstance();

		/*
		 * O Mockito verifica se o objeto que representa a request de nova
		 * proposta que vai ser passado como argumento lá dentro do método cria
		 * é de fato o mesmo objeto que está sendo esperando na definicao da
		 * expectativa
		 */
		Mockito.when(validadorDocumento.estaValido(request)).thenReturn(true);
		ResponseEntity<?> response = controller.cria(request, builder);

		Proposta propostaQueDeviaSerGerada = request.toModel();
		Mockito.verify(executaTransacao).salvaEComita(propostaQueDeviaSerGerada);
		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}
}
