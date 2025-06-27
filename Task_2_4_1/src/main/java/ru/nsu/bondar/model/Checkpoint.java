package ru.nsu.bondar.model;

import java.time.LocalDate;

public class Checkpoint {
    private String name;
    private LocalDate date;

    public Checkpoint(String name, LocalDate date) {
        this.name = name;
        this.date = date;
    }
    
    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Checkpoint{" +
               "name='" + name + '\'' +
               ", date=" + date +
               '}';
    }
}