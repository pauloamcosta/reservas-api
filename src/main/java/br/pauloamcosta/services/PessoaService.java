package br.pauloamcosta.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.pauloamcosta.model.Pessoa;
import br.pauloamcosta.repositories.PessoaRepository;

/**
 * Classe de servicos para implementacao de inserir e buscar pessoas.
 * 
 * @author pauloamcosta
 * 
 * @since 1.0.0
 *
 */

@Service
public class PessoaService {

	@Autowired
	PessoaRepository pessoaRepository;

	/**
	 * Método que insere pessoas.
	 * 
	 * @author pauloamcosta
	 * 
	 * @param obj = pessoa a ser inserida
	 * 
	 * @since 1.0.0
	 * 
	 * @return objeto salvo no banco de dados
	 */

	@Transactional
	public Pessoa insert(Pessoa obj) {
		obj.setId(null);
		return pessoaRepository.save(obj);
	}

	/**
	 * Método que busca pessoas de forma paginada.
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
	 * @return pessoas
	 */

	public Page<Pessoa> findAllByPages(Integer pagina, Integer linhasPorPagina, String oderBy, String direcao) {
		PageRequest pageRequest = PageRequest.of(pagina, linhasPorPagina, Direction.valueOf(direcao), oderBy);
		return pessoaRepository.findAll(pageRequest);
	}

}
