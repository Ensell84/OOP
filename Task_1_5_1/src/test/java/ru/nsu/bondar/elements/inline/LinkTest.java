package ru.nsu.bondar.elements.inline;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Link class.
 */
public class LinkTest {

    @Test
    void simpleLinkCreation() {
        Link link = new Link.LinkBuilder()
                .url("https://example.com")
                .text("Example")
                .build();
        assertEquals("[Example](https://example.com)", link.toMarkdown());
    }

    @Test
    void imageLink() {
        Link link = new Link.LinkBuilder()
                .url("https://example.com/image.png")
                .text("Image")
                .image()
                .build();
        assertEquals("![Image](https://example.com/image.png)", link.toMarkdown());
    }

    @Test
    void builderReset() {
        Link.LinkBuilder builder = new Link.LinkBuilder()
                .url("https://example.com")
                .text("Example");

        Link link1 = builder.build();

        builder.reset();
        builder.url("https://example.org").text("Example Org");
        Link link2 = builder.build();

        assertEquals("[Example](https://example.com)", link1.toMarkdown());
        assertEquals("[Example Org](https://example.org)", link2.toMarkdown());
    }

    @Test
    void emptyUrlThrows() {
        Link.LinkBuilder builder = new Link.LinkBuilder()
                .text("Example");
        assertThrows(IllegalStateException.class, builder::build);
    }

    @Test
    void emptyTextThrows() {
        Link.LinkBuilder builder = new Link.LinkBuilder()
                .url("https://example.com");
        assertThrows(IllegalStateException.class, builder::build);
    }

    @Test
    void equalsTest() {
        Link link1 = new Link.LinkBuilder()
                .url("https://example.com")
                .text("Example")
                .build();

        Link link2 = new Link.LinkBuilder()
                .url("https://example.com")
                .text("Example")
                .build();

        Link different = new Link.LinkBuilder()
                .url("https://example.org")
                .text("Example Org")
                .build();

        assertEquals(link1, link2);
        assertNotEquals(link1, different);
        assertNotEquals(link1, null);
    }
}