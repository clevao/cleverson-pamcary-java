package br.com.pamcary.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Util {
	
	public static String removeNonNumeric(String str) {
		str = str.replaceAll("[^\\d]", "");
		return str;
	}
		
	public static String getUniqId(){
		UUID uniqueKey = UUID.randomUUID();
	    //System.out.println (uniqueKey);
	    return uniqueKey.toString();
	}
	

	public static String data2br(String dt) {
		if(dt.contains("-")){
			String[] dts = dt.split("-");
			dt = dts[2] + "/" + dts[1] + "/" + dts[0];
		}
		return dt;
	}
	
	public static Date data2Date(String data){
		Date dtIni = null;
		try {
			dtIni = new SimpleDateFormat("dd/MM/yyyy").parse(data);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dtIni;
	}
	
	public static Timestamp data2Timestamp(String data){
		Date dt = Util.data2Date(data);
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//System.out.println(formato.format(dt));
        Timestamp ts = Timestamp.valueOf(formato.format(dt));
        return ts;
	}
		
	private static String calcDigVerif(String cpf) {
		Integer digito1 = calcularDigito(cpf.substring(0,9));
		Integer digito2 = calcularDigito(cpf.substring(0,9) + digito1);
		return digito1 + "" + digito2;
	} 
	
	
	private static int calcularDigito(String str) {
		int[] peso = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
		int soma = 0;
		for (int indice=str.length()-1, digito; indice >= 0; indice-- ) {
			digito = Integer.parseInt(str.substring(indice,indice+1));
			soma += digito*peso[peso.length-str.length()+indice];
		}
		soma = 11 - soma % 11;
		return soma > 9 ? 0 : soma;
	}

	/**
	 * Método responsável por verificar se o cpf é válido
	 * @param cpf
	 * @return
	 */
	public static boolean isValidCPF(String cpf) {
		if ((cpf==null) || (cpf.length()!=11)) return false;

		Integer digito1 = calcularDigito(cpf.substring(0,9));
		Integer digito2 = calcularDigito(cpf.substring(0,9) + digito1);
		return cpf.equals(cpf.substring(0,9) + digito1.toString() + digito2.toString());
	}

	/**
	 * Esse método gera um número de cpf válido
	 * @return
	 */
	public static String geraCPF() {  
	    String iniciais = "";  
	    Integer numero;  
	    for (int i = 0; i < 9; i++) {  
	        numero = new Integer((int) (Math.random() * 10));  
	        iniciais += numero.toString();  
	    }  
	    return iniciais + calcDigVerif(iniciais);  
	}  
	
}
