package br.com.zup.nossocartao.novaproposta;

import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.CreditCardNumber;

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
	
	@Deprecated
	public Cartao() {

	}
	
	public Cartao(@NotNull @Valid Proposta proposta, @CreditCardNumber @NotBlank String numero) {
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
	
}
