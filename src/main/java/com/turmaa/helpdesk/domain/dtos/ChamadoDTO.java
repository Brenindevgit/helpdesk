package com.turmaa.helpdesk.domain.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import com.turmaa.helpdesk.domain.Chamado;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Data Transfer Object (DTO) para a entidade Chamado.
 * Define a estrutura de dados que será usada para criar, atualizar e visualizar
 * Chamados através da API.
 */
public class ChamadoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	// CORREÇÃO: O padrão foi ajustado para "dd/MM/yyyy" para corresponder ao tipo LocalDate.
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataAbertura;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataFechamento;

	@NotNull(message = "O campo PRIORIDADE é requerido")
	private Integer prioridade;
    // Campo extra para facilitar a exibição no front-end.
	private String nomePrioridade;

	@NotNull(message = "O campo STATUS é requerido")
	private Integer status;
    // Campo extra para facilitar a exibição no front-end.
	private String nomeStatus;

	@NotNull(message = "O campo TÍTULO é requerido")
	private String titulo;

	@NotNull(message = "O campo OBSERVAÇÕES é requerido")
	private String observacoes;

	/**
     * ID do técnico associado. Na criação, a API recebe apenas o ID.
     */
	@NotNull(message = "O campo TÉCNICO é requerido")
	private Integer tecnicoId;
    /**
     * Nome do técnico, usado para facilitar a exibição dos dados no front-end
     * sem a necessidade de uma nova requisição.
     */
	private String nomeTecnico;

	/**
     * ID do cliente associado.
     */
	@NotNull(message = "O campo CLIENTE é requerido")
	private Integer clienteId;
    /**
     * Nome do cliente, para facilitar a exibição no front-end.
     */
	private String nomeCliente;

	public ChamadoDTO() {
		super();
	}

	/**
     * Construtor que converte uma entidade Chamado para um ChamadoDTO.
     * Este é um exemplo de design "flattened", onde, em vez de aninhar objetos
     * inteiros, extraímos apenas as informações relevantes (ID e nome) das entidades
     * relacionadas (Tecnico e Cliente).
     * @param obj A entidade Chamado a ser convertida.
     */
	public ChamadoDTO(Chamado obj) {
		this.id = obj.getId();
		this.dataAbertura = obj.getDataAbertura();
		this.dataFechamento = obj.getDataFechamento();
		this.prioridade = obj.getPrioridade().getCodigo();
		this.nomePrioridade = obj.getPrioridade().getDescricao();
		this.status = obj.getStatus().getCodigo();
		this.nomeStatus = obj.getStatus().getDescricao();
		this.titulo = obj.getTitulo();
		this.observacoes = obj.getObservacoes();
		this.tecnicoId = obj.getTecnico().getId();
		this.nomeTecnico = obj.getTecnico().getNome();
		this.clienteId = obj.getCliente().getId();
		this.nomeCliente = obj.getCliente().getNome();
	}

    // --- GETTERS E SETTERS ---
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(LocalDate dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public LocalDate getDataFechamento() {
		return dataFechamento;
	}

	public void setDataFechamento(LocalDate dataFechamento) {
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
}