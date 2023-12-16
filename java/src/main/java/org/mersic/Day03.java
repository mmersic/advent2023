package org.mersic;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 {
    

    public static int matchStar(String[] lines, int i) {
        Pattern star = Pattern.compile("\\*");
        Pattern digits = Pattern.compile("\\d+");
        Matcher M = star.matcher(lines[i]);
        
        int sum = 0;
        
        while (M.find()) {
            Set<Integer> N = new HashSet<>();
            Matcher currentLine = digits.matcher(lines[i]);
            
            while (currentLine.find()) {
                if (M.start() == currentLine.end()) {
                    N.add(Integer.parseInt(currentLine.group()));
                }
                if (M.end() == currentLine.start()) {
                    N.add(Integer.parseInt(currentLine.group()));
                }
            }
            if (i > 0) {
                Matcher prevLine = digits.matcher(lines[i-1]);
                while (prevLine.find()) {
                    if (M.start() >= prevLine.start()-1 && M.start() <= prevLine.end()) {
                        N.add(Integer.parseInt(prevLine.group()));
                    }
                }
            }
            if (i+1 < lines.length) {
                Matcher nextLine = digits.matcher(lines[i+1]);
                while (nextLine.find()) {
                    if (M.start() >= nextLine.start()-1 && M.start() <= nextLine.end()) {
                        N.add(Integer.parseInt(nextLine.group()));
                    }
                }
            }

            if (N.size() == 2) {
                sum += N.stream().reduce(1, (x,y) -> x*y);
            }
        }
        
        return sum;
    }
    
    
    public static void main(String[] args) throws Exception {
        List<String> input = Files.readAllLines(Path.of(Day01.class.getClassLoader().getResource("day.03.input").toURI()));
        String[] lines = input.toArray(new String[0]);

        int part1 = 0;
        int part2 = 0;
        Pattern digits = Pattern.compile("\\d+");
        for (int i = 0; i < lines.length; i++) {
            Matcher M = digits.matcher(lines[i]);
            while (M.find()) {
                Set<Character> N = new HashSet<>();
                String S = M.group();
                int start = M.start();
                int end = M.end();
                
                try { N.add(lines[i].charAt(start-1)); } catch (Exception e) {}
                try { N.add(lines[i].charAt(end)); } catch (Exception e) {}
                for (int j = start-1; j <= end; j++) {
                    try { N.add(lines[i-1].charAt(j)); } catch (Exception e) {}
                    try { N.add(lines[i+1].charAt(j)); } catch (Exception e) {}
                }

                if ((N.size() == 1 && !N.contains('.')) || N.size() == 2) {
                    part1 += Integer.parseInt(S);
                }
            }
            part2 += matchStar(lines, i);
        }
        
        System.out.println("part1: " + part1);
        System.out.println("part2: " + part2);
        
    }
}
