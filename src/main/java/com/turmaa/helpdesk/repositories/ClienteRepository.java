package com.turmaa.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.turmaa.helpdesk.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
	
}
