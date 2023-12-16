package org.mersic;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day04 {
    public static void main(String[] args) throws Exception {
        List<String> input = Files.readAllLines(Path.of(Day01.class.getClassLoader().getResource("day.04.input").toURI()));
        String[] lines = input.toArray(new String[0]);
        
        int part1 = 0;
        int part2 = 0;
        int[] c = new int[input.size()];

        Arrays.fill(c, 1);
        
        for (int i = 0; i < lines.length; i++) {
            String[] s = lines[i].split(": ")[1].split(" \\| ");
            Set<String> w = Arrays.stream(s[0].split(" ")).collect(Collectors.toSet());
            long count = Arrays.stream(s[1].split(" ")).filter(x -> !x.trim().isEmpty()).filter(w::contains).count();
            if (count > 0) {
                part1 += (1 << (count-1));
                for (int j = 1; j < count+1; j++) {
                    c[i+j] += c[i];
                }
            }
        }

        for (int j : c) {
            part2 += j;
        }

        System.out.println("part1: " + part1);
        System.out.println("part2: " + part2);

    }
}
