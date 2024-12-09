package ru.nsu.bondar.elements.block;

import ru.nsu.bondar.Element;
import ru.nsu.bondar.builder.MarkdownBuilder;

/**
 * Class represeinting a table in Markdown.
 */
public class Table extends Element {

    /**
     * Serializes the table to a string in Markdown format.
     *
     * @return a string representing the table in Markdown format.
     */
    @Override
    public String toMarkdown() {
        return "";
    }

    /**
     * Checks if this Tabble object is equal to another object.
     *
     * @param o The object to compare this Table object to.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        return false;
    }

    /**
     * Static inner class for building Table objects.
     */
    public static class TableBuilder implements MarkdownBuilder {
        /**
         * Resets the TableBuilder to its initial state.
         */
        @Override
        public void reset() {
            return;
        }

        /**
         * Builds the Table object using current state of the builder.
         *
         * @return Created Table object.
         */
        @Override
        public Table build() {
            return new Table();
        }
    }
}
