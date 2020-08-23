package br.com.zup.nossocartao.bloqueiacartao;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;
import org.springframework.web.server.ResponseStatusException;

import br.com.zup.nossocartao.bloqueiocartao.InfoNecessariaBloqueioCartao;
import br.com.zup.nossocartao.bloqueiocartao.VerificaPrecondicaoBloqueioCartao;

public class VerificaPrecondicaoBloqueioCartaoTest {

	private VerificaPrecondicaoBloqueioCartao verificaPrecondicaoBloqueioCartao = new VerificaPrecondicaoBloqueioCartao();

	@Test
	@DisplayName("deveria interromper bloqueio caso nao exista user agent")
	void teste1() throws Exception {
		Runnable executavel = () -> {
			Assertions.fail("nao deveria executar");
		};

		InfoNecessariaBloqueioCartao infoBloqueio = new InfoNecessariaBloqueioCartao() {

			@Override
			public boolean temNavegador() {
				return false;
			}

			@Override
			public boolean temIpRemoto() {
				Assertions.fail("nao deveria chegar aqui");
				return false;
			}
		};

		Assertions.assertThrows(ResponseStatusException.class, () -> {
			verificaPrecondicaoBloqueioCartao.executa(1l, infoBloqueio, executavel);
		});
	}

	@Test
	@DisplayName("deveria interromper bloqueio caso nao exista ip")
	void teste2() throws Exception {
		Runnable executavel = () -> {
			Assertions.fail("nao deveria executar");
		};

		InfoNecessariaBloqueioCartao infoBloqueio = new InfoNecessariaBloqueioCartao() {

			@Override
			public boolean temNavegador() {
				return true;
			}

			@Override
			public boolean temIpRemoto() {
				return false;
			}
		};

		Assertions.assertThrows(ResponseStatusException.class, () -> {
			verificaPrecondicaoBloqueioCartao.executa(1l, infoBloqueio, executavel);
		});
	}

	@Test
	@DisplayName("deveria executar codigo de bloqueio caso as pre condições estejam corretas")
	void teste3() throws Exception {

		InfoNecessariaBloqueioCartao infoBloqueio = new InfoNecessariaBloqueioCartao() {

			@Override
			public boolean temNavegador() {
				return true;
			}

			@Override
			public boolean temIpRemoto() {
				return true;
			}
		};

		ArrayList<Object> lista = new ArrayList<>();	
		Runnable executavel = () -> {
			lista.add(new Object());
		};
		
		verificaPrecondicaoBloqueioCartao.executa(1l, infoBloqueio, executavel);
		
		Assertions.assertTrue(lista.size() == 1);
	}
}
