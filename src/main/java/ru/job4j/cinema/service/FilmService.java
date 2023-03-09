package ru.job4j.cinema.service;

import ru.job4j.cinema.dto.FilmPreview;

import java.util.Collection;

public interface FilmService {
    Collection<FilmPreview> findAll();
}