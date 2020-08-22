package br.com.zup.nossocartao.outrossistemas;

import javax.validation.constraints.NotBlank;

import br.com.zup.nossocartao.novaproposta.CpfCnpj;

public class DocumentoProposta {

	@NotBlank
	@CpfCnpj
	private String documentoProposta;
	
	@Deprecated
	public DocumentoProposta() {

	}
	
	public DocumentoProposta(String documento) {
		this.documentoProposta = documento;
	}

	public void setDocumentoProposta(String documentoProposta) {
		this.documentoProposta = documentoProposta;
	}
	
	public String getDocumentoProposta() {
		return documentoProposta;
	}
}
