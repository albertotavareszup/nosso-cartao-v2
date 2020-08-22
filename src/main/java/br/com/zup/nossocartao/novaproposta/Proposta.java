package br.com.zup.nossocartao.novaproposta;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Version;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.util.Assert;

@Entity
public class Proposta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private @Email @NotBlank String email;
	private @NotBlank String nome;
	private @NotBlank String endereco;
	private @Positive BigDecimal salario;
	@CpfCnpj
	@NotBlank
	private String documento;
	@NotNull
	private StatusAvaliacaoProposta statusAvaliacao;
	@OneToOne(mappedBy = "proposta",cascade = CascadeType.MERGE)	
	private Cartao cartao;
	@Version
	private int versao;
	
	@Deprecated
	public Proposta() {

	}

	public Proposta(@Email @NotBlank String email, @NotBlank String nome,
			@NotBlank String endereco, @Positive BigDecimal salario,
			@CpfCnpj String documento) {
				this.email = email;
				this.nome = nome;
				this.endereco = endereco;
				this.salario = salario;
				this.documento = documento;
				this.statusAvaliacao = StatusAvaliacaoProposta.nao_elegivel;
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		//Assert.notNull(id,"O objeto precisa estar salvo para invocar o getId");
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((documento == null) ? 0 : documento.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Proposta other = (Proposta) obj;
		if (documento == null) {
			if (other.documento != null)
				return false;
		} else if (!documento.equals(other.documento))
			return false;
		return true;
	}

	public String getDocumento() {
		return this.documento;
	}

	public void atualizaStatus(StatusAvaliacaoProposta statusAvaliacao) {
		Assert.isTrue(this.statusAvaliacao.equals(StatusAvaliacaoProposta.nao_elegivel), "uma vez que a proposta é elegível não pode mais trocar");
		this.statusAvaliacao = statusAvaliacao;
	}

	public void associaCartao(String numero) {
		Assert.isNull(cartao,"ja associou o cartao");
		Assert.isTrue(this.statusAvaliacao.equals(StatusAvaliacaoProposta.elegivel),"nao rola associar cartao com proposta nao elegivel");
		this.cartao = new Cartao(this,numero);
	}
	
	
	
}
