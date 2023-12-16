package org.mersic;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day02 {

    private static final int RED = 12;
    private static final int GREEN = 13;
    private static final int BLUE = 14;
    
    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Path.of(Day01.class.getClassLoader().getResource("day.02.input").toURI()));
        
        int idx = 0;
        int part1 = 0;
        int part2 = 0;
        for (String game : lines) {
            idx += 1;
            String[] sets = game.split(": ")[1].split("; ");
            boolean possible = true;
            int min_red = -1;
            int min_blue = -1;
            int min_green = -1;
            for (String s : sets) {
                String[] colors = s.split(", ");
                for (String color : colors) {
                    String[] cc = color.split(" ");
                    int num = Integer.parseInt(cc[0]);
                    String ccc = cc[1];
                    if (ccc.equals("red") && num > min_red) {
                        min_red = num;
                    }
                    if (ccc.equals("blue") && num > min_blue) {
                        min_blue = num;
                    }
                    if (ccc.equals("green") && num > min_green) {
                        min_green = num;
                    }
                    if (ccc.equals("red") && num > RED || ccc.equals("blue") && num > BLUE || ccc.equals("green") && num > GREEN) {
                        possible = false;
                    }
                }
            }
            if (possible) {
                part1 += idx;
            }
            part2 += (min_red * min_green * min_blue);
                
        }
        
        System.out.println("part1: " + part1);
        System.out.println("part2: " + part2);
        
    }

}
