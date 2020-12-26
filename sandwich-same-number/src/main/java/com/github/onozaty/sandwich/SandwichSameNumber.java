package com.github.onozaty.sandwich;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * https://twitter.com/kazuhirohat/status/1340816426282622976
 * @author onozaty
 */
public class SandwichSameNumber {

    public static void main(String[] args) {

        int n = 9;

        Board successBoard = solove(n).get();
        System.out.printf(
                "左から%d番目 %s\n",
                successBoard.getZeroIndex() + 1,
                Arrays.toString(successBoard.getCards()));
        // => 左から18番目 [1, 2, 1, 6, 2, 7, 5, 8, 9, 4, 6, 3, 5, 7, 4, 3, 8, 0, 9]
    }

    public static Optional<Board> solove(int n) {

        List<Integer> cards = IntStream.rangeClosed(1, n)
                .mapToObj(Integer::valueOf)
                .collect(Collectors.toList());

        // 最も右側に置ける位置を探すので、右側(すなわち末尾)からジョーカー(0)の位置を固定して探索
        for (int zeroIndex = n * 2; zeroIndex >= 0; zeroIndex--) {

            Board board = Board.of(n, zeroIndex);

            Optional<Board> successBoard = next(board, cards);
            if (successBoard.isPresent()) {
                return successBoard;
            }
        }

        return Optional.empty();
    }

    private static Optional<Board> next(Board board, List<Integer> remainingCards) {

        if (remainingCards.isEmpty()) {
            // 全てのカードが配置済みの場合は成功として終了
            return Optional.of(board);
        }

        for (int remainingCard : remainingCards) {

            if (board.judge(remainingCard)) {

                Board nextBoard = board.put(remainingCard);
                List<Integer> nextRemainingCards = remainingCards.stream()
                        .filter(card -> card != remainingCard) // 使用したカードは取り除く
                        .collect(Collectors.toList());

                Optional<Board> successBoard = next(nextBoard, nextRemainingCards);

                if (successBoard.isPresent()) {
                    return successBoard;
                }
            }
        }

        return Optional.empty();
    }

    public static class Board {

        // イミュータブルなオブジェクトとして利用

        private static final int NONE = -1;

        private final int zeroIndex;

        private final int currentIndex;

        private final int[] cards;

        private Board(int zeroIndex, int currentIndex, int[] cards) {
            this.zeroIndex = zeroIndex;
            this.currentIndex = currentIndex;
            this.cards = cards;
        }

        public static Board of(int n, int zeroIndex) {

            // 各カード(nまでの数字)を2枚ずつとジョーカー(0)を1枚
            int[] cards = new int[n * 2 + 1];
            Arrays.fill(cards, NONE);

            // ジョーカー(0)を配置
            cards[zeroIndex] = 0;

            // ジョーカー(0)を先頭に配置した場合は、index:1から始める
            int currentIndex = (zeroIndex == 0) ? 1 : 0;

            return new Board(zeroIndex, currentIndex, cards);
        }

        public boolean judge(int card) {

            // 2枚目の位置(間にカードの数字分空けた位置)に配置できるかチェック
            int secondIndex = currentIndex + card + 1;
            return secondIndex < cards.length && cards[secondIndex] == NONE;
        }

        private Board put(int card) {

            // 複製として次の状態を返却
            int[] cloneCards = cards.clone();

            cloneCards[currentIndex] = card;
            cloneCards[currentIndex + card + 1] = card;

            // 既にカードが置かれている箇所はスキップしたうえで、次の位置を決定
            int nextIndex = currentIndex;
            for (; nextIndex < cloneCards.length; nextIndex++) {
                if (cloneCards[nextIndex] == NONE) {
                    break;
                }
            }

            return new Board(zeroIndex, nextIndex, cloneCards);
        }

        public int getZeroIndex() {
            return zeroIndex;
        }

        public int[] getCards() {
            return cards;
        }
    }
}
