package br.com.zup.nossocartao.acompanhaproposta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.zup.nossocartao.associacartao.PropostaRepository;
import br.com.zup.nossocartao.novaproposta.Proposta;

@RestController
public class AcompanhaPropostaController {

	@Autowired
	private PropostaRepository propostaRepository;

	@GetMapping(value = "/api/proposta/{id}")
	public DetalhePropostaResponse getMethodName(@PathVariable("id") Long id) {
		Proposta proposta = propostaRepository.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return new DetalhePropostaResponse(proposta);
	}

}
