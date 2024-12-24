package ru.nsu.bondar;

public abstract class Element {
    public boolean isInline;

    public abstract String toMarkdown();
    public abstract boolean equals(Object o);
}
