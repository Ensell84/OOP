package ru.nsu.bondar.elements.block;

import java.util.ArrayList;
import java.util.List;
import ru.nsu.bondar.Element;

public class MarkdownList extends Element {
    private final List<ListItem> items;

    private MarkdownList(MarkdownListBuilder builder) {
        this.items = builder.items;
    }

    @Override
    public String toMarkdown() {
        StringBuilder markdown = new StringBuilder();
        for (ListItem item : items) {
            markdown.append(item.toMarkdown()).append("\n");
        }
        return markdown.toString().trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MarkdownList)) return false;
        MarkdownList other = (MarkdownList) o;
        return items.equals(other.items);
    }

    public static class MarkdownListBuilder {
        private final List<ListItem> items = new ArrayList<>();

        public MarkdownListBuilder addItem(String content) {
            items.add(new ListItem(content, false, false));
            return this;
        }

        public MarkdownListBuilder addTaskItem(String content, boolean isChecked) {
            items.add(new ListItem(content, true, isChecked));
            return this;
        }

        public MarkdownListBuilder addNestedList(MarkdownList nestedList) {
            items.add(new ListItem(nestedList));
            return this;
        }

        public MarkdownList build() {
            return new MarkdownList(this);
        }
    }

    private static class ListItem {
        private final String content;
        private final boolean isTask;
        private final boolean isChecked;
        private final MarkdownList nestedList;

        public ListItem(String content, boolean isTask, boolean isChecked) {
            this.content = content;
            this.isTask = isTask;
            this.isChecked = isChecked;
            this.nestedList = null;
        }

        public ListItem(MarkdownList nestedList) {
            this.content = null;
            this.isTask = false;
            this.isChecked = false;
            this.nestedList = nestedList;
        }

        public String toMarkdown() {
            if (nestedList != null) {
                return nestedList.toMarkdown().replaceAll("(?m)^", "    ");
            }
            if (isTask) {
                return "- [" + (isChecked ? "x" : " ") + "] " + content;
            }
            return "- " + content;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ListItem)) return false;
            ListItem other = (ListItem) o;
            return isTask == other.isTask &&
                    isChecked == other.isChecked &&
                    (content != null ? content.equals(other.content) : other.content == null) &&
                    (nestedList != null ? nestedList.equals(other.nestedList) : other.nestedList == null);
        }
    }
}