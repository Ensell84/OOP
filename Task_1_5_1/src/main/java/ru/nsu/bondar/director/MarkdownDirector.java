package ru.nsu.bondar.director;

import ru.nsu.bondar.elements.block.Table;
import ru.nsu.bondar.elements.inline.Text;

public class MarkdownDirector {
    public void makeSimpleTable(Table.TableBuilder builder, String[] headers, String[][] data) {
        builder.reset();
        // ...
    }

    public void makeFormattedText(Text.TextBuilder builder, String content, boolean isBold, boolean isItalic) {
        builder.reset();
        // ...
    }
}