package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.HallService;
import ru.job4j.cinema.service.ScheduleService;
import ru.job4j.cinema.service.SimpleTicketService;
import ru.job4j.cinema.service.TicketService;

import java.util.stream.IntStream;

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
//        int[] row = hallService.getRowCountInHallInSelectedSession(id);
////        int[] place = hallService.getPlaceCountInRowInCurrentHall(id);
//        model.addAttribute("row", row);
//        model.addAttribute("place", place);
        model.addAttribute("films", scheduleService.findPreviewById(id).get());
        model.addAttribute("sessions", sessionOptional.get());
        return "tickets/buy";
    }

    @PostMapping("/buy_ticket")
    public String create(@ModelAttribute Ticket ticket, Model model) {
        try {
            ticketService.save(ticket);
            model.addAttribute("message", String.format("Вы успешно приобрели билет на %s ряд %s место",
                    ticket.getRowNumber(), ticket.getPlaceNumber()));
            return "redirect:/purchase_done";
        } catch (Exception exception) {
            model.addAttribute("message", "Не удалось приобрести билет на заданное место. "
                    + "Вероятно оно уже занято. Перейдите на страницу бронирования билетов и попробуйте снова.");
            return "errors/404";
        }
    }
}