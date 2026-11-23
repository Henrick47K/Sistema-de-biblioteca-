# BiblioTech — Sistema de Gestão de Biblioteca

**Integrantes:** Henrique Duarte Lima · Rodrigo Mariotti Possel

---

## 1. O que é o sistema

* **Problema:** Bibliotecas sem automação sofrem com empréstimos duplicados, cadastros incompletos e falhas no controle de estoque físico.
* **Solução:** Sistema Java em camadas (`app`, `dominio`, `repositorio`, `servico`) para gerenciar leitores, acervo (livros e revistas), reservas, empréstimos e devoluções com persistência em arquivo CSV (`dados/emprestimos.csv`).

---

## 2. Regras de negócio

* **Estudante (`LeitorEstudante`):** limite de **3 empréstimos ativos** simultâneos e prazo de **14 dias**.
* **Professor (`LeitorProfessor`):** limite de **10 empréstimos ativos** simultâneos e prazo de **30 dias**.
* **Cota ativa:** apenas empréstimos com status `ATIVO` ocupam o limite do leitor; ao finalizar (`FINALIZADO`), a vaga é liberada.
* **Livro (`Livro`):** controla estoque e estados (`DISPONIVEL`, `RESERVADO`, `EMPRESTADO`), bloqueando reserva ou empréstimo se já estiver emprestado ou sem estoque.
* **Revista (`Revista`):** permite empréstimo e devolução, sem reserva prévia.
* **Integridade na origem:** rejeita e-mails fora do padrão `usuario@dominio.com`, datas inválidas (`dd/MM/aaaa`) e IDs duplicados, sem alterar o estado da memória ou do arquivo em caso de erro.

---

## 3. Instruções de execução

### Pré-requisitos
* Java JDK 17+ (`java -version`)
* Apache Maven 3.8+ (`mvn -version`)
* Git (`git --version`)

### Passo a passo em máquina limpa
```bash
git clone <URL_DO_REPOSITORIO>
cd <NOME_DA_PASTA_DO_PROJETO>
mvn test
mvn compile exec:java -Dexec.mainClass="biblioteca.app.Main"
