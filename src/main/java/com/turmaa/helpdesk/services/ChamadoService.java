package com.turmaa.helpdesk.services;

import java.time.LocalDate;
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

/**
 * Classe de serviço que contém a lógica de negócio para as operações com Chamados.
 */
@Service
public class ChamadoService {
	
	@Autowired
	private ChamadoRepository repository;
	@Autowired
	private TecnicoService tecnicoService;
	@Autowired
	private ClienteService clienteService;
	
	/**
	 * Busca um Chamado pelo seu ID.
	 */
	public Chamado findById(Integer id) {
		Optional<Chamado> obj = repository.findById(id);
		return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! ID: " + id));
	}
	
	/**
	 * Retorna uma lista com todos os Chamados.
	 */
	public List<Chamado> findAll(){
		return repository.findAll();
	}
	
	/**
	 * Cria um novo Chamado no banco de dados.
	 */
	public Chamado create(@Valid ChamadoDTO objDTO) {
		// Usa o método privado para criar uma nova instância de Chamado a partir do DTO.
		return repository.save(newChamado(objDTO));
	}
	
	/**
	 * Atualiza um Chamado existente.
	 */
	public Chamado update(Integer id, @Valid ChamadoDTO objDTO) {
		objDTO.setId(id); // Seta o ID no DTO para garantir que estamos atualizando o chamado correto.
		Chamado oldObj = findById(id); // Busca o chamado existente no banco.
		
		// Atualiza os dados do objeto 'oldObj' com base nas informações do DTO.
		oldObj.setPrioridade(Prioridade.toEnum(objDTO.getPrioridade()));
		oldObj.setStatus(Status.toEnum(objDTO.getStatus()));
		oldObj.setTitulo(objDTO.getTitulo());
		oldObj.setObservacoes(objDTO.getObservacoes());
		
		// Associa o técnico e o cliente (caso tenham sido alterados).
		// Verifica se o tecnicoId veio no DTO antes de buscar
		if(objDTO.getTecnicoId() != null) {
			oldObj.setTecnico(tecnicoService.findById(objDTO.getTecnicoId()));
		}
		
		oldObj.setCliente(clienteService.findById(objDTO.getClienteId()));
		
		// Aplica a regra de negócio para data de fechamento.
		if(oldObj.getStatus().equals(Status.ENCERRADO)) {
			oldObj.setDataFechamento(LocalDate.now());
		}
		
		return repository.save(oldObj); // Salva o objeto 'oldObj' atualizado.
	}
	
	/**
	 * Método auxiliar privado para criar uma NOVA instância de Chamado a partir de um DTO.
	 * Usado apenas no método create.
	 */
	private Chamado newChamado(ChamadoDTO objDTO) {
		// CORREÇÃO: Verifica se o ID do técnico é nulo antes de buscar
		Tecnico tecnico = null;
		if(objDTO.getTecnicoId() != null) {
			tecnico = tecnicoService.findById(objDTO.getTecnicoId());
		}
		
		Cliente cliente = clienteService.findById(objDTO.getClienteId());
		
		Chamado chamado = new Chamado();
		// Se o ID vier no DTO de criação (não deveria, mas por segurança), nós o setamos.
		if(objDTO.getId() != null) {
			chamado.setId(objDTO.getId());
		}
		
		// Cria o chamado com os dados do DTO e as entidades de Técnico e Cliente encontradas.
		chamado.setTecnico(tecnico);
		chamado.setCliente(cliente);
		chamado.setPrioridade(Prioridade.toEnum(objDTO.getPrioridade()));
		chamado.setStatus(Status.toEnum(objDTO.getStatus()));
		chamado.setTitulo(objDTO.getTitulo());
		chamado.setObservacoes(objDTO.getObservacoes());
		
		return chamado;
	}
	/**
	 * Deleta um Chamado do banco de dados.
	 * @param id O ID do chamado a ser deletado.
	 */
	public void delete(Integer id) {
	    // A chamada ao findById(id) garante que o chamado existe antes de tentar deletar.
	    // Se não existir, o próprio findById já lançará a exceção ObjectNotFoundException.
	    findById(id);
	    repository.deleteById(id);
	}
}