package br.pauloamcosta.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.pauloamcosta.model.CheckIn;

/**
 * Repositório específico de checkin.
 * 
 * @author pauloamcosta
 * @param <T>
 *
 * @since 1.0.0
 */

@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, Long> {
	
	Page<CheckIn> findAll(Pageable pageRequest);

}
