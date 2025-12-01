package com.turmaa.helpdesk.Resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.turmaa.helpdesk.domain.Chamado;
import com.turmaa.helpdesk.domain.dtos.ChamadoDTO;
import com.turmaa.helpdesk.services.ChamadoService;

/**
 * Controller REST para o recurso 'Chamado'.
 * Expõe os endpoints da API para as operações de CRUD relacionadas à entidade Chamado.
 */
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
	 * CORREÇÃO: Alterado para hasAnyAuthority para checar a permissão exata ('ROLE_ADMIN', 'ROLE_TECNICO').
	 */
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_TECNICO')")
	@GetMapping
	public ResponseEntity<List<ChamadoDTO>> findAll(){
		List<Chamado> list = service.findAll();
		List<ChamadoDTO> listDTO = list.stream()
									  .map(obj -> new ChamadoDTO(obj))
									  .collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	/**
	 * Endpoint para criar um novo Chamado.
	 * Acesso restrito a CLIENTES.
	 * CORREÇÃO: Alterado para hasAnyAuthority.
	 */
	@PreAuthorize("hasAnyAuthority('ROLE_CLIENTE')")
	@PostMapping
	public ResponseEntity<ChamadoDTO> create (@Valid @RequestBody ChamadoDTO objDTO){
		Chamado obj = service.create(objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(obj.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	/**
	 * Endpoint para atualizar um Chamado.
	 * Acesso restrito a TECNICOS e ADMINS.
	 * CORREÇÃO: Alterado para hasAnyAuthority.
	 */
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_TECNICO')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<ChamadoDTO> update(@PathVariable Integer id, @Valid @RequestBody ChamadoDTO objDTO){
		Chamado newOBJ = service.update(id, objDTO);
		return ResponseEntity.ok().body(new ChamadoDTO(newOBJ));
	}
	
	/**
	 * Endpoint para deletar um Chamado.
	 * Acesso restrito a ADMINS.
	 * CORREÇÃO: Alterado para hasAnyAuthority.
	 */
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	@DeleteMapping (value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}