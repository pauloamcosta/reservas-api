package br.pauloamcosta.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.pauloamcosta.model.Pessoa;
import br.pauloamcosta.services.PessoaService;

@RestController
@RequestMapping(value = "/pessoas")

/**
 * Classe de controlador REST para pessoas
 * 
 * @author pauloamcosta
 * 
 * @since 1.0.0
 *
 */

public class PessoaController {

	@Autowired
	private PessoaService pessoaService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody Pessoa obj) {
		obj = pessoaService.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<Pessoa>> findPessoaPageble(
			@RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
			@RequestParam(value = "linhasPorPagina", defaultValue = "12") Integer linhasPorPagina,
			@RequestParam(value = "orderBy", defaultValue = "nome") String oderBy,
			@RequestParam(value = "direcao", defaultValue = "DESC") String direcao) {
		Page<Pessoa> list = pessoaService.findAllByPages(pagina, linhasPorPagina, oderBy, direcao);
		return ResponseEntity.ok().body(list);
	}

}
