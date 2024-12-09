package ru.nsu.bondar.builder;

import ru.nsu.bondar.Element;

public interface MarkdownBuilder {
    void reset();
    Element build();
}
