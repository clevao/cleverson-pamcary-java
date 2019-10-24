package br.com.pamcary.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.pamcary.entity.Pessoa;
import br.com.pamcary.repository.PessoaRepository;
import br.com.pamcary.util.Util;

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
	public List<Pessoa> findByCpf(String cpf) {
		cpf = Util.removeNonNumeric(cpf);
		return pessoaRepository.findByCpf(cpf);
	} 
	
	@Transactional
	public Pessoa save(Pessoa pessoa) {
		return pessoaRepository.save(pessoa);
	}
	
	@Transactional 
	public void delete(Optional<Pessoa> pessoa) {
		pessoaRepository.delete(pessoa.get());
	}

	/**
	 * 
	 * @param pessoa
	 * @param cadastroNovo: indica se está tratando um cadastro novo ou uma atualização
	 * @return
	 */
	@Transactional 
	public boolean validarCadastro(Pessoa pessoa, boolean cadastroNovo) {
		
		if(!Util.isValidCPF(pessoa.getCpf()))
			return false;
		
		// Para o cadastro novo, verifica se já não existe na base de dados. Se existir, não vai permitir o cadastro
		if(cadastroNovo && existeCpf(pessoa.getCpf()))
			return false;
		
		return true;
	}

	public boolean existeCpf(String cpf) {
		
		List<Pessoa> lista = findByCpf(cpf);
		return lista.size() > 0;
	}
	
}
