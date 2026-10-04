package biblioteca;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== DEMONSTRAÇÃO DE POLIMORFISMO NO ACERVO DA BIBLIOTECA ===\n");

        List<ItemAcervo> acervo = new ArrayList<>();

        acervo.add(new Livro("Dante Alighieri", "Inferno de Dante", 101, 3));
        acervo.add(new Revista("National Geographic", 204));
        acervo.add(new Livro("Robert C. Martin", "Clean Code", 102, 1));
        acervo.add(new Revista("Superinteressante", 450));

        System.out.println("--- 1. PROCESSANDO RESERVAS (REGRAS ESPECÍFICAS) ---");
        for (ItemAcervo item : acervo) {
            System.out.println("Item: " + item.getTitulo());
            try {
                item.reservar();
            } catch (IllegalStateException e) {
                System.out.println("Aviso de Negócio: " + e.getMessage());
            }
            System.out.println();
        }

        System.out.println("--- 2. PROCESSANDO EMPRÉSTIMOS POLIMORFICAMENTE ---");
        for (ItemAcervo item : acervo) {
            System.out.println("Item: " + item.getTitulo());
            try {
                item.emprestar();
            } catch (IllegalStateException e) {
                System.out.println("Aviso de Negócio: " + e.getMessage());
            }
            System.out.println();
        }

        System.out.println("--- 3. PROCESSANDO DEVOLUÇÕES ---");
        for (ItemAcervo item : acervo) {
            System.out.println("Item: " + item.getTitulo());
            try {
                item.devolver();
            } catch (IllegalStateException e) {
                System.out.println("Aviso de Negócio: " + e.getMessage());
            }
            System.out.println();
        }

        System.out.println("=== EXECUÇÃO FINALIZADA COM SUCESSO ===");
    }
}