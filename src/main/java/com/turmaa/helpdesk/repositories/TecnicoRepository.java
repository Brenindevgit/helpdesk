package com.turmaa.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.turmaa.helpdesk.domain.Tecnico;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {
	

}