package org.mersic;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

record RangeMap(long src, long dest, long range) {}
record Range(long from, long to, long range) {}

public class Day05 {
    private static long part2Slow(List<Range> Seeds, List<List<RangeMap>> lists) {
        long part2;
        part2 = Long.MAX_VALUE;
        for (Range S : Seeds) {
            System.out.println("mapping: " + S);
            long end = S.to();
            for (long seed = S.from(); seed < end; seed++) {
                long map = seed;
                for (List<RangeMap> L : lists) {
                    for (RangeMap R : L) {
                        if (map >= R.src() && map < R.src() + R.range()) {
                            map = R.dest() + (map - R.src());
                            break;
                        }
                    }
                }
                if (map < part2) {
                    part2 = map;
                }
            }
        }
        return part2;
    }

    public static void main(String[] args) throws Exception {
        List<String> input = Files.readAllLines(Path.of(Day01.class.getClassLoader().getResource("day.05.input").toURI()));
        String[] lines = input.toArray(new String[0]);
        long part1 = 0;
        long part2 = 0;
        
        List<List<RangeMap>> lists = new ArrayList<>();
        
        List<Long> seeds = Arrays.stream(lines[0].split("seeds: ")[1].split(" ")).map(Long::parseLong).toList();
        List<Range> Seeds = new ArrayList<>();
        
        for (int i = 0; i < seeds.size(); i+=2) {
            Range S = new Range(seeds.get(i), seeds.get(i) + seeds.get(i+1), seeds.get(i+1));
            Seeds.add(S);
        }
        
        List<RangeMap> list = new ArrayList<>();
        for (int i = 2; i < lines.length; i++) {
            if (lines[i].contains("map")) {
                list = new ArrayList<>();
                lists.add(list);
            } else if (lines[i].trim().isEmpty()) {
                continue;
            } else {
                List<Long> L = Arrays.stream(lines[i].split(" ")).map(Long::parseLong).toList();
                RangeMap R = new RangeMap(L.get(1), L.get(0), L.get(2));
                list.add(R);
            }
        }
        
        part1 = Long.MAX_VALUE;
        for (Long seed : seeds) {
            long map = seed;
            for (List<RangeMap> L : lists) {
                for (RangeMap R : L) {
                    if (map >= R.src() && map < R.src() + R.range()) {
                        map = R.dest() + (map-R.src());
                        break;
                    }
                }
            }
            if (map < part1) {
                part1 = map;
            }
        }

        part2 = part2Slow(Seeds, lists);
        System.out.println("part1: " + part1);
        System.out.println("part2: " + part2);
//        part1: 261668924
//        part2: 24261545
    }


}
