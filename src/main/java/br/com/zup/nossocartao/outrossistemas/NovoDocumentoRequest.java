package br.com.zup.nossocartao.outrossistemas;

import br.com.zup.nossocartao.novaproposta.CpfCnpj;

public class NovoDocumentoRequest {

	@CpfCnpj
	private String documento;
	
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	
	public String getDocumento() {
		return documento;
	}
}
