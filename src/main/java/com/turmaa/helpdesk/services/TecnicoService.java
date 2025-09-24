package com.turmaa.helpdesk.services;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.turmaa.helpdesk.domain.Pessoa;
import com.turmaa.helpdesk.domain.Tecnico;
import com.turmaa.helpdesk.domain.dtos.TecnicoDTO;
import com.turmaa.helpdesk.domains.enums.Perfil;
import com.turmaa.helpdesk.repositories.PessoaRepository;
import com.turmaa.helpdesk.repositories.TecnicoRepository;
import com.turmaa.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.turmaa.helpdesk.services.exceptions.ObjectNotFoundException;

/**
 * Classe de Serviço para a entidade Tecnico.
 * Contém toda a lógica de negócio e regras para as operações de CRUD de Técnicos.
 */
@Service
public class TecnicoService {

    @Autowired
    private TecnicoRepository repository;
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;
    
    /**
     * Busca um Técnico pelo seu ID.
     * @param id O ID do Técnico.
     * @return A entidade Tecnico correspondente.
     * @throws ObjectNotFoundException se o ID não for encontrado.
     */
    public Tecnico findById(Integer id) {
        Optional<Tecnico> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! id: " + id));
    }
    
    /**
     * Retorna uma lista de todos os Técnicos.
     * @return Uma lista de entidades Tecnico.
     */
    public List<Tecnico> findAll() {
        return repository.findAll();
    }
    
    /**
     * Cria um novo Técnico.
     * @param objDTO O DTO contendo os dados do novo técnico.
     * @return A entidade Tecnico recém-criada.
     */
    public Tecnico create(TecnicoDTO objDTO) {
        objDTO.setId(null); // Garante que a operação é de criação.
        validaPorCpfEEmail(objDTO); // Valida se CPF e Email já existem.
        
        // Regra de negócio para impedir a criação de um técnico com perfil de ADMIN.
        if(objDTO.getPerfis().contains(Perfil.ADMIN)){
            throw new DataIntegrityViolationException("Um técnico não pode ser criado com perfil de ADMIN!");
        }
        
        Tecnico newObj = new Tecnico(objDTO);
        newObj.setSenha(encoder.encode(objDTO.getSenha())); // Criptografa a senha.
        return repository.save(newObj);
    }

    /**
     * Atualiza os dados de um Técnico.
     * @param id O ID do Técnico a ser atualizado.
     * @param objDTO O DTO com os novos dados.
     * @return A entidade Tecnico atualizada.
     */
	public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
	    objDTO.setId(id);
	    Tecnico oldObj = findById(id);
	    validaPorCpfEEmail(objDTO);
	    oldObj = new Tecnico(objDTO); 
	    oldObj.setSenha(encoder.encode(objDTO.getSenha()));
	    return repository.save(oldObj);
	}
	
    /**
     * Deleta um Técnico.
     * @param id O ID do Técnico a ser deletado.
     * @throws DataIntegrityViolationException se o técnico tiver chamados associados.
     */
	public void delete(Integer id) {
    	Tecnico obj = findById(id);
    	// Regra de negócio: Um técnico não pode ser deletado se tiver chamados em seu nome.
    	if(obj.getChamados().size() > 0) {
    		throw new DataIntegrityViolationException("Técnico possui ordens de serviço e não pode ser deletado!");
    	}
    	repository.deleteById(id);
    }
	
	/**
	 * Método privado para validar se o CPF ou Email já existem na base de dados,
     * evitando duplicidade.
	 * @param objDTO O DTO contendo os dados a serem validados.
	 */
	private void validaPorCpfEEmail(TecnicoDTO objDTO) {
	    Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
	    // A validação permite que o CPF seja o mesmo se for da mesma pessoa (mesmo ID).
	    if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
	        throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
	    }

	    obj = pessoaRepository.findByEmail(objDTO.getEmail());
	    // A validação permite que o Email seja o mesmo se for da mesma pessoa (mesmo ID).
	    if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
	        throw new DataIntegrityViolationException("E-mail já cadastrado no sistema!");
	    }
	}
}