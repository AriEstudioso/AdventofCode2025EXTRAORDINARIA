package main;

import java.io.*;
import java.util.*;

public class Day7Laboratories {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<String> grid = new ArrayList<>();

        String line;
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            grid.add(line);
        }

        int rows = grid.size();
        int cols = grid.get(0).length();

        // Encontrar S
        int startCol = -1;
        for (int c = 0; c < cols; c++) {
            if (grid.get(0).charAt(c) == 'S') {
                startCol = c;
                break;
            }
        }

        Queue<int[]> queue = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();

        queue.add(new int[]{0, startCol});
        visited.add("0," + startCol);

        int splits = 0;

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int r = cur[0];
            int c = cur[1];

            int nr = r + 1;
            if (nr >= rows) continue;

            char cell = grid.get(nr).charAt(c);

            if (cell == '.') {
                String key = nr + "," + c;
                if (!visited.contains(key)) {
                    visited.add(key);
                    queue.add(new int[]{nr, c});
                }
            }
            else if (cell == '^') {
                splits++;

                // izquierda
                if (c - 1 >= 0) {
                    String left = nr + "," + (c - 1);
                    if (!visited.contains(left)) {
                        visited.add(left);
                        queue.add(new int[]{nr, c - 1});
                    }
                }

                // derecha
                if (c + 1 < cols) {
                    String right = nr + "," + (c + 1);
                    if (!visited.contains(right)) {
                        visited.add(right);
                        queue.add(new int[]{nr, c + 1});
                    }
                }
            }
        }

        System.out.println(splits);
    }
}
