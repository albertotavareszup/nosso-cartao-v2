package br.com.zup.nossocartao.associapaypal;

import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import br.com.zup.nossocartao.associacartao.Cartao;

@Entity
public class CarteiraPaypal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private @NotNull Cartao cartao;
	private @NotBlank @Email String email;
	
	@Deprecated
	public CarteiraPaypal() {

	}

	public CarteiraPaypal(@NotNull @Validated Cartao cartao,
			@NotBlank @Email String email) {
		Assert.isTrue(cartao.precondicoesUso(),"As precondicoes do cartao precisa ser válidas");
		Assert.isTrue(cartao.precondicaoAceitePaypal(), "Este cartão já está associado com o paypal");
				this.cartao = cartao;
				this.email = email;
	}

	public Optional<Long> getId() {
		return Optional.ofNullable(id);
	}

}
