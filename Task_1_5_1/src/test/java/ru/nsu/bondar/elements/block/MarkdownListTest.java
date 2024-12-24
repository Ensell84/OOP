package ru.nsu.bondar.elements.block;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MarkdownListTest {

    @Test
    void testAddItem() {
        MarkdownList list = new MarkdownList.MarkdownListBuilder()
                .addItem("Item 1")
                .addItem("Item 2")
                .build();
        String expectedMarkdown = "- Item 1\n- Item 2";
        assertEquals(expectedMarkdown, list.toMarkdown());
    }

    @Test
    void testAddTaskItem() {
        MarkdownList list = new MarkdownList.MarkdownListBuilder()
                .addTaskItem("Task 1", true)
                .addTaskItem("Task 2", false)
                .build();
        String expectedMarkdown = "- [x] Task 1\n- [ ] Task 2";
        assertEquals(expectedMarkdown, list.toMarkdown());
    }

    @Test
    void testAddNestedList() {
        MarkdownList nestedList = new MarkdownList.MarkdownListBuilder()
                .addItem("Nested Item 1")
                .addItem("Nested Item 2")
                .build();
        MarkdownList list = new MarkdownList.MarkdownListBuilder()
                .addItem("Item 1")
                .addNestedList(nestedList)
                .addItem("Item 2")
                .build();
        String expectedMarkdown = "- Item 1\n    - Nested Item 1\n    - Nested Item 2\n- Item 2";
        assertEquals(expectedMarkdown, list.toMarkdown());
    }
}