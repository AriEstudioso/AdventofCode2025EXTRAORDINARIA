package main;

import java.io.*;
import java.util.*;

public class Day5Cafeteria {

    static class Range {
        long start, end;

        Range(long start, long end) {
            this.start = start;
            this.end = end;
        }

        boolean contains(long value) {
            return value >= start && value <= end;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<Range> ranges = new ArrayList<>();

        String line;

        // Leer rangos
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            String[] parts = line.split("-");
            ranges.add(new Range(
                    Long.parseLong(parts[0]),
                    Long.parseLong(parts[1])
            ));
        }

        int freshCount = 0;

        // Leer IDs
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            long id = Long.parseLong(line);

            for (Range r : ranges) {
                if (r.contains(id)) {
                    freshCount++;
                    break;
                }
            }
        }

        System.out.println(freshCount);
    }
}
