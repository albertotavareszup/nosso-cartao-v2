package br.com.zup.nossocartao.outrossistemas;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IntegracoesController {
	
	private AtomicInteger contDocumentos = new AtomicInteger();

	@PostMapping(value = "/avaliacao")
	public String avaliaDocumento(@RequestBody NovoDocumentoRequest request) {		
		int contAtual = contDocumentos.getAndIncrement();
		if(contAtual % 2 != 0) {
			return "COM_RESTRICAO";
		}
		
		return "SEM_RESTRICAO";
	}

}
