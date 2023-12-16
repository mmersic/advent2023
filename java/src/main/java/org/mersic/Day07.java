package org.mersic;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

public class Day07 {
    
    enum CardDeterminer { PART1, PART2 }
    
    enum Card { A, K, Q, J, T, NINE, EIGHT, SEVEN, SIX, FIVE, FOUR, THREE, TWO, JOKER;
        public static Card determineCardPart1(char c) {
            return switch (c) {
                case 'A' -> A;
                case 'K' -> K;
                case 'Q' -> Q;
                case 'J' -> J;
                case 'T' -> T;
                case '9' -> NINE;
                case '8' -> EIGHT;
                case '7' -> SEVEN;
                case '6' -> SIX;
                case '5' -> FIVE;
                case '4' -> FOUR;
                case '3' -> THREE;
                case '2' -> TWO;
                default -> throw new RuntimeException("unknown card: " + c);
            };
        }
        public static Card determineCardPart2(char c) {
            return switch (c) {
                case 'A' -> A;
                case 'K' -> K;
                case 'Q' -> Q;
                case 'T' -> T;
                case '9' -> NINE;
                case '8' -> EIGHT;
                case '7' -> SEVEN;
                case '6' -> SIX;
                case '5' -> FIVE;
                case '4' -> FOUR;
                case '3' -> THREE;
                case '2' -> TWO;
                case 'J' -> JOKER;
                default -> throw new RuntimeException("unknown card: " + c);
            };
        }

    }

    enum Kind { FIVE, FOUR, FULL, THREE, TWO_PAIR, ONE_PAIR, HIGH_CARD;

        private static final Pattern FIVE_P = Pattern.compile("([AKQJT98765432])\\1{4}");
        private static final Pattern FOUR_P = Pattern.compile("([AKQJT98765432])\\1{3}");
        private static final Pattern FULL_P1 = Pattern.compile("([AKQJT98765432])\\1([AKQJT98765432])\\2{2}");
        private static final Pattern FULL_P2 = Pattern.compile("([AKQJT98765432])\\1{2}([AKQJT98765432])\\2");
        private static final Pattern THREE_P = Pattern.compile("([AKQJT98765432])\\1{2}");
        private static final Pattern PAIR_P = Pattern.compile("([AKQJT98765432])\\1");
        private static final Pattern TWO_PAIR_P = Pattern.compile("([AKQJT98765432])\\1[AKQJT98765432]*([AKQJT98765432])\\2");
        
        public static Kind determineKindPart1(String hand) {
            int [] hand_ints = hand.chars().toArray();
            Arrays.sort(hand_ints);
            
            char[] chars = new char[5];
            for (int i = 0; i < 5; i++) {
                chars[i] = (char) hand_ints[i];
            }
            
            hand = new String(chars);
            if (FIVE_P.matcher(hand).find()) {
                return FIVE;
            } if (FOUR_P.matcher(hand).find()) {
                return FOUR;
            } if (FULL_P1.matcher(hand).find()) {
                return FULL;
            } if (FULL_P2.matcher(hand).find()) {
                return FULL;
            } if (THREE_P.matcher(hand).find()) {
                return THREE;
            } if (TWO_PAIR_P.matcher(hand).find()) {
                return TWO_PAIR;
            } if (PAIR_P.matcher(hand).find()) {
                return ONE_PAIR;
            } else {
                return HIGH_CARD;
            }
        }
        public static Kind determineKindPart2(String hand) {
            int [] hand_ints = hand.chars().toArray();
            Arrays.sort(hand_ints);

            char[] chars = new char[5];
            int JOKERS = 0;
            for (int i = 0; i < 5; i++) {
                chars[i] = (char) hand_ints[i];
                if (chars[i] == 'J') {
                    JOKERS++;
                }
            }

            hand = new String(chars);
            if (FIVE_P.matcher(hand).find()) {
                return FIVE;
            } if (FOUR_P.matcher(hand).find()) {
                if (JOKERS > 0) {
                    return FIVE;
                }
                return FOUR;
            } if (FULL_P1.matcher(hand).find()) {
                if (JOKERS > 0) {
                    return FIVE;
                }
                return FULL;
            } if (FULL_P2.matcher(hand).find()) {
                if (JOKERS > 0) {
                    return FIVE;
                }
                return FULL;
            } if (THREE_P.matcher(hand).find()) {
                if (JOKERS > 0) {
                    return FOUR;
                }
                return THREE;
            } if (TWO_PAIR_P.matcher(hand).find()) {
                if (JOKERS == 1) {
                    return FULL;
                }
                if (JOKERS == 2) {
                    return FOUR;
                }
                return TWO_PAIR;
            } if (PAIR_P.matcher(hand).find()) {
                if (JOKERS > 0) {
                    return THREE;
                }
                return ONE_PAIR;
            } else {
                if (JOKERS > 0) {
                    return ONE_PAIR;
                }
                return HIGH_CARD;
            }
        }
        
    }
    
    record Hand(char[] hand, Kind kind, int bid) {
        public String toString() {
            return new String(hand) + " " + kind + " " + bid;
        }
    }
    
    static class HandComparator implements Comparator<Hand> {
        private final CardDeterminer cardDeterminer;
        public HandComparator(CardDeterminer cardDeterminer) {
            this.cardDeterminer = cardDeterminer;
        }

        @Override
        public int compare(Hand hand, Hand other) {
            if (hand.kind.compareTo(other.kind) > 0) {
                return 1;
            } else if (hand.kind.compareTo(other.kind) < 0) {
                return -1;
            } else {
                for (int i = 0; i < 5; i++) {
                    if (hand.hand[i] == other.hand[i]) {
                        continue;
                    }
                    Card c1 = cardDeterminer == CardDeterminer.PART1 ? Card.determineCardPart1(hand.hand[i]) : Card.determineCardPart2(hand.hand[i]);
                    Card c2 = cardDeterminer == CardDeterminer.PART1 ? Card.determineCardPart1(other.hand[i]) : Card.determineCardPart2(other.hand[i]);
                    return c1.compareTo(c2);
                }
            }
            return 0;
        }
    }
    
    public static void main(String[] args) throws Exception {
        List<String> input = Files.readAllLines(Path.of(Day01.class.getClassLoader().getResource("day.07.input").toURI()));
        
        List<Hand> handsPart1 = new ArrayList<>();
        List<Hand> handsPart2 = new ArrayList<>();
        for (String line : input) {
            String[] l = line.split(" ");
            handsPart1.add(new Hand(l[0].toCharArray(), Kind.determineKindPart1(l[0]), Integer.parseInt(l[1])));
            handsPart2.add(new Hand(l[0].toCharArray(), Kind.determineKindPart2(l[0]), Integer.parseInt(l[1])));
        }

        handsPart1.sort(new HandComparator(CardDeterminer.PART1));
        handsPart1 = handsPart1.reversed();
        
        int rank = 1;
        int part1 = 0;
        for (Hand h : handsPart1) {
            part1 += rank++ * h.bid;
        }

        handsPart2.sort(new HandComparator(CardDeterminer.PART2));
        handsPart2 = handsPart2.reversed();

        rank = 1;
        int part2 = 0;
        for (Hand h : handsPart2) {
            part2 += rank++ * h.bid;
        }
        
        System.out.println("part1: " + part1);
        System.out.println("part2: " + part2);
    }

}
