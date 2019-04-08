package br.pauloamcosta.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.pauloamcosta.model.Pessoa;
import br.pauloamcosta.repositories.PessoaRepository;
import br.pauloamcosta.services.exceptions.ObjectNotFoundException;

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

	/**
	 * Função que Busca uma determinado pessoa por Id.
	 * 
	 * @author pauloamcosta
	 * 
	 * @param id = id da pessoa a ser buscada
	 * 
	 * @since 1.0.0
	 * 
	 * @return pessoa específica
	 */
	public Pessoa find(Long id) {
		Optional<Pessoa> obj = pessoaRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pessoa.class.getName()));
	}

	/**
	 * Função que atualiza uma pessoa
	 * 
	 * @author pauloamcosta
	 * 
	 * @param obj = uma determinada pessoa para ser atualizado
	 * 
	 * @since 1.0.0
	 * 
	 * @return pessoa atualizada salva
	 */
	public Pessoa update(Pessoa obj) {
		Pessoa newObj = find(obj.getId());
		updateData(newObj, obj);
		return pessoaRepository.save(newObj);
	}

	/**
	 * Função que recebe os dados de um pessoa e os atualizam
	 * 
	 * @author pauloamcosta
	 * 
	 * @param newObj = pessoa atualizado
	 * @param obj    = pessoa com dados a serem atualizados
	 * 
	 * @since 1.0.0
	 * 
	 */
	private void updateData(Pessoa newObj, Pessoa obj) {
		newObj.setNome(obj.getNome());
		newObj.setDocumento(obj.getDocumento());
		newObj.setTelefone(obj.getTelefone());
	}

	/**
	 * Função que deleta uma pessoa por seu Id
	 * 
	 * @author pauloamcosta
	 * 
	 * @param id = id da pessoa a ser deletada
	 * 
	 * @since 1.0.0
	 * 
	 */
	public void delete(Long id) {
		find(id);
		pessoaRepository.deleteById(id);
	}

	/**
	 * Função para retornar todas as pessoas sem paginação
	 * 
	 * @author pauloamcosta
	 * 
	 * @since 1.0.0
	 * 
	 * @return lista com todas as pessoas
	 * 
	 */
	public List<Pessoa> findAll() {
		return pessoaRepository.findAll();
	}

}
