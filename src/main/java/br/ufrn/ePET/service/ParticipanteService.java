package br.ufrn.ePET.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.models.Evento;
import br.ufrn.ePET.models.Participante;
import br.ufrn.ePET.models.Pessoa;
import br.ufrn.ePET.repository.EventoRepository;
import br.ufrn.ePET.repository.ParticipanteRepository;
import br.ufrn.ePET.repository.PessoaRepository;

@Service
public class ParticipanteService {
	
	private final ParticipanteRepository participanteRepository;
	private final EventoRepository eventoRepository;
	private final PessoaRepository pessoaRepository;
	
	@Autowired
	public ParticipanteService(ParticipanteRepository participanteRepository, EventoRepository eventoRepository,
			PessoaRepository pessoaRepository) {
		this.participanteRepository = participanteRepository;
		this.eventoRepository = eventoRepository;
		this.pessoaRepository = pessoaRepository;
	}
	
	public Page<Participante> buscar(Pageable pageable){
		return participanteRepository.findAll(pageable);
	}
	
	public Participante buscar(Long id) {
		return participanteRepository.findById(id).isPresent() ? 
				participanteRepository.findById(id).get(): null;
	}
	
	public Page<Participante> buscarPessoa(Long id, Pageable pageable){
		return participanteRepository.findByPessoa(id, pageable);
	}
	
	/*public List<Participante> buscarEventosAtuais(Long id){
		List<Participante> lista = participanteRepository.findByPessoa(id);
		for(Participante p : lista) {
			if(p.getEvento().get)
		}
		return lista;
	}*/
	public Page<Participante> buscarPorNomeOuCpfPessoa(String search, Pageable pageable){
		return participanteRepository.findByNomeOuCpfPessoa(search, pageable);
	}

	public Page<Participante> buscarPorTituloEvento(String search, Pageable pageable){
		return participanteRepository.findByTituloEvento(search, pageable);
	}

	public Page<Participante> buscarPorEvento(Long id, Pageable pageable){
		return participanteRepository.findByEvento(id, pageable);
	}

	public void salvar(Long id_evento, Long id_pessoa) {
		Participante p = new Participante();
		//Evento e = eventoRepository.findById(id_evento).get();
		//Pessoa pe = pessoaRepository.findById(id_pessoa).get();
		Evento e= eventoRepository.findById(id_evento).isPresent() ? 
				eventoRepository.findById(id_evento).get(): null;
		Pessoa pe= pessoaRepository.findById(id_pessoa).isPresent() ? 
				pessoaRepository.findById(id_pessoa).get(): null;

		if(participanteRepository.findByPessoaAndEvento(id_pessoa, id_evento) != null){
			throw new RuntimeException("Essa pessoa já fez sua inscrição no evento!");
		}
		if(e== null)
			throw new ResourceNotFoundException("Evento com id "+ id_evento + " não encontrado.");
		if(pe== null)
			throw new ResourceNotFoundException("Pessoa com id "+ id_evento + " não encontrada.");
		p.setEvento(e);
		p.setPessoa(pe);
		p.setData_maxima(LocalDate.now().plusDays(e.getDias_compensacao()));
		int ativos =  participanteRepository.countAtivos(e.getIdEvento());
		if(!(ativos < e.getQtdVagas())) {
			p.setEspera(true);
		}
		participanteRepository.save(p);
	}
	
	public Participante remover(Long id) {
		//Participante p = participanteRepository.findById(id).get();
		Participante p = participanteRepository.findById(id).isPresent() ? 
				participanteRepository.findById(id).get(): null;
		if(p == null)
			throw new ResourceNotFoundException("Participante com id "+ id + " não encontrado.");
		if(p.isEspera()) {
			participanteRepository.deleteById(p.getIdParticipantes());
		} else {
			participanteRepository.deleteById(p.getIdParticipantes());
			Participante lista = participanteRepository.findByEspera(p.getEvento().getIdEvento(), true);
			if(lista != null) {
				lista.setEspera(false);
				lista.setData_maxima(LocalDate.now().plusDays(lista.getEvento().getDias_compensacao()));
				participanteRepository.save(lista);
				return lista;
			}
		}
		return null;
	}
	
	public void confirmar(Long id) {
		Participante p = participanteRepository.findById(id).isPresent() ?
				participanteRepository.findById(id).get() : null;
		if(p == null) {
			throw new ResourceNotFoundException("Particiipante com id " + id + " não encontrado.");
		}
		p.setConfirmado(true);
		participanteRepository.save(p);
	}
	
}
