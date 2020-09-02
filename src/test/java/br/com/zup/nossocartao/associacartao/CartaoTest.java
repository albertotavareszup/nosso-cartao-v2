package br.com.zup.nossocartao.associacartao;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.zup.nossocartao.novaproposta.Proposta;

public class CartaoTest {

	@Test
	@DisplayName("se nao tem biometria a pre condicacao para aviso viagem nao eh válida")
	void teste1() throws Exception {
		Proposta proposta = new Proposta("email@email.com", "Alberto",
				"endereco", BigDecimal.TEN, "12324353454");
		
		Cartao cartao = new Cartao(proposta, "111111111");
		Assertions.assertFalse(cartao.precondicoesUso());
	}
	
	@Test
	@DisplayName("se tem biometria e nao bloqueio a pre condicao para viagem está de boa")
	void teste2() throws Exception {
		Proposta proposta = new Proposta("email@email.com", "Alberto",
				"endereco", BigDecimal.TEN, "12324353454");
		
		Cartao cartao = new Cartao(proposta, "111111111");
		cartao.adicionaBiometria("8645878534");
		
		Assertions.assertTrue(cartao.precondicoesUso());
	}
	
	@Test
	@DisplayName("se tem biometria e tem bloqueio nao libera para viagem")
	void teste3() throws Exception {
		Proposta proposta = new Proposta("email@email.com", "Alberto",
				"endereco", BigDecimal.TEN, "12324353454");
		
		Cartao cartao = new Cartao(proposta, "111111111");
		cartao.adicionaBiometria("8645878534");
		cartao.bloqueia("navegador", "127.0.0.1");
		
		Assertions.assertFalse(cartao.precondicoesUso());
	}
}
