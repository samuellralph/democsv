package br.com.democsv.democsv.componentes;

import java.io.File;
import java.io.FileReader;
import java.util.List;

import org.springframework.stereotype.Component;

import com.opencsv.bean.CsvToBeanBuilder;

import br.com.democsv.democsv.entites.Conta;

@Component
public class ConversorArquivoCSV {
	
	public static final char CSV_SEPARATOR = ';';

	public List<Conta> lerArquivoCSV(String caminhoArquivo) throws Exception {
		File f = new File(caminhoArquivo);
		List<Conta> listaConta = new CsvToBeanBuilder<Conta>(new FileReader(f.getAbsolutePath()))
				.withSeparator(CSV_SEPARATOR)
				.withIgnoreQuotations(Boolean.TRUE)
				.withType(Conta.class).build().parse();
		return listaConta;
	}
}
