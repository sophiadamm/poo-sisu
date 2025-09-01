# Trabalho de POO: Análise de Dados do SISU/UFS

## 1. Membros da Equipe: 
Raissa Cavalcante de Albuquerque Bitencurte

Sophia Damm Zogaib Mardones 

## 2. Link para o Repositório: 
https://github.com/sophiadamm/poo-sisu/

## 3. Descrição do Tema do Trabalho
Este projeto consiste na implementação de uma interface gráfica para exploração e análise dos dados de candidatos aprovados na chamada regular do Sistema de Seleção Unificada (SISU) para a Universidade Federal de Sergipe (UFS), abrangendo o período de 2019 a 2025.
O objetivo principal foi desenvolver uma aplicação que permita visualizar as informações de múltiplas formas, como gráficos (barras, linhas e pizza), tabelas e listas, servindo como prática para os conceitos do paradigma de Programação Orientada a Objetos (POO).

## 4. Discussão do Desenvolvimento

Para o desenvolvimento, utilizamos uma base de dados contendo as informações de cada candidato aprovado:
- Número de Inscrição do ENEM
- Nome do Candidato
- Curso
- Campus
- Tipo de Concorrência (Demanda)
- Média
- Colocação 
- Estado de Origem
- Ano de Aprovação

A aplicação foi implementada em duas linguagens (Java e Python), utilizando os frameworks JavaFX e PyQt5, respectivamente, para a construção da interface gráfica.

A interface da aplicação oferece os seguintes recursos para uma análise dinâmica e intuitiva dos dados:
- Filtros Interativos(por ano, curso, campus e demanda): permitem a seleção do subconjunto de dados a ser analisado. Isso permite focar a análise em recortes específicos.
- A aplicação conta com 10 funcionalidades que processam os dados filtrados e os apresentam em formatos diversos: Tabelas, Listas, Gráficos (Pizza, Barras, Linhas)
- Um mecanismo inteligente gerencia automaticamente quais funcionalidades podem ser ativadas, bloqueando seletivamente aquelas que poderiam gerar análises inconsistentes. Por exemplo, o Gráfico de Linha, usado para avaliar o comportamento da nota de corte ao longo do tempo, só pode ser exibido quando os dados não estão restritos a um único ano. Essa lógica assegura que cada visualização gerada seja sempre coerente e relevante.
- Navegação por Abas: Cada visualização gerada é aberta em uma nova aba, permitindo que o usuário compare diferentes análises side-by-side e navegue facilmente entre os resultados sem perder o contexto de suas consultas anteriores.

As 10 funcionalidades que conseguimos desenvolver foram:
1. Gráfico de Linhas (Ano × Nota de Corte)
Permite acompanhar a evolução da nota de corte de um curso ao longo dos anos, destacando a trajetória em diferentes campus.
2. Gráfico de Barras (Notas Máximas, Mínimas e Médias)
Exibe o comportamento estatístico das notas em um campus específico ao longo do tempo.
3. Histograma de Frequência de Notas
Analisa a distribuição das notas dos aprovados dentro do recorte definido pelos filtros aplicados pelo usuário.
4. Top 10 Cursos com Maior Nota de Corte
Apresenta os cursos com as dez maiores notas de corte registradas no conjunto de dados selecionado.
5. Tabela de Análise Estatística por Estado
Exibe, para cada estado, o número de alunos, a média de notas e a participação percentual no total.
6. Tabela Comparativa de Notas de Corte
Apresenta, para cada curso, a variação das notas de corte entre dois anos selecionados, oferecendo possibilidade de ordenação para análises mais específicas.
7. Simulação do Sisu
Oferece ao usuário a possibilidade de simular sua candidatura, inserindo sua nota e observando cenários de aprovação.
8. Simulação “Em quais cursos eu passaria?”
Com base na nota informada, mostra em quais cursos o usuário teria sido aprovado dentro do conjunto de dados filtrado.
9. Gráfico de Pizza das Demandas
Representa o percentual das diferentes demandas em relação ao total de ingressantes.
10. Consulta por Nome
Permite buscar candidatos pelo nome dentro do subconjunto de dados definido.

## 5. Discussão da Orientação a Objetos na segunda linguagem adotada (Python)

Diferente de linguagens como Java, que são inteiramente baseadas em Programação Orientada a Objetos, o Python é multiparadigma. Isso torna a POO em Python mais flexível, mas também menos rígida em termos de regras, algo que conseguimos perceber claramente no desenvolvimento de nosso projeto.

No Java, há um forte mecanismo de encapsulamento, com modificadores de acesso (private, protected, public) que garantem o information hiding. Já no Python, não existe essa restrição formal: todos os atributos são, em essência, públicos. A prática adotada é usar um underscore (_atributo) para indicar que algo não deve ser acessado diretamente, ou dois underscores (__atributo) para ativar o name mangling, dificultando, mas não impedindo, o acesso externo, diferentemente do que ocorre com os modificadores de acesso do Java.  Por exemplo, em Java, se quisermos acessar um atributo privado, precisamos necessariamente implementar métodos getters. Em Python, não há essa obrigatoriedade, mas escolhemos implementar esses métodos para manter fidelidade ao paradigma. Na classe Candidatos, por exemplo, como é importante prevenir que os dados sejam modificados, todos os atributos de instância foram nomeados com dois underscore.

Outra diferença marcante é que, em Java, tudo deve estar dentro de uma classe , não existe código solto fora desse escopo. Já no Python, as classes são opcionais: é possível escrever programas inteiros apenas com funções e estruturas simples. Ainda assim, quando optamos por adotar a POO, conseguimos estruturar um projeto orientado a objetos sem a mesma rigidez do Java.

Além disso, Python oferece alguns recursos que não existem da mesma forma no Java, como o uso de decoradores. O @property, por exemplo, permite criar getters e setters de maneira mais enxuta e elegante, enquanto @classmethod (cujo primeiro parâmetro é cls) possibilita definir métodos que recebem a própria classe como argumento, úteis para construtores alternativos. Já @staticmethod permite criar métodos associados à classe, mas que não dependem de instância nem de classe como parâmetro.
Outro ponto central é o uso do self, que deve ser explicitado em todos os métodos de instância. Diferente do Java, em que o this é implícito, em Python o self é passado de forma explícita, reforçando a clareza de que aquele método opera sobre uma instância. Já o método especial __init__ funciona como construtor, inicializando atributos quando um objeto é criado, desempenhando papel análogo ao dos construtores em Java.

No que diz respeito à herança, Python também oferece suporte completo. No nosso projeto com PyQt, por exemplo, utilizamos herança ao estender classes como QMainWindow ou QWidget, adicionando novos comportamentos e personalizando a interface gráfica. 

Por fim, o uso de classes em Python é essencial para dividir responsabilidades dentro de um projeto. Ao criar classes específicas para diferentes partes da aplicação:  
- classes para a interface visual (convertidas a partir dos arquivos .ui)
- classes para a lógica funcional (que utilizam instâncias das classes da interface para adicionar comportamento)
- classes para armazenamento de dados 
- classes para métodos utilitários
- 
Conseguimos manter o código mais organizado, modular e reutilizável. Essa abordagem se torna ainda mais importante em projetos como o nosso, que foi desenvolvido de forma coletiva.

Assim, percebemos que a POO em Python é mais voltada à disciplina do programador, de ser fiel aos conceitos e processos, do que a restrições impostas pela própria linguagem. Por esse motivo, Python oferece uma alternativa mais flexível, simples e menos verbosa que Java, mas com menos garantias formais de encapsulamento.
