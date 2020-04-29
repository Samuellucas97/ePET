package br.ufrn.ePET.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.models.Evento;
import br.ufrn.ePET.repository.EventoRepository;

@Service
public class EventoService {
	
	private final EventoRepository eventoRepository;
	
	@Autowired
	public EventoService(EventoRepository eventoRepository) {
		this.eventoRepository = eventoRepository;
	}
	
	public Evento buscar(Long id) {
		return eventoRepository.findById(id).isPresent() ? 
				eventoRepository.findById(id).get(): null;
	}
	
	public Page<Evento> buscar(Pageable pageable){
		Page<Evento> eventList = eventoRepository.findEventos(pageable);
		/*if (eventList.isEmpty())
			throw new ResourceNotFoundException("Nenhum evento cadastrado");*/
		return eventList;
	}
	
	public List<Evento> buscarAtivos(){
		//Page<Evento> lista = eventoRepository.findAll(pageable);
		List<Evento> lista_aux = eventoRepository.findByAtivos();
		LocalDate ld = LocalDate.now();
		List<Evento> aux = new ArrayList<Evento>();
		for(Evento e : lista_aux) {
			if(!(ld.compareTo(e.getD_inscricao()) < 0) && !(ld.compareTo(e.getD_inscricao_fim()) > 0) ) {
				//System.out.println(e.getTitulo());
				aux.add(e);
			}
		}
		//lista_aux.clear();
		/*java.sql.Date sqlDate = java.sql.Date.valueOf(LocalDate.now());
		Page<Evento> lista = eventoRepository.findByAtivos(sqlDate, pageable);
		System.out.println(LocalDate.now());*/
		if(lista_aux.isEmpty())
			throw new ResourceNotFoundException("Nenhum evento aberto para a inscrição");
		return aux;
	}

	public Page<Evento> buscarPorTitulo(String titulo, Pageable pageable){
		return eventoRepository.findByTitulo(titulo, pageable);
	}
	
	public List<Evento> buscarInativos(){
		List<Evento> lista = eventoRepository.findByInativos();
		if(lista.isEmpty()) {
			throw new ResourceNotFoundException("Nenhum evento inativo");
		}
		return lista;
	}

	public Evento salvar(Evento evento) {
		if(evento.getTextoDeclaracaoEvento() == null) {
			evento.setTextoDeclaracaoEvento("Declaro, para os devidos fins, que {nome_participante}, portador do CPF {cpf}, participou do evento {titulo_evento}, realizado no {local},"
	  		+ " nos dias {data_inicio} à {data_fim}, com uma carga-horária total de {carga_horária}h. Este evento foi promovido pelo Programa de "
	  		+ "Educação Tutorial do Curso de Ciência da Computação da Universidade Federal do Rio Grande do Norte (PET-CC/UFRN).");
		}
		return eventoRepository.save(evento);
	}
	
	public void remover(Long id) {
		if (!eventoRepository.findById(id).isPresent())
			throw new ResourceNotFoundException("Nenhum evento com id "+id+" encontrado.");
		eventoRepository.deleteById(id);
	}
	
	public void ativar(Long id) {
		Evento evento = new Evento();
		if (eventoRepository.findById(id).isPresent())
			evento = eventoRepository.findById(id).get();
		else throw new ResourceNotFoundException("Nenhum evento com id "+id+" encontrado.");
		
		//Evento evento = eventoRepository.findById(id).get();
		evento.setAtivo(true);
		eventoRepository.save(evento);
	}
}
