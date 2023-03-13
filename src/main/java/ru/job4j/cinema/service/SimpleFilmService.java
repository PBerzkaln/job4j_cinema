package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmPreview;
import ru.job4j.cinema.repository.FilmRepository;
import ru.job4j.cinema.repository.GenreRepository;

import java.util.Collection;

@Service
@ThreadSafe
public class SimpleFilmService implements FilmService {
    private final FilmRepository sql2oFilmRepository;
    private final GenreRepository sql2oGenreRepository;

    public SimpleFilmService(FilmRepository sql2oFilmRepository, GenreRepository sql2oGenreRepository) {
        this.sql2oFilmRepository = sql2oFilmRepository;
        this.sql2oGenreRepository = sql2oGenreRepository;
    }

    @Override
    public Collection<FilmPreview> findAll() {
        return sql2oFilmRepository.findAll()
                .stream()
                .map(f -> new FilmPreview(
                        f.getId(), f.getFileId(), f.getName(), f.getDescription(), f.getYear(),
                        f.getMinimalAge(), f.getDurationInMinutes(),
                        sql2oGenreRepository.findById(f.getGenreId()).get().getName()))
                .toList();
    }
}