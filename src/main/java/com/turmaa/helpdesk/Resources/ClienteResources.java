package com.turmaa.helpdesk.Resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.turmaa.helpdesk.domain.Cliente;
import com.turmaa.helpdesk.domain.dtos.ClienteDTO;
import com.turmaa.helpdesk.services.ClienteService;

/**
 * Controller REST para o recurso 'Cliente'.
 * Esta classe expõe os endpoints da API para as operações de CRUD (Create, Read, Update, Delete)
 * relacionadas à entidade Cliente.
 * * @RestController Anotação que combina @Controller e @ResponseBody, indicando que os
 * métodos desta classe retornarão objetos serializados em JSON.
 * @RequestMapping Define o caminho base para todos os endpoints definidos neste controller.
 */
@RestController
@RequestMapping(value = "/clientes")
public class ClienteResources {

    /**
     * Injeção de dependência do ClienteService para acessar a camada de lógica de negócio.
     */
    @Autowired
    private ClienteService service;

    /**
     * Endpoint para buscar um Cliente por seu ID.
     * Responde a requisições HTTP GET no caminho "/clientes/{id}".
     * * @param id O ID do Cliente, capturado da URL pela anotação @PathVariable.
     * @return ResponseEntity com status 200 (OK) e o ClienteDTO correspondente no corpo.
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> findById(@PathVariable Integer id) {
        Cliente obj = service.findById(id);
        return ResponseEntity.ok().body(new ClienteDTO(obj));
    }

    /**
     * Endpoint para listar todos os Clientes cadastrados.
     * Responde a requisições HTTP GET no caminho "/clientes".
     * * @return ResponseEntity com status 200 (OK) e uma lista de ClienteDTO no corpo.
     */
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> findAll() {
        List<Cliente> list = service.findAll();
        // Converte a lista de entidades (Cliente) para uma lista de DTOs (ClienteDTO).
        List<ClienteDTO> listDTO = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    /**
     * Endpoint para criar um novo Cliente.
     * Responde a requisições HTTP POST no caminho "/clientes".
     * * @param objDTO O ClienteDTO com os dados do novo cliente, vindo do corpo da requisição.
     * @Valid Ativa as validações definidas no DTO (ex: @NotEmpty).
     * @return ResponseEntity com status 201 (Created) e o cabeçalho 'Location' com a URI do novo recurso.
     */
    @PostMapping
    public ResponseEntity<ClienteDTO> create(@Valid @RequestBody ClienteDTO objDTO) {
        Cliente newObj = service.create(objDTO);
        // Constrói a URI do novo recurso criado para ser retornada no cabeçalho da resposta.
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    /**
     * Endpoint para atualizar os dados de um Cliente existente.
     * Responde a requisições HTTP PUT no caminho "/clientes/{id}".
     * * @param id O ID do cliente a ser atualizado.
     * @param objDTO O ClienteDTO com os novos dados.
     * @return ResponseEntity com status 200 (OK) e o ClienteDTO atualizado no corpo.
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> update(@PathVariable Integer id, @Valid @RequestBody ClienteDTO objDTO) {
        Cliente obj = service.update(id, objDTO);
        return ResponseEntity.ok().body(new ClienteDTO(obj));
    }

    /**
     * Endpoint para deletar um Cliente.
     * Responde a requisições HTTP DELETE no caminho "/clientes/{id}".
     * * @param id O ID do cliente a ser deletado.
     * @return ResponseEntity com status 204 (No Content), indicando sucesso na operação.
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}