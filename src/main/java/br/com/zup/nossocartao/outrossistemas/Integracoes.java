package br.com.zup.nossocartao.outrossistemas;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url = "${enderecos-externos.base-url}", name = "integracoes")
public interface Integracoes {

	@PostMapping("/avalia")
	public String avalia(NovoDocumentoRequest request);

	@PostMapping("/busca-numero-cartao")
	public String buscaNumeroCartao(DocumentoProposta documentoProposta);

	@PostMapping("/bloqueia-cartao-canais")
	/**
	 * 
	 * @param params {"numero":...}
	 */
	public ResponseEntity<?> bloqueiaCartaoCanais(Map<String, String> params);
	
	@PostMapping("/associa-paypal")
	/**
	 * 
	 * @param params {"numero":...,'email':email}
	 */
	public ResponseEntity<?> associaPaypal(Map<String, String> params);
}
