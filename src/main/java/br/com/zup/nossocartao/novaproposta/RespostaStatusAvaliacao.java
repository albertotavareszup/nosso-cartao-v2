package br.com.zup.nossocartao.novaproposta;

public enum RespostaStatusAvaliacao {

	COM_RESTRICAO(StatusAvaliacaoProposta.nao_elegivel),SEM_RESTRICAO(StatusAvaliacaoProposta.elegivel);
	
	private StatusAvaliacaoProposta statusAvaliacao;

	private RespostaStatusAvaliacao(StatusAvaliacaoProposta statusAvaliacao) {
		this.statusAvaliacao = statusAvaliacao;
	}
	
	public StatusAvaliacaoProposta getStatusAvaliacao() {
		return statusAvaliacao;
	}
}
