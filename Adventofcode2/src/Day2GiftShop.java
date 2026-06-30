import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Day2GiftShop {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine(); // una sola línea

        long sum = 0;

        // separar rangos por coma
        String[] ranges = input.split(",");

        for (String range : ranges) {
            String[] parts = range.split("-");
            long start = Long.parseLong(parts[0]);
            long end = Long.parseLong(parts[1]);

            // recorrer números del rango
            for (long n = start; n <= end; n++) {
                if (isInvalid(n)) {
                    sum += n;
                }
            }
        }

        System.out.println(sum);
    }

    // comprueba si un número es "mitad repetida"
    private static boolean isInvalid(long n) {
        String s = Long.toString(n);
        int len = s.length();

        // longitud impar → imposible
        if (len % 2 != 0) {
            return false;
        }

        int half = len / 2;
        String first = s.substring(0, half);
        String second = s.substring(half);

        return first.equals(second);
    }
}
