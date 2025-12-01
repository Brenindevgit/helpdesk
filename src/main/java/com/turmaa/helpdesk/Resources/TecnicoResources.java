package com.turmaa.helpdesk.Resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; 
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.turmaa.helpdesk.domain.Tecnico;
import com.turmaa.helpdesk.domain.dtos.TecnicoDTO;
import com.turmaa.helpdesk.services.TecnicoService;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

/**
 * Controller REST para o recurso 'Técnico'.
 * Expõe os endpoints (URLs) para as operações de CRUD relacionadas à entidade Tecnico.
 */
@RestController
@RequestMapping(value = "/tecnicos")
public class TecnicoResources {

    @Autowired
    private TecnicoService service;
    
    /**
     * Endpoint para buscar um Técnico por seu ID.
     * Aberto para qualquer usuário autenticado.
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id) {
        Tecnico obj = service.findById(id);
        return ResponseEntity.ok().body(new TecnicoDTO(obj));
    }

    
    /**
     * Endpoint para listar todos os Técnicos.
     * CORREÇÃO: Alterado para hasAnyAuthority para checar a permissão exata ('ROLE_ADMIN').
     */
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<TecnicoDTO>> findAll() {
        List<Tecnico> list = service.findAll();
        List<TecnicoDTO> listDTO = list.stream().map(obj -> new TecnicoDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }
    
    
    /**
     * Endpoint para criar um novo Técnico.
     * Acesso restrito a usuários com perfil 'ADMIN'.
     * CORREÇÃO: Alterado para hasAnyAuthority.
     */
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<TecnicoDTO> create(@Valid @RequestBody TecnicoDTO objDTO) {
        Tecnico newObj = service.create(objDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

     
    /**
     * Endpoint para atualizar um Técnico existente.
     * Acesso restrito a usuários com perfil 'ADMIN'.
     * CORREÇÃO: Alterado para hasAnyAuthority.
     */
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<TecnicoDTO> update(@PathVariable Integer id, @Valid @RequestBody TecnicoDTO objDTO) {
        Tecnico obj = service.update(id, objDTO);
        return ResponseEntity.ok().body(new TecnicoDTO(obj));
    }

    
    /**
     * Endpoint para deletar um Técnico.
     * Acesso restrito a usuários com perfil 'ADMIN'.
     * CORREÇÃO: Alterado para hasAnyAuthority.
     */
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}