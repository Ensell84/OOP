package ru.nsu.bondar.elements.inline;

import java.util.Objects;
import ru.nsu.bondar.Element;
import ru.nsu.bondar.builder.MarkdownBuilder;

/**
 * Class representing a link in Markdown.
 */
public class Link extends Element {
    private final String url;
    private final String text;
    private final boolean isImage;

    /**
     * Constructor for the Link class. This constructor is private to prevent direct instantiation.
     * Inner "Builder" class LinkBuilder is used for building the Link object.
     *
     * @param builder The LinkBuilder object
     */
    private Link(LinkBuilder builder) {
        this.url = builder.url;
        this.text = builder.text;
        this.isImage = builder.isImage;
    }

    /**
     * Serializes the Link object to its Markdown representation.
     *
     * @return A string containing the link in Markdown format.
     */
    @Override
    public String toMarkdown() {
        if (isImage) {
            return "![" + text + "](" + url + ")";
        }
        return "[" + text + "](" + url + ")";
    }

    /**
     * Checks if this Link object is equal to another object.
     * Two Link objects are considered equal if they have the same URL, text, and image flag.
     *
     * @param o The object to compare to this Link object
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Link)) {
            return false;
        }

        Link other = (Link) o;
        return Objects.equals(url, other.url)
                && Objects.equals(text, other.text)
                && isImage == other.isImage;
    }

    /**
     * Static inner "Builder" class for building Link objects.
     */
    public static class LinkBuilder implements MarkdownBuilder {
        private String url = "";
        private String text = "";
        private boolean isImage;

        /**
         * Sets the URL for the link.
         *
         * @param url The URL to set.
         * @return LinkBuilder instance for further method chaining.
         */
        public LinkBuilder url(String url) {
            this.url = url;
            return this;
        }

        /**
         * Sets the text for the link.
         *
         * @param text The text to set.
         * @return LinkBuilder instance for further method chaining.
         */
        public LinkBuilder text(String text) {
            this.text = text;
            return this;
        }

        /**
         * Sets the link as an image.
         *
         * @return LinkBuilder instance for further method chaining.
         */
        public LinkBuilder image() {
            this.isImage = true;
            return this;
        }

        /**
         * Resets all the properties to default.
         */
        @Override
        public void reset() {
            url = "";
            text = "";
            isImage = false;
        }

        /**
         * Builds the Link object using current state of the builder.
         *
         * @return Created Link object.
         * @throws IllegalStateException if URL or text is empty.
         */
        @Override
        public Link build() {
            if (url.isEmpty() || text.isEmpty()) {
                throw new IllegalStateException("URL and text cannot be empty");
            }
            return new Link(this);
        }
    }
}