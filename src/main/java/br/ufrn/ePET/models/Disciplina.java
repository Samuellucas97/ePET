package br.ufrn.ePET.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Disciplina {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idDisciplina;
	
	@Column(columnDefinition = "VARCHAR(45)")
	@NotEmpty
	@ApiModelProperty(
	  value = "Nome da disciplina",
	  name = "nome",
	  dataType = "String",
	  example = "Fundamentos matemáticos da computação II")
	private String nome;
	
	@Column(columnDefinition = "VARCHAR(45)", unique = true)
	@NotEmpty
	@ApiModelProperty(
	  value = "Código da disciplina",
	  dataType = "String",
	  example = "IMD1XX")
	private String codigo;

	@Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
	private boolean ativo;

	public long getIdDisciplina() {
		return idDisciplina;
	}

	public void setIdDisciplina(long idDisciplina) {
		this.idDisciplina = idDisciplina;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
}
