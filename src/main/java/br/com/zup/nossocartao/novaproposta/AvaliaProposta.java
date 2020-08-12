package br.com.zup.nossocartao.novaproposta;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import br.com.zup.nossocartao.outrossistemas.Integracoes;
import br.com.zup.nossocartao.outrossistemas.NovoDocumentoRequest;

@Service
@Validated
public class AvaliaProposta {
	
	@Autowired
	//1
	private Integracoes integracoes;

	public StatusAvaliacaoProposta executa(@NotNull @Validated Proposta proposta) {
		//1
		String resultadoAvaliacao = integracoes
				.avalia(new NovoDocumentoRequest(proposta));

		//1 por conta do retorno da enum nova
		return RespostaStatusAvaliacao.valueOf(resultadoAvaliacao).getStatusAvaliacao();
	}

	
}
