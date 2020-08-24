package br.com.zup.nossocartao.avisoviagem;

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
public class AvisoViagem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private @Valid @NotNull Cartao cartao;
	private @NotBlank String destino;
	private @NotBlank String navegador;
	private @NotBlank String remoteAddr;
	@NotNull
	@PastOrPresent
	private LocalDateTime instante = LocalDateTime.now();

	public AvisoViagem(@Valid @NotNull Cartao cartao, @NotBlank String destino, @NotBlank String navegador,
			@NotBlank String remoteAddr) {
				Assert.isTrue(cartao.precondicoesAvisoViagem(),"As precondioes para viagem não estão satisfeitas");
				this.cartao = cartao;
				this.destino = destino;
				this.navegador = navegador;
				this.remoteAddr = remoteAddr;
	}

	public Optional<Long> getId() {
		return Optional.ofNullable(id);
	}

	
}
