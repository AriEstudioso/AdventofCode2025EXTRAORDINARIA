package main;

import java.io.*;
import java.util.*;

public class Day7LaboratoriesPart2 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<String> grid = new ArrayList<>();

        String line;
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            grid.add(line);
        }

        int rows = grid.size();
        int cols = grid.get(0).length();

        // Buscar S
        int startCol = -1;
        for (int c = 0; c < cols; c++) {
            if (grid.get(0).charAt(c) == 'S') {
                startCol = c;
                break;
            }
        }

        // dp[r][c] = número de timelines en esa posición
        long[][] dp = new long[rows][cols];
        dp[0][startCol] = 1;

        long timelines = 0;

        for (int r = 0; r < rows - 1; r++) {
            for (int c = 0; c < cols; c++) {
                long ways = dp[r][c];
                if (ways == 0) continue;

                char below = grid.get(r + 1).charAt(c);

                if (below == '.') {
                    dp[r + 1][c] += ways;
                }
                else if (below == '^') {
                    // izquierda
                    if (c - 1 >= 0) {
                        dp[r + 1][c - 1] += ways;
                    } else {
                        timelines += ways;
                    }

                    // derecha
                    if (c + 1 < cols) {
                        dp[r + 1][c + 1] += ways;
                    } else {
                        timelines += ways;
                    }
                }
            }
        }

        // Los que llegan a la última fila y siguen recto
        for (int c = 0; c < cols; c++) {
            timelines += dp[rows - 1][c];
        }

        System.out.println(timelines);
    }
}
