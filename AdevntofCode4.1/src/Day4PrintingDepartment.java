import java.io.*;
import java.util.*;

public class Day4PrintingDepartment {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<String> lines = new ArrayList<>();

        String line;
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            lines.add(line);
        }

        int rows = lines.size();
        int cols = lines.get(0).length();
        char[][] grid = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            grid[i] = lines.get(i).toCharArray();
        }

        System.out.println(countAccessible(grid));
    }

    public static int countAccessible(char[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int result = 0;

        // direcciones de los 8 vecinos
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == '@') {
                    int neighbors = 0;

                    for (int i = 0; i < 8; i++) {
                        int nr = r + dx[i];
                        int nc = c + dy[i];

                        if (nr >= 0 && nr < rows && nc >= 0 && nc < cols) {
                            if (grid[nr][nc] == '@') {
                                neighbors++;
                            }
                        }
                    }

                    if (neighbors < 4) {
                        result++;
                    }
                }
            }
        }
        return result;
    }
}
