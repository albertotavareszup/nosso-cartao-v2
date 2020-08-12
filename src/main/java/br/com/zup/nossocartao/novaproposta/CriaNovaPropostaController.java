package br.com.zup.nossocartao.novaproposta;

import java.net.URI;

import javax.persistence.EntityManager;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.nossocartao.outrossistemas.Integracoes;
import br.com.zup.nossocartao.outrossistemas.NovoDocumentoRequest;

//8 pontos de complexidade
@RestController
public class CriaNovaPropostaController {

	private EntityManager manager;
	//1
	private BloqueiaDocumentoIgualValidator bloqueiaDocumentoIgualValidator;
	//1
	private Integracoes integracoes;
	//1
	private ExecutorTransacao executorTransacao;

	public CriaNovaPropostaController(EntityManager manager,
			BloqueiaDocumentoIgualValidator bloqueiaDocumentoIgualValidator,
			Integracoes integracoes,ExecutorTransacao executorTransacao) {
		super();
		this.manager = manager;
		this.bloqueiaDocumentoIgualValidator = bloqueiaDocumentoIgualValidator;
		this.integracoes = integracoes;
		this.executorTransacao = executorTransacao;

	}

	@PostMapping(value = "/propostas")
	public ResponseEntity<?> cria(
			//1
			@RequestBody @Valid NovaPropostaRequest request,
			UriComponentsBuilder builder) {
		//1
		if (!bloqueiaDocumentoIgualValidator.estaValido(request)) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
		}

		//1
		Proposta novaProposta = request.toModel();
		executorTransacao.salvaEComita(novaProposta);

		//1
		String resultadoAvaliacao = integracoes
				.avalia(new NovoDocumentoRequest(novaProposta));

		//1 por conta do retorno da enum nova
		novaProposta.atualizaStatus(RespostaStatusAvaliacao
				.valueOf(resultadoAvaliacao).getStatusAvaliacao());

		URI enderecoConsulta = builder.path("/propostas/{id}")
				.build(novaProposta.getId());
		return ResponseEntity.created(enderecoConsulta).build();
	}

}
