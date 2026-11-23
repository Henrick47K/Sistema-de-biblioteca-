# BiblioTech — Sistema de Gestão de Biblioteca

**Integrantes:** Henrique Duarte Lima · Rodrigo Mariotti Possel

---

## 1. O que é o sistema

* **Problema:** Bibliotecas sem automação sofrem com empréstimos duplicados, registros incompletos e divergências no estoque físico.
* **Escopo:** Aplicação Java em camadas (`app`, `dominio`, `repositorio`, `servico`) para cadastro de leitores, controle de estoque e estados do acervo (livros e revistas), reservas, empréstimos e devoluções com persistência em CSV (`dados/emprestimos.csv`), protegida por exceções próprias e testes automatizados.

---

## 2. Regras de negócio

* **Perfis e cotas:**
  * **Estudante (`LeitorEstudante`):** máximo de **3 empréstimos ativos** simultâneos e prazo de **14 dias**.
  * **Professor (`LeitorProfessor`):** máximo de **10 empréstimos ativos** simultâneos e prazo de **30 dias**.
* **Liberação de vaga:** Apenas empréstimos `ATIVO` ocupam a cota do leitor; ao finalizar (`FINALIZADO`), a vaga é liberada.
* **Acervo (`ItemAcervo`):**
  * **Livro:** controla estoque e estados (`DISPONIVEL`, `RESERVADO`, `EMPRESTADO`). Não pode ser emprestado nem reservado se já estiver emprestado ou sem estoque.
  * **Revista:** pode ser emprestada e devolvida, mas não possui reserva prévia.
* **Integridade na origem:** Rejeita e-mails fora do padrão `usuario@dominio.com`, datas inexistentes (`dd/MM/aaaa`) e IDs duplicados. Falhas não deixam estado alterado pela metade na memória ou no CSV.

---

## 3. Instruções de execução

### Pré-requisitos
* Java JDK 17+ (`java -version`)
* Apache Maven 3.8+ (`mvn -version`)
* Git (`git --version`)

### Passo a passo em máquina limpa
```bash
# 1. Clonar o repositório
git clone <URL_DO_REPOSITORIO>
cd <NOME_DA_PASTA_DO_PROJETO>

# 2. Rodar os testes automatizados
mvn test

# 3. Compilar e executar o sistema
mvn compile exec:java -Dexec.mainClass="biblioteca.app.Main"
