package com.turmaa.helpdesk.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Entity;
//import javax.persistence.EnumType; // 1. IMPORT NECESSÁRIO PARA A SUGESTÃO
//import javax.persistence.Enumerated; // 2. IMPORT NECESSÁRIO PARA A SUGESTÃO
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.turmaa.helpdesk.domains.enums.Prioridade;
import com.turmaa.helpdesk.domains.enums.Status;

/**
 * Entidade que representa um Chamado (ticket de suporte) no sistema.
 */
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

    /*
     * SUGESTÃO DE MELHORIA:
     * Atualmente, estamos armazenando a prioridade como um Integer (0, 1, 2) no banco.
     * Isso funciona, mas nos obriga a fazer a conversão manual (toEnum, getCodigo)
     * nos getters e setters, o que pode gerar erros e deixa o banco menos legível.
     * * Uma abordagem mais moderna e segura com o JPA é usar a anotação @Enumerated.
     * O código ficaria assim:
     * * @Enumerated(EnumType.STRING)
     * private Prioridade prioridade;
     * * Vantagens:
     * 1. O código da classe fica muito mais limpo, sem a necessidade de conversões manuais.
     * 2. O banco de dados armazena o texto ("BAIXA", "MEDIA", "ALTA"), o que é muito mais claro.
     * 3. O Java garante que apenas valores válidos do Enum possam ser atribuídos (type safety).
     */
    private Integer prioridade;
    
    /*
     * SUGESTÃO DE MELHORIA:
     * A mesma sugestão acima se aplica ao campo 'status'. Usando @Enumerated,
     * o código ficaria mais limpo e o banco de dados mais legível.
     * * @Enumerated(EnumType.STRING)
     * private Status status;
     */
    private Integer status;
    
    private String titulo;
    private String observacoes;

    /**
     * Relacionamento "Muitos para Um" com a entidade Tecnico.
     * Muitos chamados podem pertencer a um único técnico.
     * @JoinColumn especifica a coluna de chave estrangeira na tabela de Chamado.
     */
    @ManyToOne
    @JoinColumn(name = "tecnico_id")
    private Tecnico tecnico;

    /**
     * Relacionamento "Muitos para Um" com a entidade Cliente.
     * Muitos chamados podem pertencer a um único cliente.
     */
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    /**
     * Construtor padrão.
     * Inicializa o chamado com prioridade BAIXA e status ABERTO por padrão.
     */
    public Chamado() {
        super();
        this.setPrioridade(Prioridade.BAIXA);
        this.setStatus(Status.ABERTO);
    }

    /**
     * Construtor com parâmetros.
     */
    public Chamado(Integer id, Prioridade prioridade, Status status, String titulo,
                   String observacoes, Tecnico tecnico, Cliente cliente) {
        super();
        this.id = id;
        this.prioridade = prioridade.getCodigo();
        this.status = status.getCodigo();
        this.titulo = titulo;
        this.observacoes = observacoes;
        this.tecnico = tecnico;
        this.cliente = cliente;
    }

    // Getters e Setters
    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public LocalDate getDataAbertura() { return dataAbertura; }
    public void setDataAbertura(LocalDate dataAbertura) { this.dataAbertura = dataAbertura; }

    public LocalDate getDataFechamento() { return dataFechamento; }
    public void setDataFechamento(LocalDate dataFechamento) { this.dataFechamento = dataFechamento; }

    /**
     * Retorna a prioridade no formato Enum, convertendo o código numérico.
     */
    public Prioridade getPrioridade() { return Prioridade.toEnum(prioridade); }
    public void setPrioridade(Prioridade prioridade) { this.prioridade = prioridade.getCodigo(); }

    /**
     * Retorna o status no formato Enum, convertendo o código numérico.
     */
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