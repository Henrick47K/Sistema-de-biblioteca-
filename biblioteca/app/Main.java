package biblioteca.app;

import biblioteca.repositorio.EmprestimoRepositorio;
import biblioteca.repositorio.EmprestimoRepositorioArquivo;
import biblioteca.servico.BibliotecaService;

import java.nio.file.Path;

/**
 * Ponto de entrada da aplicação.
 * Responsável exclusivamente por instanciar e conectar as camadas (Injeção de Dependência)
 * e iniciar a camada de apresentação, sem conter regras de negócio.
 */
public class Main {

    public static void main(String[] args) {
        // 1. Escolhe a implementação concreta do repositório
        EmprestimoRepositorio repositorio = new EmprestimoRepositorioArquivo(Path.of("dados", "emprestimos.csv"));

        // 2. Injeta o repositório no serviço
        BibliotecaService bibliotecaService = new BibliotecaService(repositorio);

        // 3. Injeta o serviço na apresentação e inicia o programa
        BibliotecaApp app = new BibliotecaApp(bibliotecaService);
        app.iniciar();
    }
}
