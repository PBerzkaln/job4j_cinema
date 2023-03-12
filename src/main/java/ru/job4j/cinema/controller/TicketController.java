package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.HallService;
import ru.job4j.cinema.service.ScheduleService;
import ru.job4j.cinema.service.TicketService;

@Controller
@RequestMapping("/buy")
@ThreadSafe
public class TicketController {
    private final ScheduleService scheduleService;
    private final HallService hallService;
    private final TicketService ticketService;

    public TicketController(ScheduleService scheduleService, HallService hallService, TicketService ticketService) {
        this.scheduleService = scheduleService;
        this.hallService = hallService;
        this.ticketService = ticketService;
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        var sessionOptional = scheduleService.findSessionById(id);
        if (sessionOptional.isEmpty()) {
            model.addAttribute("message", "Сеанс с указанным идентификатором не найден");
            return "errors/404";
        }
        var hallID = sessionOptional.get().getHallsId();
        model.addAttribute("row", hallService.getRowCountInHallInSelectedSession(hallID));
        model.addAttribute("place", hallService.getPlaceCountInRowInCurrentHall(hallID));
        model.addAttribute("films", scheduleService.findPreviewById(id).get());
        model.addAttribute("sessions", sessionOptional.get());
        return "tickets/buy";
    }

    @PostMapping("/buy_ticket")
    public String create(@ModelAttribute Ticket ticket, Model model) {
        var savedTicket = ticketService.save(ticket);
        if (savedTicket.isEmpty()) {
            model.addAttribute("message",
                    String.format("Не удалось приобрести билет на %s ряд %s место, %s%s ",
                            ticket.getRowNumber(), ticket.getPlaceNumber(),
                            "вероятно оно уже занято. ",
                            "Перейдите на страницу бронирования билетов и попробуйте снова."));
            return "errors/404";
        }
        model.addAttribute("message", String.format("Вы успешно приобрели билет на %s ряд %s место",
                savedTicket.get().getRowNumber(), savedTicket.get().getPlaceNumber()));
        return "tickets/purchaseDone";
    }
}