import java.io.*;
import java.util.*;

public class Day1SecreEntrance {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<String> rotations = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            rotations.add(line);
        }
        System.out.println(countZeros(rotations));
    }

    // metodo testeable
    public static int countZeros(List<String> rotations) {
        int position = 50;
        int countZero = 0;

        for (String line : rotations) {
            char dir = line.charAt(0);
            int steps = Integer.parseInt(line.substring(1));

            if (dir == 'R') {
                // contar cada cero que pasamos en sentido horario
                for (int i = 1; i <= steps; i++) {
                    position = (position + 1) % 100;
                    if (position == 0) countZero++;
                }
            } else {
                // contar cada cero que pasamos en sentido antihorario
                for (int i = 1; i <= steps; i++) {
                    position = (position - 1 + 100) % 100;
                    if (position == 0) countZero++;
                }
            }
        }

        return countZero;
    }
}
