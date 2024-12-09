package ru.nsu.bondar.elements.inline;

import java.util.Objects;

import ru.nsu.bondar.Element;
import ru.nsu.bondar.builder.MarkdownBuilder;

public class Text extends Element {
    private final String content;
    private final boolean isBold;
    private final boolean isItalic;
    private final boolean isStrikethrough;
    private final boolean isCode;

    private Text(TextBuilder builder) {
        this.content = builder.content;
        this.isBold = builder.isBold;
        this.isItalic = builder.isItalic;
        this.isStrikethrough = builder.isStrikethrough;
        this.isCode = builder.isCode;
    }

    @Override
    public String toMarkdown() {
        String result = content;

        if (isCode) {
            return "`" + result + "`";
        }
        if (isBold) {
            result = "**" + result + "**";
        }
        if (isItalic) {
            result = "_" + result + "_";
        }
        if (isStrikethrough) {
            result = "~~" + result + "~~";
        }

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Text))
            return false;

        Text other = (Text) o;
        return Objects.equals(content, other.content) &&
                isBold == other.isBold &&
                isItalic == other.isItalic &&
                isStrikethrough == other.isStrikethrough &&
                isCode == other.isCode;
    }

    public static class TextBuilder implements MarkdownBuilder {
        private String content = "";
        private boolean isBold;
        private boolean isItalic;
        private boolean isStrikethrough;
        private boolean isCode;

        public TextBuilder content(String content) {
            this.content = content;
            return this;
        }

        public TextBuilder appendContent(String content) {
            this.content = this.content + content;
            return this;
        }

        public TextBuilder bold() {
            this.isBold = true;
            return this;
        }

        public TextBuilder italic() {
            this.isItalic = true;
            return this;
        }

        public TextBuilder strikethrough() {
            this.isStrikethrough = true;
            return this;
        }

        public TextBuilder code() {
            this.isCode = true;
            return this;
        }

        @Override
        public void reset() {
            content = "";
            isBold = false;
            isItalic = false;
            isStrikethrough = false;
            isCode = false;
        }

        @Override
        public Text build() {
            if (content.isEmpty()) {
                throw new IllegalStateException("Content cannot be empty");
            }
            return new Text(this);
        }
    }
}