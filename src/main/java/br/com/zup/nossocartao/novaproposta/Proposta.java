package br.com.zup.nossocartao.novaproposta;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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

	public Proposta(@Email @NotBlank String email, @NotBlank String nome,
			@NotBlank String endereco, @Positive BigDecimal salario,
			@CpfCnpj String documento) {
				this.email = email;
				this.nome = nome;
				this.endereco = endereco;
				this.salario = salario;
				this.documento = documento;
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		Assert.notNull(id,"O objeto precisa estar salvo para invocar o getId");
		return id;
	}

}
