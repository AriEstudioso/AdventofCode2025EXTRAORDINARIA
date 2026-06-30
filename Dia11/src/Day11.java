import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day11 {

    public static void main(String[] args) {
        try {
            List<String> lines = Files.readAllLines(Paths.get("input.txt"));
            if (lines.isEmpty()) {
                System.out.println("El archivo input.txt está vacío.");
                return;
            }

            // Construir el mapa de adyacencia (Grafo dirigido)
            Map<String, List<String>> graph = new HashMap<>();
            for (String line : lines) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(":");
                String u = parts[0].trim();
                graph.putIfAbsent(u, new ArrayList<>());

                if (parts.length > 1) {
                    String[] neighbors = parts[1].trim().split("\\s+");
                    for (String v : neighbors) {
                        v = v.trim();
                        if (!v.isEmpty()) {
                            graph.get(u).add(v);
                        }
                    }
                }
            }

            System.out.println("====================================");
            System.out.println("Solución Parte 1: " + solvePart1(graph));
            System.out.println("Solución Parte 2: " + solvePart2(graph));
            System.out.println("====================================");

        } catch (IOException e) {
            System.out.println("ERROR: No se encuentra el archivo 'input.txt' en la raíz del proyecto.");
        }
    }

    // --- PARTE 1: Contar caminos totales de "you" a "out" ---
    private static long solvePart1(Map<String, List<String>> graph) {
        Map<String, Long> memo = new HashMap<>();
        return countPaths(graph, "you", "out", memo);
    }

    private static long countPaths(Map<String, List<String>> graph, String current, String target, Map<String, Long> memo) {
        if (current.equals(target)) return 1;
        if (memo.containsKey(current)) return memo.get(current);

        long paths = 0;
        List<String> neighbors = graph.getOrDefault(current, Collections.emptyList());
        for (String neighbor : neighbors) {
            paths += countPaths(graph, neighbor, target, memo);
        }

        memo.put(current, paths);
        return paths;
    }

    // --- PARTE 2: Caminos de "svr" a "out" pasando por "dac" y "fft" ---
    private static long solvePart2(Map<String, List<String>> graph) {
        // Almacenamos el estado compuesto por: "nodoActual,vistoDac,vistoFft" -> totalCaminos
        Map<String, Long> memo = new HashMap<>();
        return countPathsWithRequirements(graph, "svr", "out", false, false, memo);
    }

    private static long countPathsWithRequirements(Map<String, List<String>> graph, String current, String target,
                                                   boolean seenDac, boolean seenFft, Map<String, Long> memo) {

        // Actualizar si pasamos por los servidores críticos
        if (current.equals("dac")) seenDac = true;
        if (current.equals("fft")) seenFft = true;

        // Si llegamos a la salida, solo es válido si vimos ambos servidores
        if (current.equals(target)) {
            return (seenDac && seenFft) ? 1 : 0;
        }

        // Generar llave de memorización
        String stateKey = current + "," + seenDac + "," + seenFft;
        if (memo.containsKey(stateKey)) return memo.get(stateKey);

        long paths = 0;
        List<String> neighbors = graph.getOrDefault(current, Collections.emptyList());
        for (String neighbor : neighbors) {
            paths += countPathsWithRequirements(graph, neighbor, target, seenDac, seenFft, memo);
        }

        memo.put(stateKey, paths);
        return paths;
    }
}