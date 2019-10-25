package br.com.pamcary.pessoa;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.pamcary.AvaliacaoApplicationTests;
import br.com.pamcary.entity.Pessoa;
import br.com.pamcary.service.PessoaService;
import br.com.pamcary.util.Util;

public class PessoaTest extends AvaliacaoApplicationTests {
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Autowired
	private PessoaService pessoaService;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	
	@Test
	public void testPessoaGetId() throws Exception {
		mockMvc.perform(get("/pessoa/1"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.nome").value("João da Silva"));

	}
	
	@Test
	public void testPessoaGetCpf() throws Exception {
		// o CPF testado é do código 1. 
		// Se der erro, verifique se o código 1 existe e tem esse CPF
		mockMvc.perform(get("/pessoa/cpf/54393014022"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$[*].codigo").isNotEmpty());

	}
	
	@Test
	public void testPessoaPost() throws Exception {
		String unq = Util.getUniqId();
		
		Pessoa pessoa = new Pessoa();
		pessoa.setNome(unq);
		pessoa.setCpf(Util.geraCPF());
		pessoa.setDataNascimento(Util.data2Timestamp("01/01/2000"));
				
		mockMvc.perform(
					post("/pessoa")
					.content(asJsonString(pessoa))
					.contentType(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.codigo").exists());

	}
	

	
	@Test
	public void testPessoaPostInvalido() throws Exception {
		String unq = Util.getUniqId();
		
		Pessoa pessoa = new Pessoa();
		pessoa.setNome(unq);
		pessoa.setCpf("13546879854"); // cpf inválido
		pessoa.setDataNascimento(Util.data2Timestamp("01/01/2000"));
				
		mockMvc.perform(
					post("/pessoa")
					.content(asJsonString(pessoa))
					.contentType(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isConflict());

	}
	
	@Test
	public void testPessoaPut() throws Exception {
		String unqNome = Util.getUniqId();
		
		// usa o código 2 para teste. Não pode usar o 1 porque ele é usado nos testes anteriores. Se alterar os dados do código 1, vai dar erro nos outros testes que usam o 1
		Integer idTeste = 2;
		
		Pessoa pessoa = new Pessoa();
		pessoa.setCodigo(idTeste);
		pessoa.setNome(unqNome);
		pessoa.setCpf(Util.geraCPF());
		pessoa.setDataNascimento(Util.data2Timestamp("01/01/2000"));
		
		// ATUALIZAÇÃO DO CADASTRO
		mockMvc.perform(
					put("/pessoa/" + idTeste)
					.content(asJsonString(pessoa))
					.contentType(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.codigo").exists());
		
		// LÊ O CADASTRO PARA VERIFICAR SE SALVOU
		mockMvc.perform(get("/pessoa/" + idTeste))
			.andExpect(status().isOk())
			.andExpect(content().contentType("application/json;charset=UTF-8"))
			.andExpect(jsonPath("$.nome").value(unqNome));

	}
	
	@Test
	public void testPessoaDelete() throws Exception{
		// certifique se que o código 3 exista na base de dados
		mockMvc.perform( delete("/pessoa/3", 1) )
        .andExpect(status().isOk());
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	
	@Test
	public void testCpfCadastrado() throws Exception {
		assertEquals(true, pessoaService.existeCpf("19792150064", new Pessoa()));
	}
	
}
