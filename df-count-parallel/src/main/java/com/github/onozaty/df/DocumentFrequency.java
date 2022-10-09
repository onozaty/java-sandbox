package com.github.onozaty.df;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class DocumentFrequency {

    private final String term;

    private int count;
}
