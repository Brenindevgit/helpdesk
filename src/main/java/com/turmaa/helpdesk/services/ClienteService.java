package com.turmaa.helpdesk.services;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.turmaa.helpdesk.domain.Pessoa;
import com.turmaa.helpdesk.domain.Cliente;
import com.turmaa.helpdesk.domain.dtos.ClienteDTO;
import com.turmaa.helpdesk.domains.enums.Perfil; 
import com.turmaa.helpdesk.repositories.PessoaRepository;
import com.turmaa.helpdesk.repositories.ClienteRepository;
import com.turmaa.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.turmaa.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;
    
    public Cliente findById(Integer id) {
        Optional<Cliente> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id));
    }
    
    public List<Cliente> findAll() {
        return repository.findAll();
    }

    public Cliente create(ClienteDTO objDTO) {
        objDTO.setId(null);
        validaPorCpfEEmail(objDTO);
        
        // Adicionada regra de negócio.
        // Para garantir a segurança, eu limpo qualquer perfil que possa ter sido enviado na requisição...
        objDTO.getPerfis().clear();
        // ...e adiciono manualmente apenas o perfil de CLIENTE.
        objDTO.addPerfil(Perfil.CLIENTE);
        
        Cliente newObj = new Cliente(objDTO);
        newObj.setSenha(encoder.encode(objDTO.getSenha()));
        return repository.save(newObj);
    }
    
    public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
        objDTO.setId(id);
        Cliente oldObj = findById(id);
        validaPorCpfEEmail(objDTO);
        oldObj = new Cliente(objDTO);
        oldObj.setSenha(encoder.encode(objDTO.getSenha()));
        return repository.save(oldObj);
    }
    
    public void delete(Integer id) {
        Cliente obj = findById(id);
        if(obj.getChamados().size() > 0) {
            throw new DataIntegrityViolationException("Cliente possui ordens de serviço e não pode ser deletado!");
        }
        repository.deleteById(id);
    }
    
    private void validaPorCpfEEmail(ClienteDTO objDTO) {
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