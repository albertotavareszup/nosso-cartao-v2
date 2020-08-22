package br.com.zup.nossocartao.outrossistemas;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url = "${enderecos-externos.base-url}", name = "integracoes")
public interface Integracoes {

	@PostMapping("/avalia")
	public String avalia(NovoDocumentoRequest request);

	@PostMapping("/busca-numero-cartao")
	public String buscaNumeroCartao(DocumentoProposta documentoProposta);
}
