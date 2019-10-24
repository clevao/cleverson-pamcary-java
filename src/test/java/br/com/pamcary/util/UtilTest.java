package br.com.pamcary.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class UtilTest {
	@Test
	public void testRemoveNonNumeric() {
			
		assertEquals("12345678901", Util.removeNonNumeric("123.456.789-01"));
		
	}
	
	@Test
	public void testCpfValido() {
		String cpf = "37337413823";
		assertEquals(true, Util.validaCPF(cpf));
	}
	
	@Test
	public void testCpfInvalido() {
		String cpf = "37337413822";
		assertEquals(false, Util.validaCPF(cpf));
	}
}
