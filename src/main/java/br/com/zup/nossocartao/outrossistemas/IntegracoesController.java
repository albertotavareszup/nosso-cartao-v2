package br.com.zup.nossocartao.outrossistemas;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class IntegracoesController {

	private AtomicInteger contDocumentos = new AtomicInteger();
	private AtomicInteger contPaypal = new AtomicInteger();

	private static final Logger log = LoggerFactory
			.getLogger(IntegracoesController.class);

	@PostMapping(value = "/avalia")
	public String avaliaDocumento(@RequestBody NovoDocumentoRequest request) {
		int contAtual = contDocumentos.getAndIncrement();
		if (contAtual % 2 != 0) {
			return "COM_RESTRICAO";
		}

		return "SEM_RESTRICAO";
	}

	@PostMapping(value = "/bloqueia-cartao-canais")
	public void bloqueia(@RequestBody Map<String, String> params) {
		log.debug("bloqueando cartao ###{}", params.get("numero").substring(7));
		int contAtual = contDocumentos.getAndIncrement();
		if (contAtual % 2 != 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/associa-paypal")
	public void associaPaypal(@RequestBody Map<String, String> params) {
		log.debug("Associando paypal com cartao ###{} e email ###{}",
				params.get("numero").substring(7),
				params.get("email").split("@")[1]);
		int contAtual = contPaypal.getAndIncrement();
		if (contAtual % 2 != 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/busca-numero-cartao")
	public String buscaNumeroCartao(
			@RequestBody DocumentoProposta documentoProposta) {
		String[] numeros = { "5220709255843269", "5515073532965849",
				"5594406400812913", "5342835895400112" };
		int posicao = new Random().nextInt(4);
		return numeros[posicao];
	}

}
