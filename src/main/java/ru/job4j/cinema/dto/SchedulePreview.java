package ru.job4j.cinema.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SchedulePreview {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MMMM-EEEE-yyyy HH:mm:ss");
    private int id;
    private int price;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String filmName;
    private String hallName;

    public SchedulePreview(int id, int price, LocalDateTime startTime,
                           LocalDateTime endTime, String filmName, String hallName) {
        this.id = id;
        this.price = price;
        this.startTime = startTime;
        this.endTime = endTime;
        this.filmName = filmName;
        this.hallName = hallName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    @Override
    public String toString() {
        return "SchedulePreview{"
                + "id=" + id
                + ", price=" + price
                + ", startTime=" + startTime.format(FORMATTER)
                + ", endTime=" + endTime.format(FORMATTER)
                + ", filmName='" + filmName + '\''
                + ", hallName='" + hallName + '\''
                + '}';
    }
}