package br.com.pamcary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pamcary.entity.Telefone;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Integer> {
	
	
}