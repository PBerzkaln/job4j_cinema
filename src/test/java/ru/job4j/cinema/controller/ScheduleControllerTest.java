package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.dto.SchedulePreview;
import ru.job4j.cinema.service.ScheduleService;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

public class ScheduleControllerTest {
    private ScheduleController scheduleController;
    private ScheduleService scheduleService;

    @BeforeEach
    public void initServices() {
        scheduleService = mock(ScheduleService.class);
        scheduleController = new ScheduleController(scheduleService);
    }

    @Test
    public void whenRequestSchedulePreviewsListPageThenGetPageWithSchedulePreviews() {
        var schedulePreview1 = new SchedulePreview(1, 250, LocalDateTime.parse("2023-03-01T10:00:00"),
                LocalDateTime.parse("2023-03-01T12:30:00"), "фильм1", "Зал с пуфиками");
        var schedulePreview2 = new SchedulePreview(2, 350, LocalDateTime.parse("2023-02-28T14:00:00"),
                LocalDateTime.parse("2023-03-01T18:00:00"), "фильм2", "2D/3D");
        var expectedSchedulePreviews = List.of(schedulePreview1, schedulePreview2);
        when(scheduleService.findAll()).thenReturn(expectedSchedulePreviews);

        var model = new ConcurrentModel();
        var view = scheduleController.getAll(model);
        var actualSchedulesPreviews = model.getAttribute("schedules");

        assertThat(view).isEqualTo("schedules/list");
        assertThat(actualSchedulesPreviews).isEqualTo(expectedSchedulePreviews);
    }
}