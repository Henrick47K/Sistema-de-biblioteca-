# BiblioTech — Sistema de Gestão de Biblioteca

### TAG V--14 AS

**Integrantes:** Henrique Duarte Lima · Rodrigo Mariotti Possel

---

## 1. O que é o sistema

- **Problema:** Bibliotecas sem automação sofrem com empréstimos duplicados, cadastros incompletos e falhas no controle de estoque.
- **Solução:** Sistema Java em camadas (`app`, `dominio`, `repositorio`, `servico`) para gerenciar leitores, acervo (livros e revistas), reservas, empréstimos e devoluções com persistência em CSV (`dados/emprestimos.csv`).

---

## 2. Regras de negócio

- **Estudante (`LeitorEstudante`):** limite de **3 empréstimos ativos** e prazo de **14 dias**.
- **Professor (`LeitorProfessor`):** limite de **10 empréstimos ativos** e prazo de **30 dias**.
- **Cota ativa:** apenas empréstimos `ATIVO` ocupam o limite; ao finalizar (`FINALIZADO`), a vaga é liberada.
- **Livro (`Livro`):** controla estoque e estados (`DISPONIVEL`, `RESERVADO`, `EMPRESTADO`); bloqueia reserva ou empréstimo se já estiver emprestado ou sem estoque.
- **Revista (`Revista`):** permite empréstimo e devolução, sem reserva prévia.
- **Integridade:** barra e-mails fora do padrão `usuario@dominio.com`, datas inválidas (`dd/MM/aaaa`) e IDs duplicados, sem alterar o estado em caso de erro.

---

## 3. Instruções de execução

### Pré-requisitos
- Java JDK 17+ (`java -version`)
- Apache Maven 3.8+ (`mvn -version`)
- Git (`git --version`)

### Passo a passo em máquina limpa
```bash
git clone <URL_DO_REPOSITORIO>
cd <NOME_DA_PASTA_DO_PROJETO>
mvn test
mvn compile exec:java -Dexec.mainClass="biblioteca.app.Main"
```

```text
src/
├── main/java/biblioteca/
│   ├── app/            # Entrada (Main) e interface de console (BibliotecaApp)
│   ├── dominio/        # Entidades, validações e exceções de negócio
│   ├── repositorio/    # Contrato de persistência, CSV, Memória e RepositorioException
│   └── servico/        # Orquestração de casos de uso e rollback (BibliotecaService)
└── test/java/biblioteca/
    ├── BibliotecaTest.java              # Testes de domínio, herança e polimorfismo
    └── BibliotecaPersistenciaTest.java  # Testes de regras, exceções, limites e CSV
```

## Decisões de projeto

### 1. Persistência desacoplada por interface (`EmprestimoRepositorio`)
- **Contexto:** O sistema precisava salvar o histórico de empréstimos em CSV sem acoplar as regras de negócio ao disco e sem tornar os testes dependentes de arquivos fixos.
- **Decisão:** Criar a interface `EmprestimoRepositorio` com duas implementações intercambiáveis — `EmprestimoRepositorioArquivo` e `EmprestimoRepositorioMemoria` — injetadas via construtor em `BibliotecaService`.
- **Consequência:** Os testes rodam isolados em memória ou pastas temporárias (`@TempDir`), e uma futura troca do CSV por banco de dados não exige alterar o domínio.

### 2. Hierarquia própria de exceções de domínio e técnica
- **Contexto:** Exceções genéricas do Java não distinguiam erros previsíveis de regra de negócio de falhas técnicas de leitura e escrita em arquivo.
- **Decisão:** Criar `BibliotecaException` (com `DadosInvalidosException`, `ItemIndisponivelException` e `LimiteEmprestimosExcedidoException`) para o domínio e `RepositorioException` (preservando a causa raiz) para a camada de persistência.
- **Consequência:** A camada `BibliotecaApp` captura cada erro previsível e exibe mensagens claras ao usuário sem encerrar a aplicação abruptamente.

### 3. Validação prévia e rollback atômico no serviço
- **Contexto:** Alterar a cota do leitor e baixar o estoque do livro antes de confirmar todas as regras ou gravar no CSV poderia deixar os dados inconsistentes se ocorresse alguma falha.
- **Decisão:** Em `BibliotecaService.realizarEmprestimo`, validar todas as pré-condições antes de mutar qualquer objeto e envolver a efetivação em um `try/catch` que desfaz as alterações em memória caso o repositório lance exceção.
- **Consequência:** Garante atomicidade nas operações, mantendo memória e arquivo CSV sempre sincronizados mesmo diante de erros.

## Como o código evolui (tags)

- **`BiblioTech V1.0.0` (`v1` – `v6`):** Modelagem inicial do módulo core (`Livro`, `Leitor` e `Emprestimo`), controle de estoque e primeiros testes automatizados (`BibliotecaTest`).
- **`v7` – `v10`:** Especialização de perfis de leitor (`LeitorEstudante` e `LeitorProfessor`) e polimorfismo no acervo (`ItemAcervo` e `Revista`).
- **`v11` – `v12`:** Separação da arquitetura em camadas (`app`, `servico` e `repositorio`) e persistência em arquivo CSV com o record `RegistroEmprestimo`.
- **`v13`:** Validações na origem, hierarquia própria de exceções (`BibliotecaException` e `RepositorioException`), rollback atômico em falhas de gravação e testes isolados com `@TempDir`.
- **`v14` (Versão final):** Suíte de testes ampliada (regras de negócio, casos-limite e `@ParameterizedTest`), refatorações seguras (`ValidadorDominio`, remoção de métodos vazios e decomposição de `BibliotecaApp`) e documentação final.
