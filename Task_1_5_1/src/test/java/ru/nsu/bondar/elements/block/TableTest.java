package ru.nsu.bondar.elements.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import ru.nsu.bondar.elements.inline.Text;

/**
 * Unit tests for the Table class.
 */
public class TableTest {

    @Test
    void simpleTableCreation() {
        Table table = new Table.TableBuilder()
                .withAlignments(Table.ALIGN_LEFT, Table.ALIGN_RIGHT)
                .addRow("Header1", "Header2")
                .addRow("Row1Col1", "Row1Col2")
                .addRow("Row2Col1", "Row2Col2")
                .build();
        String expectedMarkdown = "| Header1 | Header2 |\n"
                + "| :--- | ---: |\n"
                + "| Row1Col1 | Row1Col2 |\n"
                + "| Row2Col1 | Row2Col2 |";
        assertEquals(expectedMarkdown, table.toMarkdown());
    }

    @Test
    void tableWithInlineElements() {
        Table table = new Table.TableBuilder()
                .withAlignments(Table.ALIGN_LEFT, Table.ALIGN_RIGHT)
                .addRow("Index", "Random")
                .addRow("1", new Text.TextBuilder().content("Bold").bold().build().toMarkdown())
                .addRow("2", new Text.TextBuilder().content("Italic").italic().build().toMarkdown())
                .build();
        String expectedMarkdown = "| Index | Random |\n"
                + "| :--- | ---: |\n"
                + "| 1 | **Bold** |\n"
                + "| 2 | _Italic_ |";
        assertEquals(expectedMarkdown, table.toMarkdown());
    }

    @Test
    void tableEquality() {
        Table table1 = new Table.TableBuilder()
                .withAlignments(Table.ALIGN_LEFT, Table.ALIGN_RIGHT)
                .addRow("Header1", "Header2")
                .addRow("Row1Col1", "Row1Col2")
                .addRow("Row2Col1", "Row2Col2")
                .build();

        Table table2 = new Table.TableBuilder()
                .withAlignments(Table.ALIGN_LEFT, Table.ALIGN_RIGHT)
                .addRow("Header1", "Header2")
                .addRow("Row1Col1", "Row1Col2")
                .addRow("Row2Col1", "Row2Col2")
                .build();

        Table table3 = new Table.TableBuilder()
                .withAlignments(Table.ALIGN_LEFT, Table.ALIGN_CENTER)
                .addRow("Header1", "Header2")
                .addRow("Row1Col1", "Row1Col2")
                .addRow("Row2Col1", "Row2Col2")
                .build();

        assertEquals(table1, table2);
        assertNotEquals(table1, table3);
    }
}