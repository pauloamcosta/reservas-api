package br.pauloamcosta.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.pauloamcosta.model.CheckIn;
import br.pauloamcosta.services.CheckInService;

/**
 * Classe de controlador REST para checkins
 * 
 * @author pauloamcosta
 * 
 * @since 1.0.0
 *
 */
@RestController
@RequestMapping(value = "/checkins")
public class CheckInController {

	@Autowired
	private CheckInService checkInService;
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<String> insert(@Valid @RequestBody CheckIn obj) {
		try {
			obj = checkInService.insert(obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
		            .body("Data de saída não pode ser igual o inferior a de entrada");
		}
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<CheckIn>> findCheckInPageble(
			@RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
			@RequestParam(value = "linhasPorPagina", defaultValue = "12") Integer linhasPorPagina,
			@RequestParam(value = "orderBy", defaultValue = "pessoa") String oderBy,
			@RequestParam(value = "direcao", defaultValue = "DESC") String direcao) {
		Page<CheckIn> list = checkInService.findAllByPages(pagina, linhasPorPagina, oderBy, direcao);
		return ResponseEntity.ok().body(list);
	}
	
	@CrossOrigin
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody CheckIn obj, @PathVariable Long id) {
		obj.setId(id);
		obj = checkInService.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@CrossOrigin
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		checkInService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
