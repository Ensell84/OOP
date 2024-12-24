package ru.nsu.bondar.elements.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import ru.nsu.bondar.Element;
import ru.nsu.bondar.builder.MarkdownBuilder;

/**
 * Class representing a table in Markdown.
 */
public class Table extends Element {
    public static final String ALIGN_LEFT = ":---";
    public static final String ALIGN_CENTER = ":---:";
    public static final String ALIGN_RIGHT = "---:";

    private final List<String[]> rows;
    private final String[] alignments;
    private final int rowLimit;

    private Table(TableBuilder builder) {
        this.rows = builder.rows;
        this.alignments = builder.alignments;
        this.rowLimit = builder.rowLimit;
    }

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
     * Checks if this Table object is equal to another object.
     *
     * @param o The object to compare this Table object to.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Table)) {
            return false;
        }

        Table other = (Table) o;
        return rowLimit == other.rowLimit
                && Objects.equals(rows, other.rows)
                && Objects.equals(alignments, other.alignments);
    }

    /**
     * Static inner class for building Table objects.
     */
    public static class TableBuilder implements MarkdownBuilder {
        private final List<String[]> rows = new ArrayList<>();
        private String[] alignments = new String[0];
        private int rowLimit = Integer.MAX_VALUE;

        /**
         * Sets the alignments for the table columns.
         *
         * @param alignments The alignments to set.
         * @return TableBuilder instance for further method chaining.
         */
        public TableBuilder withAlignments(String... alignments) {
            this.alignments = alignments;
            return this;
        }

        /**
         * Sets the row limit for the table.
         *
         * @param rowLimit The row limit to set.
         * @return TableBuilder instance for further method chaining.
         */
        public TableBuilder withRowLimit(int rowLimit) {
            this.rowLimit = rowLimit;
            return this;
        }

        /**
         * Adds a row to the table.
         *
         * @param cells The cells to add.
         * @return TableBuilder instance for further method chaining.
         */
        public TableBuilder addRow(Object... cells) {
            if (rows.size() < rowLimit) {
                String[] row = new String[cells.length];
                for (int i = 0; i < cells.length; i++) {
                    row[i] = cells[i].toString();
                }
                rows.add(row);
            }
            return this;
        }

        /**
         * Resets the TableBuilder to its initial state.
         */
        @Override
        public void reset() {
            rows.clear();
            alignments = new String[0];
            rowLimit = Integer.MAX_VALUE;
        }

        /**
         * Builds the Table object using current state of the builder.
         *
         * @return Created Table object.
         */
        @Override
        public Table build() {
            return new Table(this);
        }
    }
}