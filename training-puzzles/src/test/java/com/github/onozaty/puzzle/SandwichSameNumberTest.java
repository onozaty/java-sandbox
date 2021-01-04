package com.github.onozaty.puzzle;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;

import com.github.onozaty.puzzle.SandwichSameNumber;
import com.github.onozaty.puzzle.SandwichSameNumber.Board;

public class SandwichSameNumberTest {

    @Test
    public void solove3() {

        Optional<Board> successBoard = SandwichSameNumber.solove(3);
        assertThat(successBoard)
                .isPresent()
                .get()
                .returns(6, Board::getZeroIndex);

        System.out.println(Arrays.toString(successBoard.get().getCards()));
    }

    @Test
    public void solove9() {

        Optional<Board> successBoard = SandwichSameNumber.solove(9);
        assertThat(successBoard)
                .isPresent()
                .get()
                .returns(17, Board::getZeroIndex);

        System.out.println(Arrays.toString(successBoard.get().getCards()));
    }
}
