package ru.nsu.bondar.builder;

import ru.nsu.bondar.Element;

/**
 * Builder interface for markdown elements "Builders".
 */
public interface MarkdownBuilder {
    /**
     * Resets the builder to its initial state.
     * Allows to reuse the builder to create multiple elements.
     */
    void reset();

    /**
     * Builds the markdown element.
     */
    Element build();
}
