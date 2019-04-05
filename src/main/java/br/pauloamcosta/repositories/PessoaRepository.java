package br.pauloamcosta.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.pauloamcosta.model.Pessoa;

/**
 * Repositório específico de pessoa.
 * 
 * @author pauloamcosta
 * @param <T>
 *
 * @since 1.0.0
 */

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
	
	Page<Pessoa> findAll(Pageable pageRequest);

}

