package ru.job4j.cinema.model;

import java.util.Map;
import java.util.Objects;

public class Hall {
    public static final Map<String, String> COLUMN_MAPPING = Map.of(
            "id", "id",
            "row_count", "rowCount",
            "place_count", "placeCount",
            "name", "name",
            "description", "description"
    );
    private final int id;
    private final int rowCount;
    private final int placeCount;
    private final String name;
    private final String description;

    public Hall(int id, int rowCount, int placeCount, String name, String description) {
        this.id = id;
        this.rowCount = rowCount;
        this.placeCount = placeCount;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getPlaceCount() {
        return placeCount;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Hall hall = (Hall) o;
        return id == hall.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Hall{"
                + "id=" + id
                + ", rowCount=" + rowCount
                + ", placeCount=" + placeCount
                + ", name='" + name + '\''
                + ", description='" + description + '\''
                + '}';
    }
}