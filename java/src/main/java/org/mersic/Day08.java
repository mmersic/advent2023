package org.mersic;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day08 {
    
    record Node(String name, String left, String right) {}
    
    public static void main(String[] args) throws Exception {
        List<String> input = Files.readAllLines(Path.of(Day01.class.getClassLoader().getResource("day.08.input").toURI()));

        char[] ins = input.getFirst().toCharArray();
        
        
        Map<String, Node> nodes = new HashMap<>();
        List<String> startNodes = new ArrayList<>();
        for (int i = 2; i < input.size(); i++) {
            String line = input.get(i);
            String[] s = line.split(" = ");
            String from = s[0];
            
            String[] to = s[1].split(", ");
            String left = to[0].substring(1);
            String right = to[1].substring(0, 3);
            nodes.put(from, new Node(from, left, right));
            if (from.endsWith("A")) {
                startNodes.add(from);
            }
        }
        
        Node currentNode = nodes.get("AAA");
        int part1 = 0;
        done: while (true) {
            for (char c : ins) {
                switch (c) {
                    case 'R' -> currentNode = nodes.get(currentNode.right);
                    case 'L' -> currentNode = nodes.get(currentNode.left);
                }
                part1++;
                if (currentNode.name.equals("ZZZ")) {
                    break done;
                }
            }
        }
        System.out.println("part1: " + part1);

        for (String startNode : startNodes) {
            System.out.println("startNode: " + startNode);
        }
        
        //0: 11566,23133,34700
        //1: 21250,42501,63752
        //2: 12642,25285,37928
        //3: 16408,32817,49226
        //4: 19098,38197,57296
        //5: 14256,28513,42770
        //LCM is the soln.

        long[] pos = {11566,21250,12642,16408,19098,14256};
        long[] inc = {pos[0]+1,pos[1]+1,pos[2]+1,pos[3]+1,pos[4]+1,pos[5]+1};

        long currentStep = 21250;
        long nextStep = currentStep;
        while (true) {
            for (int i = 0; i < pos.length; i++) {
                while (pos[i] < currentStep) {
                    pos[i] += inc[i];
                }
                if (pos[i] > nextStep) {
                    nextStep = pos[i];
                }
            }
            if (currentStep == nextStep) {
                break;
            }
            currentStep = nextStep;
        }
        System.out.println("part2: " + (currentStep+1));
    }
}
