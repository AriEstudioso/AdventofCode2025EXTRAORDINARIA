import java.io.*;
import java.util.*;

public class Day8PlaygroundPart2 {

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

        boolean union(int a, int b) {
            a = find(a);
            b = find(b);
            if (a == b) return false;

            if (size[a] < size[b]) {
                int tmp = a;
                a = b;
                b = tmp;
            }

            parent[b] = a;
            size[a] += size[b];
            return true;
        }
    }

    static double dist(Point a, Point b) {
        long dx = a.x - b.x;
        long dy = a.y - b.y;
        long dz = a.z - b.z;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<Point> points = new ArrayList<>();

        String line;
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            String[] p = line.split(",");
            points.add(new Point(
                    Integer.parseInt(p[0].trim()),
                    Integer.parseInt(p[1].trim()),
                    Integer.parseInt(p[2].trim())
            ));
        }

        int n = points.size();
        List<Edge> edges = new ArrayList<>();

        // Generar todas las aristas
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                edges.add(new Edge(i, j, dist(points.get(i), points.get(j))));
            }
        }

        // Ordenar por distancia
        edges.sort(Comparator.comparingDouble(e -> e.dist));

        DSU dsu = new DSU(n);
        int unions = 0;
        Edge lastEdge = null;

        // Kruskal hasta conectar todo
        for (Edge e : edges) {
            if (dsu.union(e.a, e.b)) {
                unions++;
                lastEdge = e;

                if (unions == n - 1) {
                    break; // todo conectado
                }
            }
        }

        Point p1 = points.get(lastEdge.a);
        Point p2 = points.get(lastEdge.b);

        long result = 1L * p1.x * p2.x;
        System.out.println(result);
    }
}
