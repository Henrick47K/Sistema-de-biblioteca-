package biblioteca;

public class Livro {

    private String autor;
    private String titulo;
    private int codigo;
    private StatusLivro status;


    public enum StatusLivro {
        DISPONIVEL,
        EMPRESTADO,
        RESERVADO
    }

    public Livro(String autor, String titulo, int codigo) {
        this.autor = autor;
        this.titulo = titulo;
        this.codigo = codigo;
        this.status = StatusLivro.DISPONIVEL;
    }

    void cadastroLivro() {
        System.out.println("Livro cadastrado!");
    }

    void emprestar() {
        status = StatusLivro.EMPRESTADO;
        System.out.println("Livro emprestado!");
    }

    void devolver() {
        status = StatusLivro.DISPONIVEL;
        System.out.println("Livro devolvido!");
    }

    void reservar() {
        status = StatusLivro.RESERVADO;
        System.out.println("Livro reservado!");
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public StatusLivro getStatus() {
        return status;
    }
}