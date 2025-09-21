package com.turmaa.helpdesk.domain;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.turmaa.helpdesk.domains.enums.Prioridade;
import com.turmaa.helpdesk.domains.enums.Status;

@Entity
public class Chamado implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataAbertura = LocalDate.now();

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataFechamento;

    private Integer prioridade;
    private Integer status;
    private String titulo;
    private String observacoes;

    @ManyToOne
    @JoinColumn(name = "tecnico_id")
    private Tecnico tecnico;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    public Chamado() {
        this.prioridade = Prioridade.BAIXA.getCodigo();
        this.status = Status.ABERTO.getCodigo();
    }

    public Chamado(Integer id, Prioridade prioridade, Status status, String titulo,
                   String observacoes, Tecnico tecnico, Cliente cliente) {
        this.id = id;
        this.dataAbertura = LocalDate.now();
        this.prioridade = (prioridade == null) ? Prioridade.BAIXA.getCodigo() : prioridade.getCodigo();
        this.status = (status == null) ? Status.ABERTO.getCodigo() : status.getCodigo();
        this.titulo = titulo;
        this.observacoes = observacoes;
        this.tecnico = tecnico;
        this.cliente = cliente;
    }

    // getters e setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public LocalDate getDataAbertura() { return dataAbertura; }
    public void setDataAbertura(LocalDate dataAbertura) { this.dataAbertura = dataAbertura; }

    public LocalDate getDataFechamento() { return dataFechamento; }
    public void setDataFechamento(LocalDate dataFechamento) { this.dataFechamento = dataFechamento; }

    public Prioridade getPrioridade() { return Prioridade.toEnum(prioridade); }
    public void setPrioridade(Prioridade prioridade) { this.prioridade = prioridade.getCodigo(); }

    public Status getStatus() { return Status.toEnum(status); }
    public void setStatus(Status status) { this.status = status.getCodigo(); }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public Tecnico getTecnico() { return tecnico; }
    public void setTecnico(Tecnico tecnico) { this.tecnico = tecnico; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
}