package br.com.democsv.democsv;

import static java.lang.Boolean.FALSE;
import static javax.swing.JFileChooser.APPROVE_OPTION;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import br.com.democsv.democsv.componentes.ConversorArquivoCSV;
import br.com.democsv.democsv.componentes.EscreverArquivoCSV;
import br.com.democsv.democsv.entites.Conta;
import br.com.democsv.democsv.services.ReceitaService;

@ComponentScan(basePackages = "br.com.democsv.democsv")
@SpringBootApplication
public class DemocsvApplication implements CommandLineRunner {
	@Autowired
	private ConversorArquivoCSV conversorCSV;
	
	@Autowired
	private EscreverArquivoCSV escreverArquivoCSV;
	
	@Autowired
	private ReceitaService receitaService;
	
	private FileNameExtensionFilter filterCsv = new FileNameExtensionFilter("csv", "csv");
	private FileNameExtensionFilter filterTxt = new FileNameExtensionFilter("txt", "txt");
	
	public static final String HEADER_ARQUIVO_CSV = "agencia;conta;saldo;status;conta atualizada";
	public static final char QUEBRA_LINHA = '\n';
	
	public static final String MSG_SELECIONE_ARQUIVO = "### Selecione um arquivo no painel!";
	public static final String MSG_ARQUIVO_SINC = "### Arquivo selecionado para sincronizar: ";
	public static final String MSG_LOCAL_ARQUIVO_SINCRONIZADO = "### Selecione um local no painel para salvar o resultado!";
	public static final String MSG_ARQUIVO_EXPORTADO = "Arquivo sicronizado exportado: ";
	public static final String MSG_SINCRONIZANDO = "#### Sincronizando, aguarde ... ###";
	public static final String MSG_FALHA_SINCRONIZACAO = "#### Falha na sicronização ... ###";
	
	public static void main(String[] args) {
        SpringApplication app = new SpringApplication(DemocsvApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.setLogStartupInfo(false);
        app.setHeadless(false);
        app.run(args);
	}
	
    @Override
    public void run(String... args) throws Exception {
    	
		JFileChooser jfileOpen = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		jfileOpen.addChoosableFileFilter(filterCsv);
		jfileOpen.addChoosableFileFilter(filterTxt);
		jfileOpen.setAcceptAllFileFilterUsed(false);
		int returnOd = jfileOpen.showOpenDialog(null);
		
		System.out.println(MSG_SELECIONE_ARQUIVO);
		
		List<Conta> listaConta = obterDadosCSV(jfileOpen, returnOd);
		List<Conta> listaContaSicronizada = sincronizarDadosReceita(listaConta);
		
		StringBuilder sb = escreverRetornoCSV(listaContaSicronizada);
		
		JFileChooser jfileSave = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		jfileSave.addChoosableFileFilter(filterCsv);
		jfileSave.addChoosableFileFilter(filterTxt);
		jfileSave.setAcceptAllFileFilterUsed(false);
		
		System.out.println(MSG_LOCAL_ARQUIVO_SINCRONIZADO);
		int returnSd = jfileSave.showSaveDialog(null);
		
		salvarArquivoCSV(sb, jfileSave, returnSd);
    }

	private void salvarArquivoCSV(StringBuilder sb, JFileChooser jfileSave, int returnSd)
			throws InterruptedException, IOException {
		if(returnSd == JFileChooser.APPROVE_OPTION) {
			File localNomeSelecionado = jfileSave.getSelectedFile();
		    Path path = Paths.get(localNomeSelecionado.getAbsolutePath()+"."+jfileSave.getFileFilter().getDescription());
		    byte[] strToBytes = sb.toString().getBytes();
		    receitaService.atualizarConta(null, null, returnSd, null);
		    Files.write(path, strToBytes);
			System.out.println(MSG_ARQUIVO_EXPORTADO+ localNomeSelecionado.getAbsolutePath()+"."+jfileSave.getFileFilter().getDescription());
		}
	}

	private StringBuilder escreverRetornoCSV(List<Conta> listaContaSicronizada) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(HEADER_ARQUIVO_CSV);
		for (Conta contaSinc : listaContaSicronizada) {
			sb.append(QUEBRA_LINHA);
			sb.append(escreverArquivoCSV.escrever(contaSinc));
		}
		return sb;
	}

	private List<Conta> sincronizarDadosReceita(List<Conta> listaConta) {
		System.out.println(MSG_SINCRONIZANDO);
		List<Conta> listaContaSicronizada = listaConta.stream().map(conta -> {
			try {
				Double saldo = conta.getSaldo() != null ? 0 : conta.getSaldo().doubleValue();
				Boolean statusAtualizacao;
				statusAtualizacao = receitaService.atualizarConta(conta.getAgencia(), conta.getConta(), saldo, conta.getStatus());
				conta.setContaAtualizada(statusAtualizacao);
			} catch (RuntimeException | InterruptedException e) {
				conta.setContaAtualizada(FALSE);
				System.out.println(MSG_FALHA_SINCRONIZACAO);
			}
			return conta;
		}).collect(Collectors.toList());
		return listaContaSicronizada;
	}

	private List<Conta> obterDadosCSV(JFileChooser jfileOpen, int returnOd) throws Exception {
		List<Conta> listaConta = new ArrayList<Conta>();
		if (returnOd == APPROVE_OPTION) {
			File arquivoSelecionado = jfileOpen.getSelectedFile();
			listaConta = conversorCSV.lerArquivoCSV(arquivoSelecionado.getAbsolutePath());
			System.out.println(MSG_ARQUIVO_SINC+arquivoSelecionado.getAbsolutePath());
		}
		return listaConta;
	}

}
