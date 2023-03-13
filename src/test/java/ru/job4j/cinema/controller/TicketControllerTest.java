package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.dto.SchedulePreview;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.HallService;
import ru.job4j.cinema.service.ScheduleService;
import ru.job4j.cinema.service.TicketService;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TicketControllerTest {
    private TicketController ticketController;
    private ScheduleService scheduleService;
    private HallService hallService;
    private TicketService ticketService;

    @BeforeEach
    public void initServices() {
        scheduleService = mock(ScheduleService.class);
        hallService = mock(HallService.class);
        ticketService = mock(TicketService.class);
        ticketController = new TicketController(scheduleService, hallService, ticketService);
    }

    @Test
    public void whenGetByIdAndRedirectToBuyTicketPage() {
        var session = new FilmSession(1, 2, 3, 250,
                LocalDateTime.parse("2023-03-01T10:00:00"),
                LocalDateTime.parse("2023-03-01T12:30:00"));
        var preview = new SchedulePreview(1, 250,
                LocalDateTime.parse("2023-03-01T10:00:00"),
                LocalDateTime.parse("2023-03-01T12:30:00"), "фильм1", "2D/3D");
        when(scheduleService.findSessionById(1)).thenReturn(Optional.of(session));
        when(hallService.getRowCountInHallInSelectedSession(1)).thenReturn(new int[1]);
        when(hallService.getPlaceCountInRowInCurrentHall(1)).thenReturn(new int[1]);
        when(scheduleService.findPreviewById(1)).thenReturn(Optional.of(preview));

        var model = new ConcurrentModel();
        var view = ticketController.getById(model, session.getId());
        var actualSession = model.getAttribute("sessions");

        assertThat(view).isEqualTo("tickets/buy");
        assertThat(actualSession).isEqualTo(session);
    }

    @Test
    public void whenSomeExceptionThrownThenGetById() {
        var expectedException = new RuntimeException("Сеанс с указанным идентификатором не найден");
        when(scheduleService.findPreviewById(anyInt())).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var view = ticketController.getById(model, anyInt());
        var actualExceptionMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualExceptionMessage).isEqualTo(expectedException.getMessage());
    }

    @Test
    public void whenCreateAndRedirectToPurchaseDone() {
        var ticket = new Ticket(1, 1, 2, 2, 1);
        var message = String.format("Вы успешно приобрели билет на %s ряд %s место", 2, 2);
        when(ticketService.save(ticket)).thenReturn(Optional.of(ticket));

        var model = new ConcurrentModel();
        var view = ticketController.create(ticket, model);
        var actualMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("tickets/purchaseDone");
        assertThat(actualMessage).isEqualTo(message);
    }

    @Test
    public void whenSomeExceptionThrownThenCreate() {
        var expectedException = new RuntimeException(
                String.format("Не удалось приобрести билет на %s ряд %s место, %s%s ", 1, 1,
                        "вероятно оно уже занято. ",
                        "Перейдите на страницу бронирования билетов и попробуйте снова."));
        var ticket = new Ticket(1, 1, 1, 1, 1);
        when(ticketService.save(ticket)).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var view = ticketController.create(ticket, model);
        var actualExceptionMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualExceptionMessage).isEqualTo(expectedException.getMessage());
    }
}