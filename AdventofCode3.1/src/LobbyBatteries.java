import java.io.*;
import java.util.*;

public class LobbyBatteries {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        int totalJoltage = 0;

        while ((line = br.readLine()) != null && !line.isEmpty()) {
            totalJoltage += maxTwoDigit(line);
        }

        System.out.println(totalJoltage);
    }

    public static int maxTwoDigit(String bank) {
        int max = 0;
        int n = bank.length();
        for (int i = 0; i < n - 1; i++) {
            int first = bank.charAt(i) - '0';
            for (int j = i + 1; j < n; j++) {
                int second = bank.charAt(j) - '0';
                int num = first * 10 + second;
                if (num > max) {
                    max = num;
                }
            }
        }
        return max;
    }
}
