package br.com.democsv.democsv.entites;

import java.math.BigDecimal;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.opencsv.bean.CsvIgnore;

import br.com.democsv.democsv.types.ConversorContaCSV;
import br.com.democsv.democsv.types.ConversorMoedaCSV;

public class Conta {

	@CsvBindByName
	private String agencia;
	@CsvCustomBindByName(converter = ConversorContaCSV.class)
	private String conta;
	@CsvCustomBindByName(converter = ConversorMoedaCSV.class)
	private BigDecimal saldo;
	@CsvBindByName
	private String status;
	@CsvIgnore
	private Boolean contaAtualizada;

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getContaAtualizada() {
		return contaAtualizada;
	}

	public void setContaAtualizada(Boolean contaAtualizada) {
		this.contaAtualizada = contaAtualizada;
	}
}
