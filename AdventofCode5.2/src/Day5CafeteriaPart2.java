package main;

import java.io.*;
import java.util.*;

public class Day5CafeteriaPart2 {

    static class Range {
        long start, end;

        Range(long start, long end) {
            this.start = start;
            this.end = end;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<Range> ranges = new ArrayList<>();

        String line;

        // Leer SOLO los rangos (hasta línea vacía)
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            String[] parts = line.split("-");
            ranges.add(new Range(
                    Long.parseLong(parts[0]),
                    Long.parseLong(parts[1])
            ));
        }

        // Ordenar por inicio
        ranges.sort(Comparator.comparingLong(r -> r.start));

        long totalFresh = 0;

        long currentStart = ranges.get(0).start;
        long currentEnd = ranges.get(0).end;

        for (int i = 1; i < ranges.size(); i++) {
            Range r = ranges.get(i);

            if (r.start <= currentEnd + 1) {
                // Se solapa o toca
                currentEnd = Math.max(currentEnd, r.end);
            } else {
                // Nuevo rango
                totalFresh += (currentEnd - currentStart + 1);
                currentStart = r.start;
                currentEnd = r.end;
            }
        }

        // Último rango
        totalFresh += (currentEnd - currentStart + 1);

        System.out.println(totalFresh);
    }
}
