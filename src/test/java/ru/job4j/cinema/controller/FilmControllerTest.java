package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.dto.FilmPreview;
import ru.job4j.cinema.service.FilmService;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

public class FilmControllerTest {
    private FilmController filmController;
    private FilmService filmService;

    @BeforeEach
    public void initServices() {
        filmService = mock(FilmService.class);
        filmController = new FilmController(filmService);
    }

    @Test
    public void whenRequestFilmPreviewsListPageThenGetPageWithFilmPreviews() {
        var filmPreview1 = new FilmPreview(1, 1, "фильм1",
                "описание1", 1987, 16, 110, "Комедия");
        var filmPreview2 = new FilmPreview(2, 2, "фильм2",
                "описание2", 2020, 18, 120, "Ужасы");
        var expectedFilmPreviews = List.of(filmPreview1, filmPreview2);
        when(filmService.findAll()).thenReturn(expectedFilmPreviews);

        var model = new ConcurrentModel();
        var view = filmController.getAll(model);
        var actualFilmPreviews = model.getAttribute("films");

        assertThat(view).isEqualTo("films/list");
        assertThat(actualFilmPreviews).isEqualTo(expectedFilmPreviews);
    }
}