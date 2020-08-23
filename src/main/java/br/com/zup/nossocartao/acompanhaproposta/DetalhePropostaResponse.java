package br.com.zup.nossocartao.acompanhaproposta;

import br.com.zup.nossocartao.novaproposta.Proposta;
import br.com.zup.nossocartao.novaproposta.StatusAvaliacaoProposta;

public class DetalhePropostaResponse {

	private StatusAvaliacaoProposta status;
	private Long id;

	public DetalhePropostaResponse(Proposta proposta) {
		this.status = proposta.getStatusAvaliacao();
		this.id = proposta.getId();
	}
	
	public StatusAvaliacaoProposta getStatus() {
		return status;
	}
	
	public Long getId() {
		return id;
	}

}
