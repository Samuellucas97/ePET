package br.ufrn.ePET.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import br.ufrn.ePET.models.Pessoa;
import br.ufrn.ePET.repository.PessoaRepository;
import br.ufrn.ePET.repository.Tipo_UsuarioRepository;


@Service
public class PessoaService {
	
	private final PessoaRepository pessoaRepository;
	private final Tipo_UsuarioRepository tipo_UsuarioRespository;
	
	@Autowired
	public PessoaService(PessoaRepository pessoaRepository, Tipo_UsuarioRepository tipo_UsuarioRespository) {
		this.pessoaRepository = pessoaRepository;
		this.tipo_UsuarioRespository = tipo_UsuarioRespository;
	}
	
	//@Secured("ROLE_petiano")
	public Pessoa buscar(Long id) {
		try {
			return pessoaRepository.findById(id).get();
		} catch (Exception e) {
			return null;
		}
	}
	
	//@Secured("ROLE_tutor")
	public List<Pessoa> buscar(){
		try {
			return pessoaRepository.findAll();
		} catch (Exception e) {
			return null;
		}
	}
	
	public Pessoa salvar(Long id, Pessoa pessoa){
		try {
			pessoa.setTipo_usuario(tipo_UsuarioRespository.findById(id).get());
			return pessoaRepository.save(pessoa);
		} catch (Exception e) {
			return null;
		}
	}
	
}
