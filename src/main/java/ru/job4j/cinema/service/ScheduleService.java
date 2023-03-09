package ru.job4j.cinema.service;

import ru.job4j.cinema.dto.SchedulePreview;
import ru.job4j.cinema.model.FilmSession;

import java.util.Collection;
import java.util.Optional;

public interface ScheduleService {
    Collection<SchedulePreview> findAll();
    Optional<FilmSession> findSessionById(int id);
    Optional<SchedulePreview> findPreviewById(int id);
}