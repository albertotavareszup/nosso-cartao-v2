package br.com.zup.nossocartao.outrossistemas;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import br.com.zup.nossocartao.novaproposta.CpfCnpj;
import br.com.zup.nossocartao.novaproposta.Proposta;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class NovoDocumentoRequest {

	@CpfCnpj
	private String documento;
	
	@Deprecated
	public NovoDocumentoRequest() {

	}
	
	public NovoDocumentoRequest(Proposta proposta) {
		this.documento = proposta.getDocumento();
	}

}
