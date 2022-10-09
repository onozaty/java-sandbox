package com.github.onozaty.df;

import lombok.Value;

@Value
public class DocumentFrequency {

    private final String term;

    private final long count;
}
