package ru.job4j.cinema.model;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

public class FilmSession {
    public static final Map<String, String> COLUMN_MAPPING = Map.of(
            "id", "id",
            "film_id", "filmId",
            "halls_id", "hallsId",
            "price", "price",
            "start_time", "startTime",
            "end_time", "endTime"
    );
    private final int id;
    private final int filmId;
    private final int hallsId;
    private final int price;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public FilmSession(int id, int filmId, int hallsId, int price, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.filmId = filmId;
        this.hallsId = hallsId;
        this.price = price;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public int getFilmId() {
        return filmId;
    }

    public int getHallsId() {
        return hallsId;
    }

    public int getPrice() {
        return price;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FilmSession that = (FilmSession) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "FilmSession{"
                + "id=" + id
                + ", filmId=" + filmId
                + ", hallsId=" + hallsId
                + ", price=" + price
                + ", startTime=" + startTime
                + ", endTime=" + endTime
                + '}';
    }
}