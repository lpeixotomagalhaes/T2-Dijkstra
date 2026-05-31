/**
 * T2 - Resolução de Problemas com Grafos
 * Curso: Ciências da Computação - Universidade de Fortaleza (Unifor)
 * Orientador: Prof. Felipe Jucá
 * Grupo A: Lucas Peixoto Magalhães e Arthur Alves
 * Problema: CSES - Shortest Routes I
 */

import java.io.*;
import java.util.*;

public class Main {

    static class Edge {
        int target;
        long weight;

        public Edge(int target, long weight) {
            this.target = target;
            this.weight = weight;
        }
    }

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
        // Agora usando o leitor de bytes de altíssima performance
        FastIO io = new FastIO(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = io.nextInt();
        int m = io.nextInt();

        List<List<Edge>> adj = new ArrayList<>(n + 1);
        for (int i = 0; i <= n; i++) {
            adj.add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            int u = io.nextInt();
            int v = io.nextInt();
            long w = io.nextLong();
            adj.get(u).add(new Edge(v, w));
        }

        long[] dist = new long[n + 1];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[1] = 0;

        PriorityQueue<State> pq = new PriorityQueue<>();
        pq.add(new State(1, 0));

        while (!pq.isEmpty()) {
            State current = pq.poll();
            int u = current.node;
            long d = current.dist;

            if (d > dist[u]) {
                continue;
            }

            for (Edge edge : adj.get(u)) {
                int v = edge.target;
                long weight = edge.weight;

                if (dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    pq.add(new State(v, dist[v]));
                }
            }
        }

        for (int i = 1; i <= n; i++) {
            out.print(dist[i] + " ");
        }
        out.println();
        out.flush(); 
    }

    // Leitor Customizado de Bytes: Ignora Strings e vai direto ao buffer
    // Isso evita o Time Limit Exceeded em grafos massivos no Java
    static class FastIO {
        private InputStream stream;
        private byte[] buf = new byte[1 << 16]; // Buffer de 64KB
        private int head, tail;

        public FastIO(InputStream stream) {
            this.stream = stream;
        }

        private int read() {
            if (head >= tail) {
                head = 0;
                try {
                    tail = stream.read(buf, 0, buf.length);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (tail <= 0) return -1;
            }
            return buf[head++];
        }

        public int nextInt() {
            int c = read();
            while (c <= 32) {
                if (c == -1) return -1;
                c = read();
            }
            int res = 0;
            while (c > 32) {
                if (c < '0' || c > '9') throw new InputMismatchException();
                res = res * 10 + c - '0';
                c = read();
            }
            return res;
        }

        public long nextLong() {
            int c = read();
            while (c <= 32) {
                if (c == -1) return -1;
                c = read();
            }
            long res = 0;
            while (c > 32) {
                if (c < '0' || c > '9') throw new InputMismatchException();
                res = res * 10 + c - '0';
                c = read();
            }
            return res;
        }
    }
}