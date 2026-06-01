/**
 * T2 - Resolução de Problemas com Grafos
 * Curso: Ciências da Computação - Universidade de Fortaleza (Unifor)
 * Orientador: Prof. Ricardo Carubbi
 * Grupo A: Lucas Peixoto Magalhães e Arthur Alves
 * Problema: CSES - Shortest Routes I
 *
 * Solução:
 * Utilização do algoritmo de Dijkstra com fila de prioridade (PriorityQueue)
 * para encontrar as menores distâncias a partir do vértice 1 para todos os
 * demais vértices do grafo direcionado e ponderado.
 *
 * Complexidade:
 * O((N + M) log N)
 * onde:
 * N = quantidade de vértices
 * M = quantidade de arestas
 */

import java.io.*;
import java.util.*;

public class Main {

    /**
     * Representa uma aresta do grafo.
     * target = vértice de destino
     * weight = peso/custo da aresta
     */
    static class Edge {
        int target;
        long weight;

        public Edge(int target, long weight) {
            this.target = target;
            this.weight = weight;
        }
    }

    /**
     * Representa um estado na fila de prioridade.
     * node = vértice atual
     * dist = distância acumulada até esse vértice
     *
     * Comparable é utilizado para que a PriorityQueue
     * sempre remova primeiro o vértice com menor distância.
     */
    static class State implements Comparable<State> {
        int node;
        long dist;

        public State(int node, long dist) {
            this.node = node;
            this.dist = dist;
        }

        @Override
        public int compareTo(State other) {
            return Long.compare(this.dist, other.dist);
        }
    }

    public static void main(String[] args) {

        // Leitura rápida da entrada
        FastIO io = new FastIO(System.in);

        // Escrita da saída
        PrintWriter out = new PrintWriter(System.out);

        // n = quantidade de cidades (vértices)
        // m = quantidade de rotas (arestas)
        int n = io.nextInt();
        int m = io.nextInt();

        /**
         * Lista de adjacência.
         *
         * adj[u] contém todas as arestas que saem do vértice u.
         */
        List<List<Edge>> adj = new ArrayList<>(n + 1);

        for (int i = 0; i <= n; i++) {
            adj.add(new ArrayList<>());
        }

        /**
         * Leitura das arestas.
         *
         * u -> origem
         * v -> destino
         * w -> peso da aresta
         */
        for (int i = 0; i < m; i++) {
            int u = io.nextInt();
            int v = io.nextInt();
            long w = io.nextLong();

            adj.get(u).add(new Edge(v, w));
        }

        /**
         * Vetor de distâncias.
         *
         * Inicialmente todas as distâncias são infinitas.
         */
        long[] dist = new long[n + 1];
        Arrays.fill(dist, Long.MAX_VALUE);

        // Distância da origem para ela mesma é 0.
        dist[1] = 0;

        /**
         * Fila de prioridade utilizada pelo Dijkstra.
         *
         * Sempre processa primeiro o vértice
         * com a menor distância conhecida.
         */
        PriorityQueue<State> pq = new PriorityQueue<>();

        pq.add(new State(1, 0));

        /**
         * Algoritmo de Dijkstra
         */
        while (!pq.isEmpty()) {

            // Remove o vértice com menor distância da fila
            State current = pq.poll();

            int u = current.node;
            long d = current.dist;

            /**
             * Caso exista uma distância melhor já encontrada,
             * ignoramos este estado antigo.
             */
            if (d > dist[u]) {
                continue;
            }

            /**
             * Relaxamento das arestas.
             *
             * Verifica se passar por u gera um caminho
             * mais curto até o vértice vizinho v.
             */
            for (Edge edge : adj.get(u)) {

                int v = edge.target;
                long weight = edge.weight;

                if (dist[u] + weight < dist[v]) {

                    // Atualiza a menor distância encontrada
                    dist[v] = dist[u] + weight;

                    // Insere o novo estado na fila
                    pq.add(new State(v, dist[v]));
                }
            }
        }

        /**
         * Impressão das menores distâncias
         * do vértice 1 para todos os vértices.
         */
        for (int i = 1; i <= n; i++) {
            out.print(dist[i] + " ");
        }

        out.println();
        out.flush();
    }

    /**
     * Classe de entrada rápida.
     *
     * Utiliza leitura direta de bytes em buffer,
     * evitando a lentidão de Scanner em entradas muito grandes.
     *
     * Foi utilizada porque o problema possui limites elevados
     * e o Scanner pode causar Time Limit Exceeded (TLE).
     */
    static class FastIO {

        private InputStream stream;

        // Buffer de 64 KB
        private byte[] buf = new byte[1 << 16];

        private int head, tail;

        public FastIO(InputStream stream) {
            this.stream = stream;
        }

        /**
         * Lê um byte do buffer.
         * Caso o buffer esteja vazio,
         * carrega novos dados da entrada.
         */
        private int read() {

            if (head >= tail) {

                head = 0;

                try {
                    tail = stream.read(buf, 0, buf.length);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                if (tail <= 0)
                    return -1;
            }

            return buf[head++];
        }

        /**
         * Leitura de números inteiros.
         */
        public int nextInt() {

            int c = read();

            while (c <= 32) {
                if (c == -1)
                    return -1;
                c = read();
            }

            int res = 0;

            while (c > 32) {

                if (c < '0' || c > '9')
                    throw new InputMismatchException();

                res = res * 10 + c - '0';
                c = read();
            }

            return res;
        }

        /**
         * Leitura de números long.
         */
        public long nextLong() {

            int c = read();

            while (c <= 32) {
                if (c == -1)
                    return -1;
                c = read();
            }

            long res = 0;

            while (c > 32) {

                if (c < '0' || c > '9')
                    throw new InputMismatchException();

                res = res * 10 + c - '0';
                c = read();
            }

            return res;
        }
    }
}