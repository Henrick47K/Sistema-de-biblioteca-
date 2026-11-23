package biblioteca.repositorio;

import biblioteca.dominio.RegistroEmprestimo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Implementação de persistência em arquivo CSV.
 * Converte falhas de E/S (IOException) e linhas malformadas em RepositorioException,
 * sempre preservando a exceção original como causa.
 */
public class EmprestimoRepositorioArquivo implements EmprestimoRepositorio {

    private static final String SEPARADOR = ";";
    private final Path arquivo;

    public EmprestimoRepositorioArquivo(Path arquivo) {
        if (arquivo == null) {
            throw new RepositorioException("O caminho do arquivo de repositório não pode ser nulo.");
        }
        this.arquivo = arquivo;
    }

    @Override
    public void salvar(RegistroEmprestimo registro) {
        if (registro == null) {
            throw new RepositorioException("Não é possível salvar um registro nulo no arquivo.");
        }

        // Carrega os registros atuais e atualiza pelo ID para evitar duplicidade
        Map<Integer, RegistroEmprestimo> mapaRegistros = new LinkedHashMap<>();
        for (RegistroEmprestimo existente : listarTodos()) {
            mapaRegistros.put(existente.id(), existente);
        }
        mapaRegistros.put(registro.id(), registro);

        try {
            if (arquivo.getParent() != null) {
                Files.createDirectories(arquivo.getParent());
            }
            // try-with-resources garante o fechamento automático do arquivo mesmo se ocorrer erro
            try (BufferedWriter writer = Files.newBufferedWriter(arquivo, StandardCharsets.UTF_8)) {
                for (RegistroEmprestimo item : mapaRegistros.values()) {
                    writer.write(formatarLinha(item));
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            // Converte IOException em exceção técnica própria preservando a causa original
            throw new RepositorioException(
                    "Erro técnico ao gravar dados no arquivo '" + arquivo + "'. Verifique permissões e espaço em disco.",
                    e);
        }
    }

    @Override
    public List<RegistroEmprestimo> listarTodos() {
        if (!Files.exists(arquivo)) {
            return new ArrayList<>();
        }

        List<RegistroEmprestimo> registros = new ArrayList<>();
        // try-with-resources fecha o BufferedReader automaticamente ao fim da leitura
        try (BufferedReader reader = Files.newBufferedReader(arquivo, StandardCharsets.UTF_8)) {
            String linha;
            int numeroLinha = 0;
            while ((linha = reader.readLine()) != null) {
                numeroLinha++;
                if (linha.isBlank()) {
                    continue;
                }
                registros.add(parsearLinha(linha, numeroLinha));
            }
        } catch (IOException e) {
            throw new RepositorioException(
                    "Erro técnico ao ler registros do arquivo '" + arquivo + "'. Verifique se o caminho é um arquivo válido.",
                    e);
        }
        return registros;
    }

    @Override
    public Optional<RegistroEmprestimo> buscarPorId(int id) {
        return listarTodos().stream()
                .filter(r -> r.id() == id)
                .findFirst();
    }

    private String formatarLinha(RegistroEmprestimo r) {
        String dataDevStr = (r.dataDev() == null) ? "" : r.dataDev();
        return String.join(SEPARADOR,
                String.valueOf(r.id()),
                r.cpfLeitor(),
                r.tituloItem(),
                r.dataEmp(),
                dataDevStr,
                r.status());
    }

    private RegistroEmprestimo parsearLinha(String linha, int numeroLinha) {
        String[] partes = linha.split(SEPARADOR, -1);
        if (partes.length != 6) {
            throw new RepositorioException(
                    "Linha inválida na linha " + numeroLinha + " do arquivo '" + arquivo
                            + "': esperado 6 colunas, encontrado " + partes.length + ".");
        }
        try {
            int id = Integer.parseInt(partes[0].trim());
            String cpfLeitor = partes[1].trim();
            String tituloItem = partes[2].trim();
            String dataEmp = partes[3].trim();
            String dataDev = partes[4].trim().isEmpty() ? null : partes[4].trim();
            String status = partes[5].trim();

            return new RegistroEmprestimo(id, cpfLeitor, tituloItem, dataEmp, dataDev, status);
        } catch (IllegalArgumentException e) {
            // Captura erro de conversão numérica ou campo inválido no CSV e preserva a causa
            throw new RepositorioException(
                    "Dados corrompidos na linha " + numeroLinha + " do arquivo '" + arquivo + "': " + e.getMessage(),
                    e);
        }
    }
}