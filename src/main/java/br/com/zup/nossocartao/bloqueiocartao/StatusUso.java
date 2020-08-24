package br.com.zup.nossocartao.bloqueiocartao;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.zup.nossocartao.novaproposta.Cartao;
import br.com.zup.nossocartao.novaproposta.PossiveisStatusUso;

@Entity
public class StatusUso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private @NotNull PossiveisStatusUso statusSolicitado;
	@ManyToOne
	private @Valid @NotNull Cartao cartao;
	private @NotBlank String userAgent;
	private @NotBlank String ipRemoto;
	private LocalDateTime instante = LocalDateTime.now();

	public StatusUso(@NotNull PossiveisStatusUso statusSolicitado,
			@Valid @NotNull Cartao cartao, @NotBlank String userAgent,
			@NotBlank String ipRemoto) {
		this.statusSolicitado = statusSolicitado;
		this.cartao = cartao;
		this.userAgent = userAgent;
		this.ipRemoto = ipRemoto;
	}
	
	public boolean verificaStatus(PossiveisStatusUso possivelStatusUso) {
		return this.statusSolicitado.equals(possivelStatusUso);
	}

}
