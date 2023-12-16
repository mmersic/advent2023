package org.mersic;

public class Day06 {
    
    private static long solve(long time, long dist) {
        long hold = 1;
        for (int i = 1; i * (time-i) < dist; i++) {
            hold = i;
        }
        hold++;
        
        long go = time - hold;
        return (go - hold) + 1;
    }
    
    public static void main(String[] args) {

//input        
//        Time:        59     68     82     74
//        Distance:   543   1020   1664   1022
        //part1
        long[] times = new long[] {59, 68, 82, 74};
        long[] dists = new long[] {543, 1020, 1664, 1022};
        
        long part1 = 1;
        long part2 = 0;

        for (int i = 0; i < times.length; i++) {
            part1 *=solve(times[i], dists[i]);
        }
        
        part2 = solve(59688274, 543102016641022L);
        
        System.out.println("part1: " + part1);
        System.out.println("part2: " + part2);
//        part1: 275724
//        part2: 37286485
    }
}
