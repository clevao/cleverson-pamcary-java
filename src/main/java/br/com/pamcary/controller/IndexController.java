package br.com.pamcary.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.pamcary.entity.Pessoa;
import br.com.pamcary.service.PessoaService;

@Controller
public class IndexController {

	@Autowired
    private PessoaService pessoaService;
	
	@RequestMapping("/")
	public String home(Map<String, Object> model) {
		model.put("message", "HowToDoInJava Reader !!");
		return "index";
	}
	
	@RequestMapping("/next")
	public String next(Map<String, Object> model) {
		model.put("message", "You are in new page !!");
		return "next";
	}
	
	@RequestMapping(value = "/site/pessoa/{id}", method = RequestMethod.GET)
    public String GetById(Model model, @PathVariable(value = "id") Integer id)
    {
        Optional<Pessoa> pessoa = pessoaService.findById(id);
        if(pessoa.isPresent()) {
        	model.addAttribute("pessoa", pessoa.get());
        }else {
        	model.addAttribute("pessoa", new Pessoa());
        }
        
        return "pessoa/view";
        
        
        
    }

}