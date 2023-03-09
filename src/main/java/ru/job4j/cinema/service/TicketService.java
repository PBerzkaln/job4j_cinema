package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Ticket;

import java.util.Collection;

public interface TicketService {
    Ticket save(Ticket ticket);
    Collection<Ticket> findAll();
}