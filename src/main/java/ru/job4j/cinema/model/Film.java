package ru.job4j.cinema.model;

import java.util.Map;
import java.util.Objects;

public class Film {
    public static final Map<String, String> COLUMN_MAPPING = Map.of(
            "id", "id",
            "\"year\"", "year",
            "genre_id", "genreId",
            "minimal_age", "minimalAge",
            "duration_in_minutes", "durationInMinutes",
            "file_id", "fileId",
            "name", "name",
            "description", "description"
    );
    private final int id;
    private final int year;
    private final int genreId;
    private final int minimalAge;
    private final int durationInMinutes;
    private final int fileId;
    private final String name;
    private final String description;

    public Film(int id, int year, int genreId, int minimalAge,
                int durationInMinutes, int fileId, String name, String description) {
        this.id = id;
        this.year = year;
        this.genreId = genreId;
        this.minimalAge = minimalAge;
        this.durationInMinutes = durationInMinutes;
        this.fileId = fileId;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public int getYear() {
        return year;
    }

    public int getGenreId() {
        return genreId;
    }

    public int getMinimalAge() {
        return minimalAge;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public int getFileId() {
        return fileId;
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
        Film film = (Film) o;
        return id == film.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Film{"
                + "id=" + id
                + ", year=" + year
                + ", genreId=" + genreId
                + ", minimalAge=" + minimalAge
                + ", durationInMinutes=" + durationInMinutes
                + ", fileId=" + fileId
                + ", name='" + name + '\''
                + ", description='" + description + '\''
                + '}';
    }
}