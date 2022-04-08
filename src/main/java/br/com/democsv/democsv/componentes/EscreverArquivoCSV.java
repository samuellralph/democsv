package br.com.democsv.democsv.componentes;

import org.springframework.stereotype.Component;

import br.com.democsv.democsv.entites.Conta;

@Component
public class EscreverArquivoCSV {
	
	public static final char CSV_SEPARATOR = ';';
	public static final char QUEBRA_LINHA = '\n';
	public static final String VALOR_SIM = "sim";
	public static final String VALOR_NAO = "não";
	
	public StringBuilder escrever(Conta conta) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(conta.getAgencia());
		sb.append(CSV_SEPARATOR);
		if(conta.getConta() == null || conta.getConta().length() != 6) {
			sb.append(conta.getConta());	
		}else {
			String numeroConta = conta.getConta();
			String numeroDivisor = numeroConta.substring(5, 6).toString();
			String divisor = "-";
			StringBuilder sbConta = new StringBuilder();
			
			sbConta.append(numeroConta.substring(0,5));
			sbConta.append(divisor);
			sbConta.append(numeroDivisor);
			sb.append(sbConta.toString());
		}
		sb.append(CSV_SEPARATOR);
		sb.append(conta.getSaldo());
		sb.append(CSV_SEPARATOR);
		sb.append(conta.getStatus());
		sb.append(CSV_SEPARATOR);
		sb.append(conta.getContaAtualizada() == Boolean.TRUE ? VALOR_SIM : VALOR_NAO);
		return sb;
	}
	
}
