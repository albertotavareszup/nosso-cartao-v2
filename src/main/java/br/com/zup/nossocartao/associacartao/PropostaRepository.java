package br.com.zup.nossocartao.associacartao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.zup.nossocartao.novaproposta.Proposta;
import br.com.zup.nossocartao.novaproposta.StatusAvaliacaoProposta;

@Repository
public interface PropostaRepository extends CrudRepository<Proposta, Long>{

	@Query("select p from Proposta p left join p.cartao c where p.statusAvaliacao = :status and c.id is null")
	List<Proposta> todasSemCartao(@Param("status") StatusAvaliacaoProposta status);

}
