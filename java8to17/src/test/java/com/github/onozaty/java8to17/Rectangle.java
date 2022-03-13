package com.github.onozaty.java8to17;

public record Rectangle(int length, int width) {

    public int area() {
        return length * width;
    }
}
