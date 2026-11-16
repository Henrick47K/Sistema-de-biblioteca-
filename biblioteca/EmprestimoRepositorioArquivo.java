package biblioteca.repositorio;

import biblioteca.dominio.RegistroEmprestimo;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmprestimoRepositorioArquivo implements EmprestimoRepositorio {

    private final Path caminhoArquivo;

    public EmprestimoRepositorioArquivo(Path caminhoArquivo) {
        if (caminhoArquivo == null) {
            throw new IllegalArgumentException("O caminho do arquivo não pode ser nulo.");
        }
        this.caminhoArquivo = caminhoArquivo;
    }

    @Override
    public void salvar(RegistroEmprestimo registro) {
        if (registro == null) {
            throw new IllegalArgumentException("Registro de empréstimo não pode ser nulo.");
        }

        List<RegistroEmprestimo> registros = new ArrayList<>(listarTodos());
        registros.removeIf(r -> r.id() == registro.id());
        registros.add(registro);

        List<String> linhas = new ArrayList<>();
        for (RegistroEmprestimo r : registros) {
            linhas.add(formatarLinha(r));
        }

        try {
            if (caminhoArquivo.getParent() != null) {
                Files.createDirectories(caminhoArquivo.getParent());
            }
            Files.write(caminhoArquivo, linhas, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException(
                    "Falha ao gravar registros de empréstimo no arquivo: " + caminhoArquivo, e
            );
        }
    }

    @Override
    public List<RegistroEmprestimo> listarTodos() {
        if (Files.notExists(caminhoArquivo)) {
            return List.of();
        }

        try {
            List<String> linhas = Files.readAllLines(caminhoArquivo, StandardCharsets.UTF_8);
            List<RegistroEmprestimo> registros = new ArrayList<>();

            for (String linha : linhas) {
                if (!linha.isBlank()) {
                    registros.add(parsearLinha(linha));
                }
            }
            return List.copyOf(registros);
        } catch (IOException e) {
            throw new UncheckedIOException(
                    "Falha ao ler registros de empréstimo do arquivo: " + caminhoArquivo, e
            );
        }
    }

    @Override
    public Optional<RegistroEmprestimo> buscarPorId(int id) {
        for (RegistroEmprestimo r : listarTodos()) {
            if (r.id() == id) {
                return Optional.of(r);
            }
        }
        return Optional.empty();
    }

    private String formatarLinha(RegistroEmprestimo r) {
        String dataDev = (r.dataDev() == null) ? "" : r.dataDev();
        return String.join(";",
                String.valueOf(r.id()),
                limparTexto(r.cpfLeitor()),
                limparTexto(r.tituloItem()),
                limparTexto(r.dataEmp()),
                limparTexto(dataDev),
                limparTexto(r.status())
        );
    }

    private RegistroEmprestimo parsearLinha(String linha) {
        String[] partes = linha.split(";", -1);
        if (partes.length < 6) {
            throw new IllegalStateException("Linha inválida no arquivo de empréstimos: " + linha);
        }
        int id = Integer.parseInt(partes[0]);
        String cpfLeitor = partes[1];
        String tituloItem = partes[2];
        String dataEmp = partes[3];
        String dataDev = partes[4].isEmpty() ? null : partes[4];
        String status = partes[5];

        return new RegistroEmprestimo(id, cpfLeitor, tituloItem, dataEmp, dataDev, status);
    }

    private String limparTexto(String valor) {
        return valor.replace(";", ",").replace("\n", " ").replace("\r", " ");
    }
}