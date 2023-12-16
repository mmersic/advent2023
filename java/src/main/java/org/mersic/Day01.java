package org.mersic;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day01 {
    
    static final String[] numbers_part1 = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", }; 
    static final String[] numbers_part2 = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };
    
    private static int calibrate(String line, String[] numbers) {
        int f = -1;
        int fidx = Integer.MAX_VALUE;
        int l = -1;
        int lidx = Integer.MIN_VALUE;
        for (int i = 0; i < numbers.length; i++) {
            int idx = line.indexOf(numbers[i]);
            if (idx > -1) {
                if (fidx > idx) {
                    f = i % 10;
                    fidx = idx;
                }
            }
            idx = line.lastIndexOf(numbers[i]);
            if (idx > -1) {
                if (lidx < idx) {
                    l = i % 10;
                    lidx = idx;
                }
            }
        }        
        return Integer.parseInt(f + "" + l);
    }
    
    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Path.of(Day01.class.getClassLoader().getResource("day.01.input").toURI()));
        
        int part1 = 0;
        int part2 = 0;
        for (String line : lines) {
            part1 += calibrate(line, numbers_part1);
            part2 += calibrate(line, numbers_part2);
        }
                        
        System.out.println("part1: " + part1);
        System.out.println("part2: " + part2);
    }
}
