package com.turmaa.helpdesk.services;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turmaa.helpdesk.domain.Cliente;
import com.turmaa.helpdesk.domain.dtos.ClienteDTO;
import com.turmaa.helpdesk.repositories.ClienteRepository;
import com.turmaa.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    public Cliente findById(Integer id) {
        Optional<Cliente> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id));
    }

    public Cliente create(ClienteDTO objDTO) {
        // Lógica de criação
        return null;
    }

    public Cliente update(Integer id, ClienteDTO objDTO) {
        Cliente clienteExistente = findById(id);
        
        // Atualiza os campos do cliente existente com os dados do DTO
        clienteExistente.setNome(objDTO.getNome());
        clienteExistente.setCpf(objDTO.getCpf());
        clienteExistente.setEmail(objDTO.getEmail());

        return repository.save(clienteExistente);
    }
    
    public void delete(Integer id) {
        findById(id);
        repository.deleteById(id);
    }
}