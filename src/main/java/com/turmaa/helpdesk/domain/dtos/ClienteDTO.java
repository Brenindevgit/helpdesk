package com.turmaa.helpdesk.domain.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.constraints.NotBlank;
import com.turmaa.helpdesk.domain.Cliente;
import com.turmaa.helpdesk.domains.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data Transfer Object (DTO) para a entidade Cliente.
 * Define a estrutura de dados que será usada para interações com a API
 * que envolvam a entidade Cliente.
 */
public class ClienteDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    protected Integer id;

    @NotBlank(message = "O campo NOME é requerido")
    protected String nome;

    @NotBlank(message = "O campo CPF é requerido")
    protected String cpf;

    @NotBlank(message = "O campo EMAIL é requerido")
    protected String email;

    /**
     * A anotação @JsonProperty com acesso WRITE_ONLY é uma medida de segurança crucial.
     * Ela permite que este campo seja recebido em requisições (ex: criação de cliente),
     * mas o impede de ser incluído em respostas JSON, evitando a exposição da senha.
     */
    @NotBlank(message = "O campo SENHA é requerido")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    protected String senha;

    protected Set<Integer> perfis = new HashSet<>();

    @JsonFormat(pattern = "dd/MM/yyyy")
    protected LocalDate dataCriacao;

    public ClienteDTO() {
        super();
        addPerfil(Perfil.CLIENTE);
    }

    /**
     * Construtor que converte uma entidade Cliente para um ClienteDTO.
     * @param obj A entidade Cliente a ser convertida.
     */
    public ClienteDTO(Cliente obj) {
        super();
        this.id = obj.getId();
        this.nome = obj.getNome();
        this.cpf = obj.getCpf();
        this.email = obj.getEmail();
        this.perfis = obj.getPerfis().stream().map(x -> x.getCodigo()).collect(Collectors.toSet());
        this.dataCriacao = obj.getDataCriacao();
    }
    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    
    public LocalDate getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDate dataCriacao) { this.dataCriacao = dataCriacao; }
    
    /**
     * Retorna o conjunto de códigos dos perfis associados.
     * Este método agora retorna o mesmo tipo do atributo (Set<Integer>),
     * mantendo a consistência do DTO como um simples transportador de dados.
     * @return Um Set<Integer> contendo os códigos dos perfis.
     */
    public Set<Integer> getPerfis() {
        return perfis;
    }

    /**
     * Método auxiliar para adicionar um perfil ao DTO a partir do Enum.
     * @param perfil O Perfil (Enum) a ser adicionado.
     */
    public void addPerfil(Perfil perfil) {
        this.perfis.add(perfil.getCodigo());
    }
}