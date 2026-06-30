import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day12 {

    public static void main(String[] args) {
        try {
            // Lee todoel archivo input.txt
            String content = Files.readString(Paths.get("input.txt"));

            // Normalizar saltos de línea para evitar problemas entre Windows/Linux
            content = content.replace("\r\n", "\n");

            // Separar por bloques de doble salto de línea
            String[] parts = content.split("\n\n");
            if (parts.length < 2) {
                System.out.println("ERROR: El archivo input.txt no tiene el formato esperado (bloques separados por líneas vacías).");
                return;
            }

            // Los regalos son todos los bloques menos el último
            Map<Integer, Integer> sizes = new HashMap<>();
            for (int i = 0; i < parts.length - 1; i++) {
                String present = parts[i].trim();
                if (present.isEmpty()) continue;

                String[] lines = present.split("\n");
                // El nombre es el número antes de los dos puntos (ej: "0:")
                int name = Integer.parseInt(lines[0].replace(":", "").trim());

                int size = 0;
                for (int j = 1; j < lines.length; j++) {
                    for (char c : lines[j].toCharArray()) {
                        if (c == '#') {
                            size++;
                        }
                    }
                }
                sizes.put(name, size);
            }

            // La última parte contiene las regiones (las cajas)
            int ans = 0;
            String regionsBlock = parts[parts.length - 1].trim();
            String[] regionsLines = regionsBlock.split("\n");

            for (String line : regionsLines) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] mainParts = line.split(": ");
                String szStr = mainParts[0].trim();
                String nsStr = mainParts[1].trim();

                // Obtener dimensiones R y C
                String[] dims = szStr.split("x");
                int R = Integer.parseInt(dims[0].trim());
                int C = Integer.parseInt(dims[1].trim());

                // Obtener la lista de cantidad de regalos (ns)
                String[] nsTokens = nsStr.split("\\s+");
                List<Integer> ns = new ArrayList<>();
                for (String token : nsTokens) {
                    if (!token.isEmpty()) {
                        ns.add(Integer.parseInt(token));
                    }
                }

                // Calcular el tamaño total de los regalos usando su índice
                long totalPresentSize = 0;
                for (int i = 0; i < ns.size(); i++) {
                    int n = ns.get(i);
                    int presentSize = sizes.getOrDefault(i, 0);
                    totalPresentSize += (long) n * presentSize;
                }

                long totalGridSize = (long) R * C;

                // Lógica de filtrado exacta de tu script de Python
                if (totalPresentSize * 1.3 < totalGridSize) {
                    ans++;
                }
            }

            System.out.println("====================================");
            System.out.println("Solución Parte 1: " + ans);
            System.out.println("====================================");

        } catch (IOException e) {
            System.out.println("ERROR: No se encuentra el archivo 'input.txt' en la raíz del proyecto.");
        }
    }
}