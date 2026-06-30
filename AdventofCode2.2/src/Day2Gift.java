import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Day2Gift {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();

        long sum = 0;
        String[] ranges = input.split(",");

        for (String range : ranges) {
            String[] parts = range.split("-");
            long start = Long.parseLong(parts[0]);
            long end = Long.parseLong(parts[1]);

            for (long n = start; n <= end; n++) {
                if (isInvalid(n)) {
                    sum += n;
                }
            }
        }

        System.out.println(sum);
    }

    // Parte 2: patrón repetido AL MENOS 2 veces
    private static boolean isInvalid(long n) {
        String s = Long.toString(n);
        int len = s.length();

        for (int patternLen = 1; patternLen <= len / 2; patternLen++) {
            if (len % patternLen != 0) continue;

            String pattern = s.substring(0, patternLen);
            int repeats = len / patternLen;

            if (repeats < 2) continue;

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < repeats; i++) {
                sb.append(pattern);
            }

            if (sb.toString().equals(s)) {
                return true;
            }
        }
        return false;
    }
}
