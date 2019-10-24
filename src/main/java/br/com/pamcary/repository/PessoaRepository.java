package br.com.pamcary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pamcary.entity.Pessoa;



@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {
	
	List<Pessoa> findByCpf(String cpf);
}