package br.com.zup.nossocartao.outrossistemas;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IntegracoesController {
	
	private AtomicInteger contDocumentos = new AtomicInteger();

	@PostMapping(value = "/avalia")
	public String avaliaDocumento(@RequestBody NovoDocumentoRequest request) {		
		int contAtual = contDocumentos.getAndIncrement();
		if(contAtual % 2 != 0) {
			return "COM_RESTRICAO";
		}
		
		return "SEM_RESTRICAO";
	}
	
	@PostMapping("/busca-numero-cartao")
	public String buscaNumeroCartao(@RequestBody DocumentoProposta documentoProposta) {
		String[] numeros = {"5220709255843269","5515073532965849","5594406400812913","5342835895400112"};
		int posicao = new Random().nextInt(4);
		return numeros[posicao];
	}


}
