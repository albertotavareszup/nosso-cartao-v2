package br.com.zup.nossocartao.recuperacaosenha;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.springframework.util.Assert;

import br.com.zup.nossocartao.associacartao.Cartao;

@Entity
public class RecuperaSenha {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private @NotNull @Valid Cartao cartao;
	private @NotBlank String remoteAddr;
	private @NotBlank String navegador;
	@PastOrPresent
	private LocalDateTime instante = LocalDateTime.now();

	public RecuperaSenha(@NotNull @Valid Cartao cartao, @NotBlank String remoteAddr,
			@NotBlank String navegador) {
		Assert.isTrue(cartao.precondicoesUso(),"Este cartão não deveria estar sendo usado");
				this.cartao = cartao;
				this.remoteAddr = remoteAddr;
				this.navegador = navegador;
	}

	public Optional<Long> getId() {
		return Optional.of(id);
	}

}
