package br.com.pamcary.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.pamcary.entity.Pessoa;
import br.com.pamcary.repository.PessoaRepository;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Transactional
	public List<Pessoa> findAll() {
		return pessoaRepository.findAll();
	}
	
	@Transactional
	public Optional<Pessoa> findById(Integer id) {
		return pessoaRepository.findById(id);
	}
	
	@Transactional
	public Pessoa save(Pessoa pessoa) {
		return pessoaRepository.save(pessoa);
	}
	
	@Transactional 
	public void delete(Optional<Pessoa> pessoa) {
		pessoaRepository.delete(pessoa.get());
	}
	
}
