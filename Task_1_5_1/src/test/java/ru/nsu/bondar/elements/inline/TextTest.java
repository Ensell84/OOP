package ru.nsu.bondar.elements.inline;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextTest {

    @Test
    void simpleTextCreation() {
        Text text = new Text.TextBuilder()
                .content("Hello")
                .build();
        assertEquals("Hello", text.toMarkdown());
    }

    @Test
    void boldText() {
        Text text = new Text.TextBuilder()
                .content("Hello")
                .bold()
                .build();
        assertEquals("**Hello**", text.toMarkdown());
    }

    @Test
    void italicText() {
        Text text = new Text.TextBuilder()
                .content("Hello")
                .italic()
                .build();
        assertEquals("_Hello_", text.toMarkdown());
    }

    @Test
    void strikethroughText() {
        Text text = new Text.TextBuilder()
                .content("Hello")
                .strikethrough()
                .build();
        assertEquals("~~Hello~~", text.toMarkdown());
    }

    @Test
    void codeText() {
        Text text = new Text.TextBuilder()
                .content("Hello")
                .code()
                .build();
        assertEquals("`Hello`", text.toMarkdown());
    }

    @Test
    void multipleStyling() {
        Text text = new Text.TextBuilder()
                .content("Hello")
                .bold()
                .italic()
                .build();
        assertEquals("_**Hello**_", text.toMarkdown());
    }

    @Test
    void codeIgnoresOtherStyling() {
        Text text = new Text.TextBuilder()
                .content("Hello")
                .bold()
                .italic()
                .code()
                .build();
        assertEquals("`Hello`", text.toMarkdown());
    }

    @Test
    void appendContent() {
        Text text = new Text.TextBuilder()
                .content("Hello")
                .appendContent(" World")
                .build();
        assertEquals("Hello World", text.toMarkdown());
    }

    @Test
    void builderReset() {
        Text.TextBuilder builder = new Text.TextBuilder()
                .content("Hello")
                .bold();

        Text text1 = builder.build();

        builder.reset();
        builder.content("World");
        Text text2 = builder.build();

        assertEquals("**Hello**", text1.toMarkdown());
        assertEquals("World", text2.toMarkdown());
    }

    @Test
    void emptyContentThrows() {
        Text.TextBuilder builder = new Text.TextBuilder();
        assertThrows(IllegalStateException.class, builder::build);
    }

    @Test
    void equalsTest() {
        Text text1 = new Text.TextBuilder()
                .content("Hello")
                .bold()
                .build();

        Text text2 = new Text.TextBuilder()
                .content("Hello")
                .bold()
                .build();

        Text different = new Text.TextBuilder()
                .content("Hello")
                .italic()
                .build();

        assertEquals(text1, text2);
        assertNotEquals(text1, different);
        assertNotEquals(text1, null);
    }
}