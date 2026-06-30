package main;

import java.io.*;
import java.util.*;

public class Day8Playground {

    static class Point {
        int x, y, z;
        Point(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    static class Edge {
        int a, b;
        double dist;
        Edge(int a, int b, double dist) {
            this.a = a;
            this.b = b;
            this.dist = dist;
        }
    }

    static class DSU {
        int[] parent, size;

        DSU(int n) {
            parent = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        int find(int x) {
            if (parent[x] != x)
                parent[x] = find(parent[x]);
            return parent[x];
        }

        void union(int a, int b) {
            a = find(a);
            b = find(b);
            if (a == b) return;
            if (size[a] < size[b]) {
                int tmp = a; a = b; b = tmp;
            }
            parent[b] = a;
            size[a] += size[b];
        }
    }

    static double dist(Point a, Point b) {
        long dx = a.x - b.x;
        long dy = a.y - b.y;
        long dz = a.z - b.z;
        return Math.sqrt(dx*dx + dy*dy + dz*dz);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<Point> points = new ArrayList<>();

        String line;
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            String[] p = line.split(",");
            points.add(new Point(
                    Integer.parseInt(p[0]),
                    Integer.parseInt(p[1]),
                    Integer.parseInt(p[2])
            ));
        }

        int n = points.size();
        List<Edge> edges = new ArrayList<>();

        // Todas las distancias
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                edges.add(new Edge(i, j, dist(points.get(i), points.get(j))));
            }
        }

        // Ordenar por distancia
        edges.sort(Comparator.comparingDouble(e -> e.dist));

        DSU dsu = new DSU(n);

        // Conectar los 1000 pares más cercanos
        for (int i = 0; i < 1000; i++) {
            Edge e = edges.get(i);
            dsu.union(e.a, e.b);
        }

        // Contar tamaños finales
        Map<Integer, Integer> count = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int root = dsu.find(i);
            count.put(root, count.getOrDefault(root, 0) + 1);
        }

        // Obtener los tamaños
        List<Integer> sizes = new ArrayList<>(count.values());
        sizes.sort(Collections.reverseOrder());

        long result = 1L * sizes.get(0) * sizes.get(1) * sizes.get(2);
        System.out.println(result);
    }
}
