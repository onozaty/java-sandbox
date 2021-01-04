package com.github.onozaty.puzzle;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * https://twitter.com/kazuhirohat/status/1341179311642923008/photo/4
 * @author onozaty
 */
public class ChangeSeatRoundTable {

    public static void main(String[] args) {

        int n = 10;

        int count = solove(n);
        System.out.printf("%,d通り\n", count);
        // => 315,862通り
    }

    public static int solove(int n) {

        List<Integer> students = IntStream.rangeClosed(1, n)
                .mapToObj(Integer::valueOf)
                .collect(Collectors.toList());

        Table table = Table.of(n);
        return next(table, students);
    }

    private static int next(Table table, List<Integer> remainingStudents) {

        if (remainingStudents.isEmpty()) {
            // 全ての生徒が問題なく着席済みの場合は成功として終了
            return 1;
        }

        int count = 0;
        for (int remainingStudent : remainingStudents) {

            if (table.judge(remainingStudent)) {

                Table nextTable = table.sit(remainingStudent);
                List<Integer> nextRemainingStudents = remainingStudents.stream()
                        .filter(student -> student != remainingStudent) // 座った生徒は除く
                        .collect(Collectors.toList());

                count += next(nextTable, nextRemainingStudents);
            }
        }

        return count;
    }

    public static class Table {

        // イミュータブルなオブジェクトとして利用

        private static final int NONE = -1;

        private final int n;

        private final int[] seats;

        private final int currentIndex;

        private Table(int n, int[] seats, int currentIndex) {
            this.n = n;
            this.seats = seats;
            this.currentIndex = currentIndex;
        }

        public static Table of(int n) {

            int[] seats = new int[n + 1]; // n:生徒の数 ＋ 1:先生
            Arrays.fill(seats, NONE);

            // 先頭は先生
            seats[0] = 0;
            return new Table(n, seats, 1);
        }

        public boolean judge(int student) {

            // 右回りに 0,1,2,3 ... n 順での並びを基準として、その時と両隣の人が違うかチェックする
            // すなわち自分の数から±1した数が両隣だと配置できない
            // (円卓なのでnの場合には、左隣が0に戻るので注意)

            // 基準時の両隣
            int baseRight = student - 1;
            int baseLeft = (student == n)
                    ? 0 // 最後の生徒の場合、左は0:先生
                    : student + 1;

            // 現在の両隣
            int nowRight = seats[currentIndex - 1];
            int nowLeft = ((currentIndex + 1) == seats.length)
                    ? seats[0] // 末尾の場合には先頭に戻る
                    : seats[currentIndex + 1];

            // 両隣が基準時と異なること
            return nowRight != baseRight && nowRight != baseLeft
                    && nowLeft != baseRight && nowLeft != baseLeft;
        }

        private Table sit(int student) {

            // 複製として次の状態を返却
            int[] cloneSeats = seats.clone();

            cloneSeats[currentIndex] = student;
            return new Table(n, cloneSeats, currentIndex + 1);
        }
    }
}
