# T2 - Resolução de Problemas com Grafos

**Instituição:** Universidade de Fortaleza (Unifor) - Ciências da Computação  
**Orientador:** Prof. Ricardo  
**Grupo A:** Lucas Peixoto Magalhães e Arthur Alves  

## Informações do Problema
* **Nome:** Shortest Routes I
* **Plataforma:** CSES Problem Set
* **Link:** [https://cses.fi/problemset/task/1671](https://cses.fi/problemset/task/1671)
* **Linguagem Utilizada:** Java 

## Como Executar a Solução

A partir do Java 11, é possível executar o código-fonte diretamente sem a necessidade de compilação prévia gerando arquivos `.class`. Para rodar a solução injetando o arquivo de teste localmente, abra o terminal e execute o comando abaixo:

**Execução (PowerShell):**

    Get-Content dados/input.txt | java src/Main.java

*(Para terminais CMD ou Linux, utilize:* `java src/Main.java < dados/input.txt` *)*

## Explicação da Modelagem

O problema é modelado como um grafo direcionado e ponderado, onde:
* Os vértices representam as cidades (de 1 a n).
* As arestas representam as conexões de voo entre as cidades.
* O peso associado a cada aresta é o custo ou comprimento da rota de voo correspondente.
* O objetivo é calcular o vetor de menores distâncias da cidade de origem (Syrjälä, vértice 1) para todas as demais cidades.
* **Representação Adotada:** Lista de Adjacência (`List<List<Edge>>`), ideal para alocação eficiente de memória em grafos esparsos.

## Algoritmo Utilizado

Foi utilizado o algoritmo de **Dijkstra de origem única**, com suporte de uma Fila de Prioridade Mínima (`PriorityQueue`).
A estratégia é:
* Inicializar o vetor de distâncias com valores infinitos, configurando a origem como 0.
* Utilizar a fila de prioridade para extrair e processar sempre o vértice não processado mais próximo.
* Para cada vértice, realizar o relaxamento das arestas adjacentes: se o caminho pelo vértice atual for menor que a distância registrada, o valor é atualizado e o novo estado é inserido na fila.
* Foram utilizados tipos numéricos de 64 bits (`long`) para armazenar as distâncias acumuladas, garantindo que não ocorra estouro numérico (overflow).

## Desafios Encontrados na Implementação

Durante os testes na plataforma CSES, a submissão inicial do algoritmo de Dijkstra obteve o veredito **Time Limit Exceeded (TLE)** em 3 dos 24 casos de teste (testes 15, 22 e 23). O limite de tempo rigoroso do problema é de 1.00 segundo.

Ao analisarmos a complexidade temporal de O(m log n), constatamos que o algoritmo em si estava perfeitamente otimizado. O gargalo estava no processo de I/O (Entrada e Saída) nativo do Java:
* O uso de estruturas padrão como `Scanner` ou a combinação de `BufferedReader` com `StringTokenizer` gera uma grande quantidade de objetos do tipo `String` em memória durante a leitura das mais de 200.000 conexões.
* Essa alocação massiva aciona o *Garbage Collector* (Coletor de Lixo) do Java frequentemente, paralisando a execução e estourando o tempo limite.

**A Solução:** Para contornar esse problema, substituímos a leitura tradicional por uma classe customizada de **Fast I/O** (`FastIO`). Essa classe lê os bytes puros diretamente do `InputStream` para um buffer de memória interno, realizando a conversão matemática para inteiros e longs sem instanciar nenhuma `String`. Isso derrubou drasticamente o tempo de processamento, permitindo que a solução fosse aprovada (*Accepted*) em todos os cenários.

## Análise de Complexidade

* Extração de vértices e relaxamento com Fila de Prioridade: O(m log n)
* Ocupação de memória com a Lista de Adjacência: O(n + m)
* Complexidade temporal total: O(m log n)

## Evidência de Submissão

A submissão passou em todos os casos de teste de tempo e memória da plataforma CSES. A evidência com o resultado *Accepted* está disponível no repositório em:  
`evidencias/accepted.png`   