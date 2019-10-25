package br.com.pamcary.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
		return pessoaRepository.findAll(sortByIdAsc());
	}
	
	private Sort sortByIdAsc() {
        return new Sort(Sort.Direction.ASC, "nome");
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
	 * Método responsável por verificar se os dados estão de acordo com as regras do sistema
	 * @param pessoa
	 * @param cadastroNovo: indica se está tratando um cadastro novo ou uma atualização
	 * @return
	 */
	@Transactional 
	public boolean validarCadastro(Pessoa pessoa, boolean cadastroNovo) {
		
		if(!Util.isValidCPF(pessoa.getCpf()))
			return false;
		
		// Para o cadastro novo, verifica se já não existe na base de dados. Se existir, não vai permitir o cadastro
		if(existeCpf(pessoa.getCpf(), pessoa))
			return false;
		
		return true;
	}

	/**
	 * Método responsável por verificar se o CPF fornecido já não consta na base de dados
	 * @param cpf
	 * @return
	 */
	public boolean existeCpf(String cpf, Pessoa pessoa) {

		List<Pessoa> lista = findByCpf(cpf);
		
		if(lista.size() == 0)
			return false; // retorna FALSE pois esse CPF ainda não cosnta na base de dados
		else {
			/* 
			 * se achou algum CPF, verifique se ele já não pertence ao cadastro 
			 * que está sendo editado. Se é do próprio cadastro, pode salvar
			 * se não, não pode.
			 */
			if(pessoa != null) {
				// se é um cadastro novo e encontrou o CPF na base, então já pertence a outro;
				if(pessoa.getCodigo() == null)
					return true;
				
				for(Pessoa p: lista) {
					/* se o CPF encontrado pertence a uma pessoa diferente da que está sendo editada, 
					 * o sistema não vai permitir também, pois esse CPF já está sendo usado
					 * por outro cadastro
					 */
					if(pessoa.getCodigo() != p.getCodigo()) {
						System.out.println(" **** Esse cpf pertence ao ID " + p.getCodigo());
						return true;
					}
				}
				return false;
			}
			return true;
		}
	}


}
