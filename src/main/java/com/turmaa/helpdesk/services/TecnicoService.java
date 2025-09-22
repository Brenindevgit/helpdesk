package com.turmaa.helpdesk.services;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // IMPORTANTE: Adicionar este import
import org.springframework.stereotype.Service;

import com.turmaa.helpdesk.domain.Pessoa;
import com.turmaa.helpdesk.domain.Tecnico;
import com.turmaa.helpdesk.domain.dtos.TecnicoDTO;
import com.turmaa.helpdesk.repositories.PessoaRepository;
import com.turmaa.helpdesk.repositories.TecnicoRepository;
import com.turmaa.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.turmaa.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {

    @Autowired
    private TecnicoRepository repository;
    @Autowired
    private PessoaRepository pessoaRepository;
    
    // 1. INJETANDO O CRIPTOGRAFADOR DE SENHA
    @Autowired
    private BCryptPasswordEncoder encoder;
    
    // ... (métodos findById e findAll continuam iguais) ...
    public Tecnico findById(Integer id) {
        Optional<Tecnico> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! id: " + id));
    }
    
    public List<Tecnico> findAll() {
        return repository.findAll();
    }
    
    public Tecnico create(TecnicoDTO objDTO) {
        objDTO.setId(null);
        validaPorCpfEEmail(objDTO);
        Tecnico newObj = new Tecnico(objDTO);
        // 2. CRIPTOGRAFANDO A SENHA ANTES DE SALVAR
        newObj.setSenha(encoder.encode(objDTO.getSenha()));
        return repository.save(newObj);
    }

	public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
	    objDTO.setId(id);
	    Tecnico oldObj = findById(id);
	    validaPorCpfEEmail(objDTO);
	    oldObj = new Tecnico(objDTO); // Este construtor precisa ser criado em Tecnico.java
	    // 2. CRIPTOGRAFANDO A SENHA ANTES DE SALVAR (se ela for alterada)
	    oldObj.setSenha(encoder.encode(objDTO.getSenha()));
	    return repository.save(oldObj);
	}
	
	// ... (método delete e validaPorCpfEEmail continuam iguais) ...
	public void delete(Integer id) {
    	Tecnico obj = findById(id);
    	if(obj.getChamados().size() > 0) {
    		throw new DataIntegrityViolationException("Técnico possui ordens de serviço e não pode ser deletado!");
    	}
    	repository.deleteById(id);
    }
	
	private void validaPorCpfEEmail(TecnicoDTO objDTO) {
	    Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
	    if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
	        throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
	    }

	    obj = pessoaRepository.findByEmail(objDTO.getEmail());
	    if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
	        throw new DataIntegrityViolationException("E-mail já cadastrado no sistema!");
	    }
	}
}