package br.com.zup.nossocartao.avisoviagem;

import javax.validation.constraints.NotBlank;

public class NovoAvisoViagemRequest {

	@NotBlank
	private String destino;
	
	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getDestino() {
		return destino;
	}
}
