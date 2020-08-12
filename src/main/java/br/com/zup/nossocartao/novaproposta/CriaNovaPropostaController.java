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

//8 pontos de complexidade
@RestController
public class CriaNovaPropostaController {

	//1
	private BloqueiaDocumentoIgualValidator bloqueiaDocumentoIgualValidator;
	//1
	private ExecutorTransacao executorTransacao;
	//1
	private AvaliaProposta avaliaProposta;

	public CriaNovaPropostaController(
			BloqueiaDocumentoIgualValidator bloqueiaDocumentoIgualValidator,
			AvaliaProposta avaliaProposta,ExecutorTransacao executorTransacao) {
		super();
		this.bloqueiaDocumentoIgualValidator = bloqueiaDocumentoIgualValidator;
		this.avaliaProposta = avaliaProposta;
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
		StatusAvaliacaoProposta avaliacao = avaliaProposta.executa(novaProposta);
		novaProposta.atualizaStatus(avaliacao);

		URI enderecoConsulta = builder.path("/propostas/{id}")
				.build(novaProposta.getId());
		return ResponseEntity.created(enderecoConsulta).build();
	}

}
