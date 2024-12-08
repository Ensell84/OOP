package ru.nsu.bondar.elements.inline;

import ru.nsu.bondar.Element;
import ru.nsu.bondar.builder.MarkdownBuilder;

public class Text extends Element {
    //TODO

    @Override
    public String serialize() {
        return "";
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }

    public static class TextBuilder implements MarkdownBuilder {
        @Override
        public void reset() {
            return;
        }

        @Override
        public String getResult() {
            return "";
        }
    }
}