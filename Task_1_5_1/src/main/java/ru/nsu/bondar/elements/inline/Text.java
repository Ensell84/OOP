package ru.nsu.bondar.elements.inline;

import java.util.Objects;
import ru.nsu.bondar.Element;
import ru.nsu.bondar.builder.MarkdownBuilder;

/**
 * Class representing the text element in Markdown syntax.
 * This class is used to create inline text elements that
 * can be formatted as bold, italic, strikethrough, and code.
 */
public class Text extends Element {
    private final String content;
    private final boolean isBold;
    private final boolean isItalic;
    private final boolean isStrikethrough;
    private final boolean isCode;

    /**
     * Constructor for the Text class. This constructor is private to prevent direct instantiation.
     * Inner "Builder" class TextBuilder is used for building the Text object.
     *
     * @param builder The TextBuilder object
     */
    private Text(TextBuilder builder) {
        this.content = builder.content;
        this.isBold = builder.isBold;
        this.isItalic = builder.isItalic;
        this.isStrikethrough = builder.isStrikethrough;
        this.isCode = builder.isCode;
    }

    /**
     * Serializes the Text object to its Markdown representation.
     *
     * @return A string containing the inline Text elment in Markdown format.
     */
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

    /**
     * Checks if this Text object is equal to another object.
     * Two Text objects are considered equal if they have the same content,
     * the same bold, italic, strikethrough, and code formatting.
     * 
     * @param o The object to compare to this Text object
     * @return True if the objects are equal, false otherwise.
     */
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

    /**
     * Static inner "Builder" class for building Text objects.
     */
    public static class TextBuilder implements MarkdownBuilder {
        private String content = "";
        private boolean isBold;
        private boolean isItalic;
        private boolean isStrikethrough;
        private boolean isCode;

        /**
         * Replaes current content with the provided.
         *
         * @param content String to replace current content with.
         * @return TextBuilder instance for further method chaining.
         */
        public TextBuilder content(String content) {
            this.content = content;
            return this;
        }

        /**
         * Append content to the current content.
         * 
         * @param content
         * @return TextBuilder instance for further method chaining.
         */
        public TextBuilder appendContent(String content) {
            this.content = this.content + content;
            return this;
        }

        /**
         * Applie bold formatting to the text.
         * 
         * @return TextBuilder instance for further method chaining.
         */
        public TextBuilder bold() {
            this.isBold = true;
            return this;
        }

        /**
         * Appilies italic formatting to the text.
         * 
         * @return TextBuilder instance for further method chaining.
         */
        public TextBuilder italic() {
            this.isItalic = true;
            return this;
        }

        /**
         * Appilies strikethrough formatting to the text.
         * 
         * @return TextBuilder instance for further method chaining.
         */
        public TextBuilder strikethrough() {
            this.isStrikethrough = true;
            return this;
        }

        /**
         * Appilies code formatting to the text.
         * 
         * @return TextBuilder instance for further method chaining.
         */
        public TextBuilder code() {
            this.isCode = true;
            return this;
        }

        /**
         * Resets all the formatting and content to default.
         */
        @Override
        public void reset() {
            content = "";
            isBold = false;
            isItalic = false;
            isStrikethrough = false;
            isCode = false;
        }

        /**
         * Builds the Text object using current state of the builder.
         *
         * @return Cretaed Text object.
         * @throws IllegalStateException if content is empty.
         */
        @Override
        public Text build() {
            if (content.isEmpty()) {
                throw new IllegalStateException("Content cannot be empty");
            }
            return new Text(this);
        }
    }
}