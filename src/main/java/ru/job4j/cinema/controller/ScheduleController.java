package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cinema.service.HallService;
import ru.job4j.cinema.service.ScheduleService;

@Controller
@RequestMapping("/schedules")
@ThreadSafe
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final HallService hallService;


    public ScheduleController(ScheduleService scheduleService, HallService hallService) {
        this.scheduleService = scheduleService;
        this.hallService = hallService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("schedules", scheduleService.findAll());
        return "schedules/list";
    }
}