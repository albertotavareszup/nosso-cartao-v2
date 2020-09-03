package br.com.zup.nossocartao.associacartao;

import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.util.Assert;

import br.com.zup.nossocartao.associapaypal.CarteiraPaypal;

@Entity
public class CarteiraSamsung {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToOne
	private @NotNull @Valid Cartao cartao;
	private @NotBlank @Email String email;

	public CarteiraSamsung(@NotNull @Valid Cartao cartao,
			@NotBlank @Email String email) {
		Assert.isTrue(cartao.precondicaoSamsung(),
				"A precondicao para samsung não foi respeitada");
		this.cartao = cartao;
		this.email = email;
	}

	public Optional<Long> getId() {
		return Optional.ofNullable(id);
	}

}
