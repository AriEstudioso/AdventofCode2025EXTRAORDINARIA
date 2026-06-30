import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day10 {

    static class Machine {
        int numLights;
        int targetLights;
        List<Integer> targetJoltages = new ArrayList<>();
        List<Integer> buttonMasks = new ArrayList<>();
        List<int[]> buttonVectors = new ArrayList<>();
    }

    public static void main(String[] args) {
        try {
            List<String> lines = Files.readAllLines(Paths.get("input.txt"));
            List<Machine> machines = parseInput(lines);

            if (machines.isEmpty()) {
                System.out.println("ERROR: No se pudo leer ninguna máquina del input.txt.");
                return;
            }

            long totalPart1 = 0;
            long totalPart2 = 0;

            for (Machine m : machines) {
                totalPart1 += solvePart1(m);
                totalPart2 += solvePart2(m);
            }

            System.out.println("====================================");
            System.out.println("Solución Parte 1: " + totalPart1);
            System.out.println("Solución Parte 2: " + totalPart2);
            System.out.println("====================================");

        } catch (IOException e) {
            System.out.println("ERROR: No se encuentra el archivo 'input.txt' en la raíz del proyecto.");
        }
    }

    // --- PARTE 1: BFS ---
    private static int solvePart1(Machine m) {
        Queue<Integer> queue = new LinkedList<>();
        Map<Integer, Integer> dist = new HashMap<>();

        queue.add(0);
        dist.put(0, 0);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            int currentDist = dist.get(current);

            if (current == m.targetLights) {
                return currentDist;
            }

            for (int mask : m.buttonMasks) {
                int next = current ^ mask;
                if (!dist.containsKey(next)) {
                    dist.put(next, currentDist + 1);
                    queue.add(next);
                }
            }
        }
        return 0;
    }

    // --- PARTE 2: Eliminación Gaussiana + DFS ---
    private static long solvePart2(Machine m) {
        int rows = m.targetJoltages.size(); // Número de luces
        int cols = m.buttonVectors.size();   // Número de botones
        if (rows == 0 || cols == 0) return 0;

        double[][] matrix = new double[rows][cols + 1];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                matrix[r][c] = m.buttonVectors.get(c)[r];
            }
            matrix[r][cols] = m.targetJoltages.get(r);
        }

        int rank = 0;
        int[] pivotColumn = new int[rows];
        Arrays.fill(pivotColumn, -1);

        for (int c = 0; c < cols && rank < rows; c++) {
            int pivotRow = rank;
            while (pivotRow < rows && Math.abs(matrix[pivotRow][c]) < 1e-9) pivotRow++;
            if (pivotRow == rows) continue;

            double[] temp = matrix[rank];
            matrix[rank] = matrix[pivotRow];
            matrix[pivotRow] = temp;

            double factor = matrix[rank][c];
            for (int j = c; j <= cols; j++) matrix[rank][j] /= factor;

            for (int i = 0; i < rows; i++) {
                if (i != rank && Math.abs(matrix[i][c]) > 1e-9) {
                    double f = matrix[i][c];
                    for (int j = c; j <= cols; j++) matrix[i][j] -= f * matrix[rank][j];
                }
            }
            pivotColumn[rank] = c;
            rank++;
        }

        boolean[] isPivot = new boolean[cols];
        for (int i = 0; i < rank; i++) {
            if (pivotColumn[i] != -1) isPivot[pivotColumn[i]] = true;
        }

        List<Integer> freeVars = new ArrayList<>();
        for (int c = 0; c < cols; c++) {
            if (!isPivot[c]) freeVars.add(c);
        }

        int[] currentAnswers = new int[cols];
        long[] minPresses = {Long.MAX_VALUE};

        dfsFreeVariables(0, freeVars, isPivot, pivotColumn, rank, matrix, currentAnswers, minPresses);

        return minPresses[0] == Long.MAX_VALUE ? 0 : minPresses[0];
    }

    private static void dfsFreeVariables(int index, List<Integer> freeVars, boolean[] isPivot,
                                         int[] pivotColumn, int rank, double[][] matrix,
                                         int[] currentAnswers, long[] minPresses) {
        if (index == freeVars.size()) {
            int[] tempAnswers = currentAnswers.clone();
            long totalPresses = 0;

            for (int i = 0; i < rank; i++) {
                int pCol = pivotColumn[i];
                double val = matrix[i][matrix[0].length - 1];
                for (int c = 0; c < matrix[0].length - 1; c++) {
                    if (!isPivot[c]) val -= matrix[i][c] * tempAnswers[c];
                }

                long rounded = Math.round(val);
                if (Math.abs(val - rounded) > 1e-4 || rounded < 0) return;
                tempAnswers[pCol] = (int) rounded;
            }

            for (int i = rank; i < matrix.length; i++) {
                if (Math.abs(matrix[i][matrix[0].length - 1]) > 1e-4) return;
            }

            for (int ans : tempAnswers) totalPresses += ans;
            minPresses[0] = Math.min(minPresses[0], totalPresses);
            return;
        }

        int fVar = freeVars.get(index);
        // Límite de pulsaciones máximas estimadas por botón individual
        for (int val = 0; val <= 200; val++) {
            currentAnswers[fVar] = val;
            dfsFreeVariables(index + 1, freeVars, isPivot, pivotColumn, rank, matrix, currentAnswers, minPresses);
        }
    }

    private static List<Machine> parseInput(List<String> lines) {
        List<Machine> machines = new ArrayList<>();

        for (String line : lines) {
            if (line.trim().isEmpty()) continue;

            Machine m = new Machine();

            // 1. Parsear el estado de luces objetivo
            int openBracket = line.indexOf('[');
            int closeBracket = line.indexOf(']');
            if (openBracket == -1 || closeBracket == -1) continue;

            String lightsStr = line.substring(openBracket + 1, closeBracket);
            m.numLights = lightsStr.length();

            int mask = 0;
            for (int i = 0; i < m.numLights; i++) {
                if (lightsStr.charAt(i) == '#') mask |= (1 << i);
            }
            m.targetLights = mask;

            // 2. Parsear los botones
            int braceOpen = line.indexOf('{');
            String buttonsPart = (braceOpen != -1) ? line.substring(closeBracket + 1, braceOpen) : line.substring(closeBracket + 1);

            Pattern buttonPattern = Pattern.compile("\\(([^)]+)\\)");
            Matcher buttonMatcher = buttonPattern.matcher(buttonsPart);

            while (buttonMatcher.find()) {
                String[] indices = buttonMatcher.group(1).split(",");
                int buttonMask = 0;
                int[] vector = new int[m.numLights];

                for (String idxStr : indices) {
                    int idx = Integer.parseInt(idxStr.trim());
                    if (idx < m.numLights) {
                        buttonMask |= (1 << idx);
                        vector[idx] = 1;
                    }
                }
                m.buttonMasks.add(buttonMask);
                m.buttonVectors.add(vector);
            }

            // 3. Parsear los voltajes objetivo:
            if (braceOpen != -1) {
                int braceClose = line.indexOf('}', braceOpen);
                String joltagesStr = line.substring(braceOpen + 1, braceClose);
                String[] jolts = joltagesStr.split(",");
                for (String jolt : jolts) {
                    m.targetJoltages.add(Integer.parseInt(jolt.trim()));
                }
            }

            machines.add(m);
        }
        return machines;
    }
}

