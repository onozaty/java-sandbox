package com.github.onozaty.java8to17;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class Java14Test {

    public enum Day {
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY;
    }

    @Test
    public void switch式() {

        {
            Day day = Day.WEDNESDAY;
            int numLetters = switch (day) {
                case MONDAY, FRIDAY, SUNDAY -> 6;
                case TUESDAY -> 7;
                case THURSDAY, SATURDAY -> 8;
                case WEDNESDAY -> 9;
                default -> throw new IllegalStateException("Invalid day: " + day);
            };

            assertThat(numLetters).isEqualTo(9);
        }

        {
            Day day = Day.WEDNESDAY;
            int numLetters = switch (day) {
                case MONDAY:
                case FRIDAY:
                case SUNDAY:
                    System.out.println(6);
                    yield 6;
                case TUESDAY:
                    System.out.println(7);
                    yield 7;
                case THURSDAY:
                case SATURDAY:
                    System.out.println(8);
                    yield 8;
                case WEDNESDAY:
                    System.out.println(9);
                    yield 9;
                default:
                    throw new IllegalStateException("Invalid day: " + day);
            };
            assertThat(numLetters).isEqualTo(9);
        }
    }

    @Test
    public void switch_複数ラベル() {

        {
            Day day = Day.WEDNESDAY;
            switch (day) {
                case MONDAY, FRIDAY, SUNDAY:
                    System.out.println(6);
                    break;
                case TUESDAY:
                    System.out.println(7);
                    break;
                case THURSDAY, SATURDAY:
                    System.out.println(8);
                    break;
                case WEDNESDAY:
                    System.out.println(9);
                    break;
                default:
                    throw new IllegalStateException("Invalid day: " + day);
            }
        }
    }
}
