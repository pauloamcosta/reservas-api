package br.pauloamcosta.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.pauloamcosta.model.CheckIn;
import br.pauloamcosta.repositories.CheckInRepository;

/**
 * Classe de servicos para implementacao de inserir e buscar checkins.
 * 
 * @author pauloamcosta
 * 
 * @since 1.0.0
 *
 */

@Service
public class CheckInService {
	@Autowired
	CheckInRepository checkInRepository;

	/**
	 * Método que insere checkins. Data de entrada é setada para a data atual.
	 * 
	 * @author pauloamcosta
	 * 
	 * @param obj = checkin a ser inserido
	 * 
	 * @since 1.0.0
	 * 
	 * @return objeto salvo no banco de dados
	 */

	@Transactional
	public CheckIn insert(CheckIn obj) {

		LocalDateTime agora = LocalDateTime.now();

		obj.setId(null);
		obj.setDataEntrada(agora);
		return checkInRepository.save(obj);
	}

	/**
	 * Método que busca checkins de forma paginada.
	 * 
	 * @author pauloamcosta
	 * 
	 * @param pagina          = pagina a ser buscada
	 * @param linhasPorPagina = linhas a serem retornadas por página
	 * @param oderBy          = atributo de ordenacao
	 * @param direcao         = direcao da ordenacao
	 * 
	 * @since 1.0.0
	 * 
	 * @return checkins
	 */

	public Page<CheckIn> findAllByPages(Integer pagina, Integer linhasPorPagina, String oderBy, String direcao) {
		PageRequest pageRequest = PageRequest.of(pagina, linhasPorPagina, Direction.valueOf(direcao), oderBy);
		return checkInRepository.findAll(pageRequest);
	}

}
