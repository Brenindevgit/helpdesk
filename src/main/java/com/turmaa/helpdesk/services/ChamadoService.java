package com.turmaa.helpdesk.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turmaa.helpdesk.domain.Chamado;
import com.turmaa.helpdesk.domain.Cliente;
import com.turmaa.helpdesk.domain.Tecnico;
import com.turmaa.helpdesk.domain.dtos.ChamadoDTO;
import com.turmaa.helpdesk.domains.enums.Prioridade;
import com.turmaa.helpdesk.domains.enums.Status;
import com.turmaa.helpdesk.repositories.ChamadoRepository;
import com.turmaa.helpdesk.services.exceptions.ObjectNotFoundException;


@Service
public class ChamadoService {
	
	@Autowired
	private ChamadoRepository repository;
	@Autowired
	private TecnicoService tecnicoService;
	@Autowired
	private ClienteService clienteService;
	
	public Chamado findById(Integer id) {
		Optional<Chamado> obj = repository.findById(id);
		return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! ID: " +id));
	}
	
	public List<Chamado> findAll(){
		return repository.findAll();
	}
	
	
	public Chamado create (ChamadoDTO objDTO) {
		return repository.save(fromDTO(objDTO));
	}
	
	public Chamado update(Integer id, @Valid ChamadoDTO objDTO) {
		objDTO.setId(null);
		Chamado oldObj = findById(id);
		oldObj = fromDTO(objDTO);
		return repository.save(oldObj);
	}
	
	
	private Chamado fromDTO(ChamadoDTO objDTO) {
		Tecnico tecnico = tecnicoService.findById(objDTO.getTecnicoId());
		Cliente cliente = clienteService.findById(objDTO.getClienteId());
		
		Chamado chamado = new Chamado();
		if(objDTO.getId() != null) {
			chamado.setId(objDTO.getId());
		}
		
		if(objDTO.getStatus().equals(2)) {
			chamado.setDataFechamento(LocalDateTime.now());
		}
		
		chamado.setTecnico(tecnico);
		chamado.setCliente(cliente);
		chamado.setPrioridade(Prioridade.toEnum(objDTO.getPrioridade()));
		chamado.setStatus(Status.toEnum(objDTO.getStatus()));
		chamado.setTitulo(objDTO.getTitulo());
		chamado.setObservacoes(objDTO.getObservacoes());
		
		return chamado;
	}
	
	
	public void delete(Integer id) {
    	Chamado obj = findById(id); 	
    	repository.deleteById(id);
    }

}