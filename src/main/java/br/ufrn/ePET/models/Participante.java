package br.ufrn.ePET.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

@Entity
public class Participante {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idParticipante;
	
	@ManyToOne
	@NotEmpty
	@JoinColumn(name = "id_pessoa")
	private Pessoa pessoa;
	
	@ManyToOne
	@NotEmpty
	@JoinColumn(name = "id_evento")
	private Evento evento;
	
	@NotEmpty
	private boolean confirmado;
	
	@NotEmpty
	private boolean espera;

	public long getIdParticipantes() {
		return idParticipante;
	}

	public void setIdParticipantes(long idParticipantes) {
		this.idParticipante = idParticipantes;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public boolean isConfirmado() {
		return confirmado;
	}

	public void setConfirmado(boolean confirmado) {
		this.confirmado = confirmado;
	}
}