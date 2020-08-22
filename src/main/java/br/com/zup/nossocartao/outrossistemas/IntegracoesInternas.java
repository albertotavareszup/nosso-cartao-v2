package br.com.zup.nossocartao.outrossistemas;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url = "${enderecos-internos.base-url}", name = "integracoes-internas")
public interface IntegracoesInternas {

	@PostMapping("/api-interna/associa-proposta-cartao")
	void associaPropostasECartoes();

}
