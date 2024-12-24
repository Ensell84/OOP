package ru.nsu.bondar.elements.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import ru.nsu.bondar.Element;

/**
 * Represents a markdown list.
 */
public class MarkdownList extends Element {
    private final List<ListItem> items;

    private MarkdownList(MarkdownListBuilder builder) {
        this.items = builder.items;
    }

    /**
     * Serializes the MarkdownList object to its Markdown representation.
     *
     * @return A string containing the list in Markdown format.
     */
    @Override
    public String toMarkdown() {
        StringBuilder markdown = new StringBuilder();
        for (ListItem item : items) {
            markdown.append(item.toMarkdown()).append("\n");
        }
        return markdown.toString().trim();
    }

    /**
     * Checks if this MarkdownList object is equal to another object.
     * Two MarkdownList objects are considered equal if they have the same items.
     *
     * @param o The object to compare to this MarkdownList object.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MarkdownList)) {
            return false;
        }
        MarkdownList other = (MarkdownList) o;
        return items.equals(other.items);
    }

    /**
     * Static inner "Builder" class for building MarkdownList objects.
     */
    public static class MarkdownListBuilder {
        private final List<ListItem> items = new ArrayList<>();

        /**
         * Adds a regular item to the list.
         *
         * @param content The content of the item.
         * @return MarkdownListBuilder instance for further method chaining.
         */
        public MarkdownListBuilder addItem(String content) {
            items.add(new ListItem(content, false, false));
            return this;
        }

        /**
         * Adds a task item to the list.
         *
         * @param content The content of the task item.
         * @param isChecked Whether the task item is checked.
         * @return MarkdownListBuilder instance for further method chaining.
         */
        public MarkdownListBuilder addTaskItem(String content, boolean isChecked) {
            items.add(new ListItem(content, true, isChecked));
            return this;
        }

        /**
         * Adds a task item to the list.
         *
         * @param element The inline element of the task item.
         * @param isChecked Whether the task item is checked.
         * @return MarkdownListBuilder instance for further method chaining.
         */
        public MarkdownListBuilder addTaskItem(Element element, boolean isChecked) {
            items.add(new ListItem(element, true, isChecked));
            return this;
        }

        /**
         * Adds a nested list to the list.
         *
         * @param nestedList The nested MarkdownList to add.
         * @return MarkdownListBuilder instance for further method chaining.
         */
        public MarkdownListBuilder addNestedList(MarkdownList nestedList) {
            items.add(new ListItem(nestedList));
            return this;
        }

        /**
         * Builds the MarkdownList object using the current state of the builder.
         *
         * @return Created MarkdownList object.
         */
        public MarkdownList build() {
            return new MarkdownList(this);
        }
    }

    /**
     * Represents an item in the markdown list.
     */
    private static class ListItem {
        private final String content;
        private final boolean isTask;
        private final boolean isChecked;
        private final MarkdownList nestedList;
        private final Element inlineElement;

        /**
         * Constructor for a regular or task list item.
         *
         * @param content The content of the item.
         * @param isTask Whether the item is a task.
         * @param isChecked Whether the task item is checked.
         */
        public ListItem(String content, boolean isTask, boolean isChecked) {
            this.content = content;
            this.isTask = isTask;
            this.isChecked = isChecked;
            this.nestedList = null;
            this.inlineElement = null;
        }

        /**
         * Constructor for a task list item with an inline element.
         *
         * @param inlineElement The inline element.
         * @param isTask Whether the item is a task.
         * @param isChecked Whether the task item is checked.
         */
        public ListItem(Element inlineElement, boolean isTask, boolean isChecked) {
            this.content = null;
            this.isTask = isTask;
            this.isChecked = isChecked;
            this.nestedList = null;
            this.inlineElement = inlineElement;
        }

        /**
         * Constructor for a nested list item.
         *
         * @param nestedList The nested MarkdownList.
         */
        public ListItem(MarkdownList nestedList) {
            this.content = null;
            this.isTask = false;
            this.isChecked = false;
            this.nestedList = nestedList;
            this.inlineElement = null;
        }

        /**
         * Serializes the ListItem object to its Markdown representation.
         *
         * @return A string containing the item in Markdown format.
         */
        public String toMarkdown() {
            if (nestedList != null) {
                return nestedList.toMarkdown().replaceAll("(?m)^", "    ");
            }
            if (inlineElement != null) {
                if (isTask) {
                    return "- [" + (isChecked ? "x" : " ") + "] " + inlineElement.toMarkdown();
                }
                return "- " + inlineElement.toMarkdown();
            }
            if (isTask) {
                return "- [" + (isChecked ? "x" : " ") + "] " + content;
            }
            return "- " + content;
        }

        /**
         * Checks if this ListItem object is equal to another object.
         * Two ListItem objects are considered equal if they have the same content,
         * task status, and nested list.
         *
         * @param o The object to compare to this ListItem object.
         * @return True if the objects are equal, false otherwise.
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof ListItem)) {
                return false;
            }
            ListItem other = (ListItem) o;
            return isTask == other.isTask
                    && isChecked == other.isChecked
                    && Objects.equals(content, other.content)
                    && Objects.equals(nestedList, other.nestedList)
                    && Objects.equals(inlineElement, other.inlineElement);
        }
    }
}