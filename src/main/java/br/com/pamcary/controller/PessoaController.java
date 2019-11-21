package br.com.pamcary.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.pamcary.entity.Pessoa;
import br.com.pamcary.entity.Telefone;
import br.com.pamcary.service.PessoaService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class PessoaController {
    
    @Autowired
    private PessoaService pessoaService;

    @RequestMapping(value = "/pessoa", method = RequestMethod.GET)
    public List<Pessoa> Get() {
        return pessoaService.findAll();
    }

    @RequestMapping(value = "/pessoa/{id}", method = RequestMethod.GET)
    public ResponseEntity<Pessoa> GetById(@PathVariable(value = "id") Integer id)
    {
        Optional<Pessoa> pessoa = pessoaService.findById(id);
        if(pessoa.isPresent())
            return new ResponseEntity<Pessoa>(pessoa.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @RequestMapping(value = "/pessoa/cpf/{cpf}", method = RequestMethod.GET)
    public List<Pessoa> GetByCpf(@PathVariable(value = "cpf") String cpf)
    {
        return pessoaService.findByCpf(cpf);
    }
    
	/*
	 * @RequestMapping(value = "/pessoa", method = RequestMethod.POST) public Pessoa
	 * Post(@Valid @RequestBody Pessoa pessoa) { return pessoaService.save(pessoa);
	 * }
	 */
    
    @RequestMapping(value = "/pessoa", method =  RequestMethod.POST)
    public ResponseEntity<Pessoa> Post(@Valid @RequestBody Pessoa pessoa)
    {
    	if(pessoa.getTelefones() == null)
    		pessoa.setTelefones(new ArrayList<Telefone>());
    	
        if(pessoaService.validarCadastro(pessoa, true)){
        	for(Telefone tel : pessoa.getTelefones()) {
        		tel.setPessoa(pessoa);
        	}
            pessoaService.save(pessoa);
            return new ResponseEntity<Pessoa>(pessoa, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
    
    @RequestMapping(value = "/pessoa/lista", method =  RequestMethod.POST)
    public ResponseEntity<Pessoa> PostList(@Valid @RequestBody List<Pessoa> pessoa)
    {
    	for (Pessoa pessoa2 : pessoa) {
    		if(pessoa2.getTelefones() == null)
        		pessoa2.setTelefones(new ArrayList<Telefone>());
        	
    		if(pessoaService.validarCadastro(pessoa2, true)){
                pessoaService.save(pessoa2);
            }
		}
        
    	return new ResponseEntity<>(new Pessoa(), HttpStatus.OK);
    }

    @RequestMapping(value = "/pessoa/{id}", method =  RequestMethod.PUT)
    public ResponseEntity<Pessoa> Put(@PathVariable(value = "id") Integer id, @Valid @RequestBody Pessoa newPessoa)
    {
        Optional<Pessoa> oldPessoa = pessoaService.findById(id);
        
        if(!pessoaService.validarCadastro(newPessoa, false))
        	 return new ResponseEntity<>(HttpStatus.CONFLICT);
        	
        if(oldPessoa.isPresent()){
        	if(newPessoa.getTelefones() == null)
        		newPessoa.setTelefones(new ArrayList<Telefone>());
        	
        	for(Telefone tel : newPessoa.getTelefones()) {
        		tel.setPessoa(newPessoa);
        	}
            pessoaService.save(newPessoa);
            return new ResponseEntity<Pessoa>(newPessoa, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/pessoa/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> Delete(@PathVariable(value = "id") Integer id)
    {
        Optional<Pessoa> pessoa = pessoaService.findById(id);
        if(pessoa.isPresent()){
            pessoaService.delete(pessoa);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}