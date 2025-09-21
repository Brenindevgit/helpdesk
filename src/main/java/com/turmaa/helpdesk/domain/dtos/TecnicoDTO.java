package com.turmaa.helpdesk.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.turmaa.helpdesk.domain.Tecnico;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;

public class TecnicoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    protected Integer id;
    
    @NotEmpty(message = "O campo NOME é requerido")
    protected String nome;
    
    @NotEmpty(message = "O campo CPF é requerido")
    protected String cpf;
    
    @NotEmpty(message = "O campo EMAIL é requerido")
    protected String email;
    
    @NotEmpty(message = "O campo SENHA é requerido")
    protected String senha;
    
    // O tipo do atributo 'perfil' foi alterado de Integer para Set<Integer>
    protected Set<Integer> perfis; 

    @JsonFormat(pattern = "dd/MM/yyyy")
    protected LocalDate dataCriacao = LocalDate.now();

    public TecnicoDTO() {
        super();
    }

    public TecnicoDTO(Tecnico obj) {
        super();
        this.id = obj.getId();
        this.nome = obj.getNome();
        this.cpf = obj.getCpf();
        this.email = obj.getEmail();
        this.senha = obj.getSenha();
        
        // Atribui o Set<Integer> de códigos de perfil ao novo atributo 'perfis'
        this.perfis = obj.getPerfis().stream().map(x -> x.getCodigo()).collect(Collectors.toSet()); 
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Set<Integer> getPerfis() {
		return perfis;
	}

	public void setPerfis(Set<Integer> perfis) {
		this.perfis = perfis;
	}

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
    
}