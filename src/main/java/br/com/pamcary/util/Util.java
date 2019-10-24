package br.com.pamcary.util;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

public class Util {
	
	public static String removeNonNumeric(String str) {
		str = str.replaceAll("[^\\d]", "");
		return str;
	}
	
	public static String getHora(){
        String hora;
        int segundos;
        int minutos;
        int horas;
        Calendar data = Calendar.getInstance();
        horas = data.get(Calendar.HOUR_OF_DAY);
        minutos = data.get(Calendar.MINUTE);
        segundos = data.get(Calendar.SECOND);
        hora = horas + ":" + minutos + ":" + segundos;
         
        return hora;
    }
	
	public static String getUniqId(){
		UUID uniqueKey = UUID.randomUUID();
	    //System.out.println (uniqueKey);
	    return uniqueKey.toString();
	}
	
	public static String getFileExtension(File file) {
        String extension = "";
 
        try {
            if (file != null && file.exists()) {
                String name = file.getName();
                extension = name.substring(name.lastIndexOf("."));
            }
        } catch (Exception e) {
            extension = "";
        }
 
        return extension;
 
    }
	
	public static String gerarSha1(String value ){
		
		String hash = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
	        digest.reset();
	        digest.update(value.getBytes("utf8"));
	        hash = String.format("%040x", new BigInteger(1, digest.digest()));
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return hash;
	}

	public static String gerarMD5(String s ){
		String hash = "";
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.update(s.getBytes(),0,s.length());
			hash = new BigInteger(1,m.digest()).toString(16);
		} catch (Exception e){
			e.printStackTrace();
		}
		return hash;
	}
	
	public static Double num2double(String arg0) {
		//System.out.println("*************\nRecebido: " + arg0);
		if(arg0.isEmpty())
			arg0 = "0";

		if(arg0.indexOf(",") >= 0){
			arg0 = arg0.replace(".", "");
			arg0 = arg0.replace(",", ".");
		}
		//System.out.println(arg0 + ": " + arg0.indexOf(",") + " = " + value + "( "+arg0.replaceAll(",",".")+" )");
		return new Double(arg0);
	}

	public static String data2bd(String dt) {
		if(dt.contains("/")){
			String[] dts = dt.split("/");
			dt = dts[2] + "-" + dts[1] + "-" + dts[0];
		}
		return dt;
	}
	


	public static String data2br(String dt) {
		if(dt.contains("-")){
			String[] dts = dt.split("-");
			dt = dts[2] + "/" + dts[1] + "/" + dts[0];
		}
		return dt;
	}
	
	public static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }
	public static boolean isEmpty(Object obj) {
	    if (obj == null) {
	        return true;
	    }
	    return false;
	}

	public static boolean isEmpty(Collection<?> value) {
	    if (value == null || value.isEmpty()) {
	        return true;
	    }
	    return false;
	}

	public static boolean isEmpty(String value) {
	    if (value == null || value.isEmpty()) {
	        return true;
	    }
	    return false;
	}

	public static Timestamp getCurrentTimestamp() {
		Date date = new Date();
		Timestamp ts = new Timestamp(date.getTime());
		return ts;
	}
	
	public static long diferencaEntreDatas(Calendar dtInicio, Calendar dtFim){
		long DAY = 24L * 60L * 60L * 1000L;
		long diferenca = (( dtFim.getTimeInMillis() - dtInicio.getTimeInMillis() ) / DAY );

		
		return diferenca;
	}
	
	public static String getDataHoje(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");	
		Calendar calendar = new GregorianCalendar();
		return sdf.format(calendar.getTime());
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
	
	public static List<String> calcularDatas(Integer qtde, Integer dias, String data){
		List<String> datas = new ArrayList<String>();
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		Date dtIni;
		String dt = "";
		
		try {
			dtIni = new SimpleDateFormat("dd/MM/yyyy").parse(data);
			
			
			if(dias != 30){
				Calendar cal = new GregorianCalendar();
				cal.setTime(dtIni);
				for(int i = 1; i< qtde; i++){
					cal.add(Calendar.DAY_OF_MONTH, dias);
					datas.add(formato.format(cal.getTime()));
				}
			}else{
				String[] adt = data.split("/");
				int di = new Integer(adt[0]);
				int mes = new Integer(adt[1]);
				int ano = new Integer(adt[2]);
				for(int i = 1; i< qtde; i++){
					mes++;
					if(mes == 13){
						mes = 1; ano++;
					}
					
					if(di == 31 || (di > 28 && mes == 2)){
						dt = "01/" + mes + "/" + ano;
						dtIni = new SimpleDateFormat("dd/MM/yyyy").parse(dt);
						Calendar cal = new GregorianCalendar();
						cal.setTime(dtIni);
						int ud = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
						dt = ud + "/" + mes + "/" + ano;
					}else{
						dt = di + "/" + mes + "/" + ano;
					}
					dtIni = new SimpleDateFormat("dd/MM/yyyy").parse(dt);
					datas.add(formato.format(dtIni));
				}
			}
			
			return datas;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return datas;
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

	public static boolean isValidCPF(String cpf) {
		if ((cpf==null) || (cpf.length()!=11)) return false;

		Integer digito1 = calcularDigito(cpf.substring(0,9));
		Integer digito2 = calcularDigito(cpf.substring(0,9) + digito1);
		return cpf.equals(cpf.substring(0,9) + digito1.toString() + digito2.toString());
	}

	public static String geraCPF() {  
	    String iniciais = "";  
	    Integer numero;  
	    for (int i = 0; i < 9; i++) {  
	        numero = new Integer((int) (Math.random() * 10));  
	        iniciais += numero.toString();  
	    }  
	    return iniciais + calcDigVerif(iniciais);  
	}  

	public static boolean validaCPF(String cpf) {  
	    if (cpf.length() != 11)  
	        return false;  

	    String numDig = cpf.substring(0, 9);  
	    return calcDigVerif(numDig).equals(cpf.substring(9, 11));  
	} 
	
}
