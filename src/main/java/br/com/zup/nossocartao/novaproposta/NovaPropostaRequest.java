package br.com.zup.nossocartao.novaproposta;

import java.math.BigDecimal;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import com.sun.istack.NotNull;

public class NovaPropostaRequest {

	@Email
	@NotBlank
	private String email;
	@NotBlank
	private String nome;
	@NotBlank
	private String endereco;
	@NotNull
	@Positive
	private BigDecimal salario;
	@CpfCnpj
	@NotBlank
	private String documento;

	public NovaPropostaRequest(@Email @NotBlank String email,
			@NotBlank String nome, @NotBlank String endereco,
			@Positive BigDecimal salario, String documento) {
		super();
		this.email = email;
		this.nome = nome;
		this.endereco = endereco;
		this.salario = salario;
		this.documento = documento;
	}

	public Proposta toModel() {
		return new Proposta(email,nome,endereco,salario,documento);
	}

	public String getDocumento() {
		return documento;
	}

}
