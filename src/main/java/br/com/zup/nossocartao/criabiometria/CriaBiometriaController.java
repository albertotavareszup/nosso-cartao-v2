package br.com.zup.nossocartao.criabiometria;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.zup.nossocartao.novaproposta.Cartao;

@RestController
public class CriaBiometriaController {
	
	@Autowired
	private EntityManager manager;

	@PostMapping(value = "/api/cartoes/{id}/biometria")
	@Transactional
	public void cria(@PathVariable("id") Long id,@RequestBody @Valid NovaBiometriaRequest request) {
		Cartao cartao = manager.find(Cartao.class, id);
		if(cartao == null) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		cartao.adicionaBiometria(request.getDigital());
		
	}

}
