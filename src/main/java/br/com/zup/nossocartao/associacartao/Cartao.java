package br.com.zup.nossocartao.associacartao;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.util.Assert;

import br.com.zup.nossocartao.associapaypal.CarteiraPaypal;
import br.com.zup.nossocartao.bloqueiocartao.PossiveisStatusUso;
import br.com.zup.nossocartao.bloqueiocartao.StatusUso;
import br.com.zup.nossocartao.criabiometria.Biometria;
import br.com.zup.nossocartao.novaproposta.Proposta;

@Entity
public class Cartao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToOne
	private Proposta proposta;
	@NotBlank
	@CreditCardNumber
	private String numero;
	@ElementCollection
	private Set<Biometria> biometrias = new HashSet<>();
	@OneToMany(mappedBy = "cartao")
	private List<StatusUso> statusUsos = new ArrayList<>();
	@OneToOne(mappedBy = "cartao",cascade = CascadeType.PERSIST)
	private CarteiraPaypal carteiraPaypal;

	@Deprecated
	public Cartao() {

	}

	public Cartao(@NotNull @Valid Proposta proposta,
			@CreditCardNumber @NotBlank String numero) {
		this.proposta = proposta;
		this.numero = numero;
	}

	/**
	 * 
	 * @param digital representa a digital que vai ser adicionada ao cartao
	 */
	public void adicionaBiometria(String digital) {
		this.biometrias.add(new Biometria(digital));
	}

	/**
	 * 
	 * @param userAgent navegador que solicitou o bloqueio
	 * @param ipRemoto  ip da solicitacao
	 */
	public void bloqueia(@NotBlank String userAgent,
			@NotBlank String ipRemoto) {
		Assert.state(!this.biometrias.isEmpty(),
				"Nenhum cartão pode ser bloqueado se não tiver digital associada");
		this.statusUsos.add(new StatusUso(PossiveisStatusUso.bloqueado, this,
				userAgent, ipRemoto));
	}

	public String getNumero() {
		// aqui deveria ta encodado de alguma forma?
		return numero;
	}

	public boolean precondicoesUso() {
		return !this.biometrias.isEmpty() && liberado();
	}

	private boolean liberado() {
		if (this.statusUsos.isEmpty()) {
			return true;
		}
		//ultimo status liberado
		return this.statusUsos.get(this.statusUsos.size() - 1)
				.verificaStatus(PossiveisStatusUso.liberado);
	}

	public Optional<CarteiraPaypal> adicionaPaypal(
			@NotBlank @Email String email) {
		if(this.carteiraPaypal != null) {
			return Optional.empty();
		}
		
		this.carteiraPaypal = new CarteiraPaypal(this, email);		 
		return Optional.of(this.carteiraPaypal);
	}

	public boolean precondicaoAceitePaypal() {
		return this.carteiraPaypal == null;
	}

}
