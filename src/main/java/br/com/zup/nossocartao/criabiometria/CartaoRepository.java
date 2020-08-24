package br.com.zup.nossocartao.criabiometria;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.zup.nossocartao.associacartao.Cartao;

@Repository
public interface CartaoRepository extends CrudRepository<Cartao, Long>{

}
