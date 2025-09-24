package com.turmaa.helpdesk.Resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; 
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.turmaa.helpdesk.domain.Chamado;
import com.turmaa.helpdesk.domain.dtos.ChamadoDTO;
import com.turmaa.helpdesk.services.ChamadoService;

@RestController
@RequestMapping(value = "/chamados")
public class ChamadoResources {
	
	@Autowired
	private ChamadoService service;
	
	/**
	 * Endpoint para buscar um Chamado por ID.
	 * Qualquer usuário autenticado pode buscar um chamado.
	 */
	@GetMapping(value="/{id}")
	public ResponseEntity<ChamadoDTO> findById(@PathVariable Integer id){
		Chamado obj = service.findById(id);
		return ResponseEntity.ok().body(new ChamadoDTO(obj));
	}
	
	/**
	 * Endpoint para listar todos os Chamados.
	 * Acesso restrito a ADMINS ou TECNICOS. Clientes não podem ver todos os chamados.
	 */
	@PreAuthorize("hasAnyRole('ADMIN', 'TECNICO')") // REGRA DE AUTORIZAÇÃO 
	@GetMapping
	public ResponseEntity<List<ChamadoDTO>> findAll(){
		List<Chamado> list = service.findAll();
		List<ChamadoDTO> listDTO = list.stream().map(obj -> new ChamadoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	/**
	 * Endpoint para criar um novo Chamado.
	 * Acesso restrito a CLIENTES (pois técnicos e admins não abrem chamados para si mesmos).
	 */
	@PreAuthorize("hasAnyRole('CLIENTE')") // REGRA DE AUTORIZAÇÃO 
	@PostMapping
	public ResponseEntity<ChamadoDTO> create (@Valid @RequestBody ChamadoDTO objDTO){
		Chamado obj = service.create(objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	/**
	 * Endpoint para atualizar um Chamado.
	 * Acesso restrito a TECNICOS e ADMINS, que são quem gerenciam os chamados.
	 */
	@PreAuthorize("hasAnyRole('ADMIN', 'TECNICO')") // REGRA DE AUTORIZAÇÃO 
	@PutMapping(value = "/{id}")
	public ResponseEntity<ChamadoDTO> update(@PathVariable Integer id, @Valid @RequestBody ChamadoDTO objDTO){
		Chamado newOBJ = service.update(id, objDTO);
		return ResponseEntity.ok().body(new ChamadoDTO(newOBJ));
	}
	
	// OBS: O método DELETE para chamados pode não ser uma boa prática de negócio
	// (geralmente se cancela um chamado, mas não se apaga o registro).
	// Mas, mantendo a funcionalidade de CRUD completo:
	/**
	 * Endpoint para deletar um Chamado.
	 * Acesso restrito a ADMINS.
	 */
	@PreAuthorize("hasAnyRole('ADMIN')") //REGRA DE AUTORIZAÇÃO 
	@DeleteMapping (value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}