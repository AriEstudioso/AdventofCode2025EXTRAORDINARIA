package main;

import java.io.*;
import java.util.*;

public class Day6TrashCompactorPart2 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<String> lines = new ArrayList<>();

        String line;
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            lines.add(line);
        }

        int rows = lines.size();
        int cols = lines.get(0).length();

        long total = 0;
        int c = 0;

        while (c < cols) {
            // Saltar columnas vacías
            boolean empty = true;
            for (String l : lines) {
                if (c < l.length() && l.charAt(c) != ' ') {
                    empty = false;
                    break;
                }
            }
            if (empty) {
                c++;
                continue;
            }

            // Inicio del bloque
            int start = c;

            // Fin del bloque
            while (c < cols) {
                boolean allSpace = true;
                for (String l : lines) {
                    if (c < l.length() && l.charAt(c) != ' ') {
                        allSpace = false;
                        break;
                    }
                }
                if (allSpace) break;
                c++;
            }
            int end = c;

            // Operador (última fila)
            char op = lines.get(rows - 1).substring(start, end).trim().charAt(0);

            List<Long> numbers = new ArrayList<>();

            // Leer columnas de derecha a izquierda
            for (int col = end - 1; col >= start; col--) {
                StringBuilder sb = new StringBuilder();

                for (int row = 0; row < rows - 1; row++) {
                    if (col < lines.get(row).length()) {
                        char ch = lines.get(row).charAt(col);
                        if (Character.isDigit(ch)) {
                            sb.append(ch);
                        }
                    }
                }

                if (sb.length() > 0) {
                    numbers.add(Long.parseLong(sb.toString()));
                }
            }

            // Calcular resultado del bloque
            long result = (op == '*') ? 1 : 0;
            for (long n : numbers) {
                result = (op == '*') ? result * n : result + n;
            }

            total += result;
        }

        System.out.println(total);
    }
}
