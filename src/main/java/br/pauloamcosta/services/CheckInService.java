package br.pauloamcosta.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.pauloamcosta.model.CheckIn;
import br.pauloamcosta.repositories.CheckInRepository;
import br.pauloamcosta.services.exceptions.ObjectNotFoundException;

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

	public CheckIn find(Long id) {
		Optional<CheckIn> obj = checkInRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + CheckIn.class.getName()));
	}

	public CheckIn update(CheckIn obj) {
		CheckIn newObj = find(obj.getId());
		updateData(newObj, obj);
		return checkInRepository.save(newObj);
	}

	private void updateData(CheckIn newObj, CheckIn obj) {
		newObj.setPessoa(obj.getPessoa());
		newObj.setDataEntrada(obj.getDataEntrada());
		newObj.setDataSaida(obj.getDataSaida());
		newObj.setAdicionalVeiculo(obj.isAdicionalVeiculo());
		newObj.setValorDiarias(obj.getValorDiarias());
	}

	public void delete(Long id) {
		find(id);
		checkInRepository.deleteById(id);
	}
}
