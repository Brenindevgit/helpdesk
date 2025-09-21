package com.turmaa.helpdesk.domain.dtos;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;

import com.turmaa.helpdesk.domain.Chamado;
import com.fasterxml.jackson.annotation.JsonFormat;

public class ChamadoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime dataAbertura;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime dataFechamento;

	@NotNull(message = "O campo PRIORIDADE é requerido")
	private Integer prioridade;
	private String nomePrioridade;

	@NotNull(message = "O campo STATUS é requerido")
	private Integer status;
	private String nomeStatus;

	@NotNull(message = "O campo TÍTULO é requerido")
	private String titulo;

	@NotNull(message = "O campo OBSERVAÇÕES é requerido")
	private String observacoes;

	@NotNull(message = "O campo TÉCNICO é requerido")
	private Integer tecnicoId;
	private String nomeTecnico;

	@NotNull(message = "O campo CLIENTE é requerido")
	private Integer clienteId;
	private String nomeCliente;

	public ChamadoDTO() {
		super();
	}

	public ChamadoDTO(Chamado obj) {
		this.id = obj.getId();
		this.dataAbertura = obj.getDataAbertura();
		
		if(obj.getDataFechamento() != null) {
			this.dataFechamento = obj.getDataFechamento();
		}
		
		this.prioridade = obj.getPrioridade().getCodigo();
		this.nomePrioridade = obj.getPrioridade().getDescricao();
		this.status = obj.getStatus().getCodigo();
		this.nomeStatus = obj.getStatus().getDescricao();
		this.titulo = obj.getTitulo();
		this.observacoes = obj.getObservacoes();

		if(obj.getTecnico() != null) {
			this.tecnicoId = obj.getTecnico().getId();
			this.nomeTecnico = obj.getTecnico().getNome();
		}
		
		if(obj.getCliente() != null) {
			this.clienteId = obj.getCliente().getId();
			this.nomeCliente = obj.getCliente().getNome();
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(LocalDateTime dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public LocalDateTime getDataFechamento() {
		return dataFechamento;
	}

	public void setDataFechamento(LocalDateTime dataFechamento) {
		this.dataFechamento = dataFechamento;
	}

	public Integer getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(Integer prioridade) {
		this.prioridade = prioridade;
	}

	public String getNomePrioridade() {
		return nomePrioridade;
	}

	public void setNomePrioridade(String nomePrioridade) {
		this.nomePrioridade = nomePrioridade;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getNomeStatus() {
		return nomeStatus;
	}

	public void setNomeStatus(String nomeStatus) {
		this.nomeStatus = nomeStatus;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public Integer getTecnicoId() {
		return tecnicoId;
	}

	public void setTecnicoId(Integer tecnicoId) {
		this.tecnicoId = tecnicoId;
	}

	public String getNomeTecnico() {
		return nomeTecnico;
	}

	public void setNomeTecnico(String nomeTecnico) {
		this.nomeTecnico = nomeTecnico;
	}

	public Integer getClienteId() {
		return clienteId;
	}

	public void setClienteId(Integer clienteId) {
		this.clienteId = clienteId;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
