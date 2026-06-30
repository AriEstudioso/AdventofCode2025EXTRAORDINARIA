package main;

import java.io.*;
import java.util.*;

public class Day6TrashCompactor {

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

            // Buscar fin del bloque
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

            // Procesar bloque
            List<Long> numbers = new ArrayList<>();
            char op = '?';

            for (String l : lines) {
                if (start >= l.length()) continue;
                String part = l.substring(start, Math.min(end, l.length())).trim();
                if (part.isEmpty()) continue;

                if (part.equals("+") || part.equals("*")) {
                    op = part.charAt(0);
                } else {
                    numbers.add(Long.parseLong(part));
                }
            }

            // Calcular resultado
            long result = (op == '*') ? 1 : 0;
            for (long n : numbers) {
                result = (op == '*') ? result * n : result + n;
            }

            total += result;
        }

        System.out.println(total);
    }
}
