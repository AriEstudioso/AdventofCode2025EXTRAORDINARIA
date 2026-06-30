import java.io.*;
import java.util.*;

public class LobbyBatteriesPart2 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        long totalJoltage = 0;

        while ((line = br.readLine()) != null && !line.isEmpty()) {
            totalJoltage += Long.parseLong(maxKDigits(line, 12));
        }

        System.out.println(totalJoltage);
    }

    // Devuelve el mayor número posible formado por k dígitos en orden
    public static String maxKDigits(String bank, int k) {
        StringBuilder result = new StringBuilder();
        int n = bank.length();
        int start = 0;

        for (int i = 0; i < k; i++) {
            int end = n - (k - i);
            char maxDigit = '0';
            int maxPos = start;
            for (int j = start; j <= end; j++) {
                if (bank.charAt(j) > maxDigit) {
                    maxDigit = bank.charAt(j);
                    maxPos = j;
                }
            }
            result.append(maxDigit);
            start = maxPos + 1;
        }

        return result.toString();
    }
}
