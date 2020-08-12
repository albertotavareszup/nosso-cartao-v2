package br.com.zup.nossocartao.novaproposta;

public enum StatusAvaliacaoProposta {

	nao_elegivel, elegivel;

	public static StatusAvaliacaoProposta nova(String resultadoAvaliacao) {
		switch (resultadoAvaliacao.toUpperCase()) {
		case "SEM_RESTRICAO":
			return StatusAvaliacaoProposta.elegivel;
		case "COM_RESTRICAO":
			return StatusAvaliacaoProposta.nao_elegivel;
		}
				
		throw new IllegalArgumentException("O resultado da avaliacao nao foi um valor aceitável "+resultadoAvaliacao);
	}
}
