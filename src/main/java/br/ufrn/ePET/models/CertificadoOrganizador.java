package br.ufrn.ePET.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class CertificadoOrganizador {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idCertificado;
	@ManyToOne
	//@NotEmpty
	@JoinColumn(name = "id_organizadores")
	private Organizadores organizadores;
	
	@Column(columnDefinition = "VARCHAR(100)")
	@ApiModelProperty(
	  value = "Hash code contido na declaração",
	  dataType = "String",
	  example = "260ed4cbb913e500d6626f7fd60bea5f191ea5ad")
	private String hash;

	@Column(name="dataCriacao", columnDefinition = "DATE")
	private LocalDate dataCriacao;

	public long getIdCertificado() {
		return idCertificado;
	}

	public void setIdCertificado(long idCertificado) {
		this.idCertificado = idCertificado;
	}

	public Organizadores getOrganizadores() {
		return organizadores;
	}

	public void setOrganizadores(Organizadores organizadores) {
		this.organizadores = organizadores;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
}
