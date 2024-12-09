package ru.nsu.bondar.elements.block;

import ru.nsu.bondar.Element;
import ru.nsu.bondar.builder.MarkdownBuilder;

public class Table extends Element {
    //TODO

    @Override
    public String toMarkdown() {
        return "";
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }

    public static class TableBuilder implements MarkdownBuilder {
        @Override
        public void reset() {
            return;
        }

        @Override
        public Table build() {
            return new Table();
        }
    }
}
