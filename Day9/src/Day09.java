import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day09 {

    static class Point {
        long x, y;
        Point(long x, long y) {
            this.x = x;
            this.y = y;
        }
    }

    static class Edge {
        Point p1, p2;
        long minX, maxX, minY, maxY;

        Edge(Point p1, Point p2) {
            this.p1 = p1;
            this.p2 = p2;
            this.minX = Math.min(p1.x, p2.x);
            this.maxX = Math.max(p1.x, p2.x);
            this.minY = Math.min(p1.y, p2.y);
            this.maxY = Math.max(p1.y, p2.y);
        }

        // Verifica si este borde corta/atraviesa el interior del rectángulo dado
        boolean intersectsInterior(long rLeft, long rRight, long rTop, long rBottom) {
            // Si el borde está completamente fuera de los límites extendidos del rectángulo
            if (this.maxX <= rLeft || this.minX >= rRight || this.maxY <= rTop || this.minY >= rBottom) {
                return false;
            }
            // Si es un borde vertical que cruza horizontalmente el interior
            if (this.minX == this.maxX && this.minX > rLeft && this.minX < rRight) {
                return this.minY < rBottom && this.maxY > rTop;
            }
            // Si es un borde horizontal que cruza verticalmente el interior
            if (this.minY == this.maxY && this.minY > rTop && this.minY < rBottom) {
                return this.minX < rRight && this.maxX > rLeft;
            }
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("input.txt"));
        List<Point> points = new ArrayList<>();

        for (String line : lines) {
            if (line.trim().isEmpty()) continue;
            String[] parts = line.split(",");
            long x = Long.parseLong(parts[0].trim());
            long y = Long.parseLong(parts[1].trim());
            points.add(new Point(x, y));
        }

        // Construir los bordes del polígono (uniciendo los puntos en orden cíclico)
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            Point p1 = points.get(i);
            Point p2 = points.get((i + 1) % points.size());
            edges.add(new Edge(p1, p2));
        }

        long maxAreaP1 = 0;
        long maxAreaP2 = 0;

        // Evaluamos todas las combinaciones de pares de puntos
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                Point p1 = points.get(i);
                Point p2 = points.get(j);

                long left = Math.min(p1.x, p2.x);
                long right = Math.max(p1.x, p2.x);
                long top = Math.min(p1.y, p2.y);
                long bottom = Math.max(p1.y, p2.y);

                // El área incluye las líneas de las baldosas
                long area = (right - left + 1) * (bottom - top + 1);

                //Parte 1
                if (area > maxAreaP1) {
                    maxAreaP1 = area;
                }

                //Parte 2
                // Para que el rectángulo sea válido en la Parte 2, ningún borde del
                // polígono original puede cruzar o segmentar el interior de dicho rectángulo.
                boolean validP2 = true;
                for (Edge edge : edges) {
                    if (edge.intersectsInterior(left, right, top, bottom)) {
                        validP2 = false;
                        break;
                    }
                }

                if (validP2 && area > maxAreaP2) {
                    maxAreaP2 = area;
                }
            }
        }

        System.out.println("Solución Parte 1: " + maxAreaP1);
        System.out.println("Solución Parte 2: " + maxAreaP2);
    }
}