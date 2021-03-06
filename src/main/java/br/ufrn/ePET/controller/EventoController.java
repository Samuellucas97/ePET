package br.ufrn.ePET.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.models.Evento;
import br.ufrn.ePET.models.EventoDTO;
import br.ufrn.ePET.service.EventoService;

@RestController
@RequestMapping(value = "/api")
public class EventoController {
	
	private final EventoService eventoService;
	
	@Autowired
	public EventoController(EventoService eventoService) {
		this.eventoService = eventoService;
	}
	
	@GetMapping(value = "/eventos")
	@ApiOperation(value = "Retorna todos os eventos do sistema.")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> getEventos(Pageable pageable){
		//try {
			Page<Evento> page = eventoService.buscar(pageable);
			/*if(page.isEmpty()) {
				throw new ResourceNotFoundException("Nenhum evento cadastrado.");
			}*/
			return new ResponseEntity<>(page, HttpStatus.OK);
		//} catch (Exception e) {
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
	}
	
	@GetMapping(value = "/eventos-abertos")
	@ApiOperation(value = "Retorna todos os eventos abertos do sistema.")
	public ResponseEntity<?> getEventosAbertos(Pageable pageable){
		//try {
			List<Evento> page = eventoService.buscarAtivos();
			if(page.isEmpty()) {
				throw new ResourceNotFoundException("Nenhum evento aberto para a inscrição.");
			}
			return new ResponseEntity<>(page, HttpStatus.OK);
		//} catch (Exception e) {
			//throw new ResourceNotFoundException("Nenhum evento aberto para a inscrição");
		//}
	}

	@GetMapping(value = "/eventos-abertos/{id}")
	@ApiOperation(value = "Retorna um evento aberto do sistema pelo seu ID.")
	public ResponseEntity<?> getEventosAbertosID(@ApiParam(value = "Id do evento procurado") @PathVariable Long id){
			EventoDTO e = eventoService.buscarAtivosId(id);
			if(e == null) {
				throw new ResourceNotFoundException("Nenhum evento aberto com o id informado.");
			}
			return new ResponseEntity<>(e, HttpStatus.OK);
	}
	
	@GetMapping(value = "/eventos/{id}")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@ApiOperation(value = "Método que busca um evento a partir de um ID.")
	public ResponseEntity<?> getEventos(@ApiParam(value = "Id do evento procurado") @PathVariable Long id){
		EventoDTO evento = eventoService.buscar(id);
		if(evento == null)
			throw new ResourceNotFoundException("Nenhum evento com id "+id +" encontrado.");
		//try {
			return new ResponseEntity<>(evento, HttpStatus.OK);
		//} catch (Exception e) {
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
	}
	
	@GetMapping(value = "eventos-inativos")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@ApiOperation(value = "Retorna os eventos inativos do sistema.")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> getEventosInativos(){
		return new ResponseEntity<>(eventoService.buscarInativos(), HttpStatus.OK);
	}

	@GetMapping(value = "pesquisar-evento/{search}")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@ApiOperation(value = "Método que retorna os eventos a partir de um título.")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> getEventoPorTitulo(@ApiParam(value = "Título do evento a ser procurado") @PathVariable String search, Pageable pageable){
		Page<Evento> eventos = eventoService.buscarPorTitulo(search, pageable);
		if(eventos.isEmpty()){
			throw new ResourceNotFoundException("Nenhum evento encontrado");
		} else {
			return new ResponseEntity<>(eventos, HttpStatus.OK);
		}
	}
	@PostMapping(value = "/eventos-ativar/{id}")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@ApiOperation(value = "Método que ativa um evento a partir de um ID(SOMENTE UM TUTOR REALIZA ESSA AÇÂO).")
	@Secured("ROLE_tutor")
	public ResponseEntity<?> ativarEventos(@ApiParam(value = "Id do avento a ser ativado") @PathVariable Long id){
		//try {
			eventoService.ativar(id);
			return new ResponseEntity<>(HttpStatus.OK);
		//} catch (Exception e) {
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
	}
	
	@PostMapping(value = "/eventos-cadastrar")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@ApiOperation(value = "Cadastra ou edita um evento no sistema.(petianos e comuns só podem editar eventos que organizam)")
	@Secured({"ROLE_tutor", "ROLE_petiano", "ROLE_comum"})
	public ResponseEntity<?> saveEventos(@Valid @RequestBody EventoDTO evento, HttpServletRequest req){
		//try {
			eventoService.salvar(evento, req);
			return new ResponseEntity<>(HttpStatus.OK);
		//} catch (Exception e) {
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
	}

	@CrossOrigin
	@DeleteMapping(value="/eventos-remove/{id}")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@ApiOperation(value = "Remove um evento do sistema a partir de seu ID.(Só remove se não ouver organizadores, participantes, periodo_evento e anexos cadastrado para esse evento)")
	@Secured({"ROLE_tutor", "ROLE_petiano"})
	public ResponseEntity<?> removeEventos(@ApiParam(value = "Id do evento a ser removido") @PathVariable Long id, HttpServletRequest req){
		//try {
			eventoService.remover(id, req);
		return new ResponseEntity<>(HttpStatus.OK);
		//} catch (Exception e) {
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
	}

	@GetMapping(value = "/eventos-abertos-nao-organizo-ativos")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@ApiOperation(value = "Método que busca todos os eventos aberto no qual o usuário que fez a requisição não organiza.")
	@Secured({"ROLE_tutor", "ROLE_petiano", "ROLE_comum"})
	public ResponseEntity<?> getEventosNaoOrganizo(HttpServletRequest  req, Pageable pageable){
		Page<Evento> eventos = eventoService.buscarNaoOrganizo(req, pageable);
		if(eventos == null || eventos.isEmpty())
			throw new ResourceNotFoundException("Nenhum evento encontrado no qual você não organiza.");
		//try {
		return new ResponseEntity<>(eventos, HttpStatus.OK);
		//} catch (Exception e) {
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		//}
	}
	
	@GetMapping(value = "/eventos-abertos-nao-organizo-inativos")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
	@ApiOperation(value = "Método que busca todos os eventos (ativos e inativos) no qual o usuário que fez a requisição não organiza.")
	@Secured({"ROLE_tutor"})
	public ResponseEntity<?> getEventosNaoOrganizoIna(HttpServletRequest  req, Pageable pageable){
		Page<Evento> eventos = eventoService.buscarNaoOrganizoIna(req, pageable);
		if(eventos == null || eventos.isEmpty())
			throw new ResourceNotFoundException("Nenhum evento encontrado no qual você não organiza.");
		return new ResponseEntity<>(eventos, HttpStatus.OK);
	}
}
