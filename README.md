# BiblioTech — Sistema de Gestão de Biblioteca

**Integrantes:** Henrique Duarte Lima · Rodrigo Mariotti Possel

---

## 1. O que é o sistema

* **Problema:** Bibliotecas sem automação sofrem com empréstimos duplicados, cadastros incompletos e falhas no controle de estoque.
* **Solução:** Sistema Java em camadas (`app`, `dominio`, `repositorio`, `servico`) para gerenciar leitores, acervo (livros e revistas), reservas, empréstimos e devoluções com persistência em CSV (`dados/emprestimos.csv`)[cite: 1, 2, 16, 19].

---

## 2. Regras de negócio

* **Estudante (`LeitorEstudante`):** limite de **3 empréstimos ativos** e prazo de **14 dias**[cite: 9].
* **Professor (`LeitorProfessor`):** limite de **10 empréstimos ativos** e prazo de **30 dias**[cite: 10].
* **Cota ativa:** apenas empréstimos `ATIVO` ocupam o limite; ao finalizar (`FINALIZADO`), a vaga é liberada[cite: 5, 8].
* **Livro (`Livro`):** controla estoque e estados (`DISPONIVEL`, `RESERVADO`, `EMPRESTADO`); bloqueia reserva ou empréstimo se já estiver emprestado ou sem estoque.
* **Revista (`Revista`):** permite empréstimo e devolução, sem reserva prévia[cite: 14].
* **Integridade:** barra e-mails fora do padrão `usuario@dominio.com`, datas inválidas (`dd/MM/aaaa`) e IDs duplicados[cite: 5, 8, 19], sem alterar o estado em caso de erro.

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

src/
├── main/java/biblioteca/
│   ├── app/            # Entrada (Main) e interface de console (BibliotecaApp)[cite: 1, 2]
│   ├── dominio/        # Entidades, validações e exceções de negócio[cite: 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14]
│   ├── repositorio/    # Contrato de persistência, CSV, Memória e RepositorioException[cite: 15, 16, 17, 18]
│   └── servico/        # Orquestração de casos de uso e rollback (BibliotecaService)
└── test/java/biblioteca/
    ├── BibliotecaTest.java              # Testes de domínio, herança e polimorfismo
    └── BibliotecaPersistenciaTest.java  # Testes de regras, exceções, limites e CSV

## Decisões de projeto
Persistência desacoplada (EmprestimoRepositorio)Contexto: Salvar histórico em CSV sem acoplar regras de negócio ao disco e sem tornar testes lentos.   Decisão: Criar a interface EmprestimoRepositorio com implementações em arquivo (EmprestimoRepositorioArquivo) e memória (EmprestimoRepositorioMemoria).   Consequência: Testes rodam isolados em memória ou @TempDir[cite: 20] e a troca futura por banco de dados não afeta o domínio.   Hierarquia própria de exceçõesContexto: Exceções genéricas não distinguiam falhas de regra de negócio de erros técnicos de arquivo.   Decisão: Criar BibliotecaException (DadosInvalidosException, ItemIndisponivelException, LimiteEmprestimosExcedidoException) para domínio e RepositorioException (preservando a causa raiz) para persistência.   Consequência: A BibliotecaApp captura cada erro previsível com mensagens claras sem encerrar o programa abruptamente.   Validação prévia e rollback atômicoContexto: Mutar objetos em memória antes de confirmar regras ou gravar no CSV gerava inconsistência em caso de falha.   Decisão: Validar todas as pré-condições antes de alterar estado e reverter alterações em memória via try/catch se o repositório falha .Consequência: Memória e arquivo CSV permanecem sempre sincronizados.

## Como o código evolui:
(tags)BiblioTech V1.0.0 (v1–v6): Módulo core (Livro, Leitor, Emprestimo), controle de estoque e primeiros testes (BibliotecaTest)[cite: 21].v7–v10: Especialização (LeitorEstudante, LeitorProfessor) e polimorfismo (ItemAcervo, Revista).   v11–v12: Separação em camadas (app, servico, repositorio) e persistência CSV com RegistroEmprestimo.   v13: Validações na origem, exceções próprias, rollback e testes isolados com @TempDir.   v14 (Final): Suíte ampliada (regras de negócio, casos-limite e @ParameterizedTest), refatorações seguras (ValidadorDominio, remoção de métodos vazios, decomposição de BibliotecaApp) e documentação final.

