package main;

import java.io.*;
import java.util.*;

public class Day4PrintingDepartment {

    static int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
    static int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<char[]> grid = new ArrayList<>();

        String line;
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            grid.add(line.toCharArray());
        }

        System.out.println(totalRemovable(grid));
    }

    public static int totalRemovable(List<char[]> grid) {
        int rows = grid.size();
        int cols = grid.get(0).length;
        int totalRemoved = 0;

        while (true) {
            boolean[][] remove = new boolean[rows][cols];
            int roundRemoved = 0;

            // 1. detectar accesibles
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (grid.get(i)[j] == '@') {
                        int neighbors = countNeighbors(grid, i, j);
                        if (neighbors < 4) {
                            remove[i][j] = true;
                            roundRemoved++;
                        }
                    }
                }
            }

            // 2. si no se elimina nada, terminar
            if (roundRemoved == 0) break;

            // 3. eliminar
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (remove[i][j]) {
                        grid.get(i)[j] = '.';
                    }
                }
            }

            totalRemoved += roundRemoved;
        }

        return totalRemoved;
    }

    private static int countNeighbors(List<char[]> grid, int x, int y) {
        int rows = grid.size();
        int cols = grid.get(0).length;
        int count = 0;

        for (int d = 0; d < 8; d++) {
            int nx = x + dx[d];
            int ny = y + dy[d];

            if (nx >= 0 && nx < rows && ny >= 0 && ny < cols) {
                if (grid.get(nx)[ny] == '@') {
                    count++;
                }
            }
        }
        return count;
    }
}
